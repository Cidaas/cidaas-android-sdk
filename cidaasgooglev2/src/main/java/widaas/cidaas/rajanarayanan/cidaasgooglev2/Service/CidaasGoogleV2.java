package widaas.cidaas.rajanarayanan.cidaasgooglev2.Service;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Interface.ICidaasGoogle;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.CidaasService;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.Entity.GoogleSettingsEntity;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.GoogleAccessTokenEntity;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.Interface.IGoogleAccessTokenEntity;

public class CidaasGoogleV2 implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks ,ICidaasGoogle{

    Activity activity;
    GoogleSignInClient mGoogleSignInClient;
    Result<AccessTokenEntity> localAccessTokenEntityResult;


    public static final int REQ_CODE = 9001;
    private GoogleSignInOptions signInOptions;

    private GoogleApiClient client;
    private GoogleSettingsEntity googleSettingsEntity;


    private static  CidaasGoogleV2 cidaasGoogleInstance;

    public static CidaasGoogleV2 getInstance(Activity YourActivity) {

        if (cidaasGoogleInstance == null) {
            cidaasGoogleInstance = new CidaasGoogleV2(YourActivity);
        }

        return cidaasGoogleInstance;
    }

    public CidaasGoogleV2(Activity activityFromCidaas) {
        activity = activityFromCidaas;
        googleSettingsEntity = readFileInputs(activity.getAssets(), "google-service.json");
        initGoogleSettings();
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(googleSettingsEntity.getClientId())
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, signInOptions);



        CidaasSDKLayout.iCidaasGoogle = new ICidaasGoogle() {
            @Override
            public void login(Result<AccessTokenEntity> accessTokenEntityResult) {
                signIn(accessTokenEntityResult);
            }

            @Override
            public void logout() {

            }
        };

    }


    private void signIn(Result<AccessTokenEntity> accessTokenEntityResult) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, 9001);
        localAccessTokenEntityResult=accessTokenEntityResult;
    }


    public void authorize(int requestCode, int resultCode, Intent data) {
        try {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (result.isSuccess() == true) {
            Task<GoogleSignInAccount> completedTask = GoogleSignIn.getSignedInAccountFromIntent(data);

                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            googleSettingsEntity.setCode(account.getServerAuthCode());

            if (account != null) {
                getGoogleAccessToken(googleSettingsEntity, new IGoogleAccessTokenEntity() {
                    @Override
                    public void onSuccess(GoogleAccessTokenEntity tokenEntity) {

                        CidaasSDKLayout.getInstance(CidaasGoogleV2.this.activity).getAccessTokenBySocialWithLoader(tokenEntity.getAccess_token(),
                                "google", CidaasHelper.baseurl, "login", localAccessTokenEntityResult);
                    }

                    @Override
                    public void onError(WebAuthError errorEntity) {
                        localAccessTokenEntityResult.failure(errorEntity);
                    }
                });
            }
            else {
                localAccessTokenEntityResult.failure(WebAuthError.getShared(activity).googleError());
            }
        }
        else {

            localAccessTokenEntityResult.failure(WebAuthError.getShared(activity).googleError());
        }
        } catch (Exception e) {
         //e.printStackTrace();
        }
    }

    @Override
    public void login(Result<AccessTokenEntity> accessTokenEntityResult) {
        signIn(accessTokenEntityResult);
    }

    @Override
    public void logout() {
        signOut();
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(client).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }


    private void initGoogleSettings() {
        try {

            client = new GoogleApiClient.Builder(activity, this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                    .build();
            googleSettingsEntity = readFileInputs(activity.getAssets(), "google-service.json");


        } catch (Exception ex) {
           // Timber.d(ex.getMessage());
        }

    }

    private GoogleSettingsEntity readFileInputs(AssetManager assetManager, String fileName) {

        GoogleSettingsEntity googleSettingsEntity = new GoogleSettingsEntity();

        try {
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONObject web = jsonObject.getJSONObject("web");

            googleSettingsEntity.setClientId(web.get("client_id").toString());
            googleSettingsEntity.setClientSecret(web.get("client_secret").toString());
            JSONArray redidecturis = web.getJSONArray("redirect_uris");
            googleSettingsEntity.setRedirectUrl(redidecturis.get(0).toString());
            googleSettingsEntity.setGrantType("authorization_code");

        } catch (Exception ex) {
          //  Timber.e(ex.getMessage());
        }

        return googleSettingsEntity;
    }

    private void getGoogleAccessToken(GoogleSettingsEntity googleSettingsEntity, final IGoogleAccessTokenEntity iGoogleAccessTokenEntity) {
        CidaasService cidaasService = new CidaasService(activity);
        cidaasService.getGoogleAccessToken(googleSettingsEntity, new IGoogleAccessTokenEntity() {
            @Override
            public void onSuccess(GoogleAccessTokenEntity googleAccessTokenEntity) {
                iGoogleAccessTokenEntity.onSuccess(googleAccessTokenEntity);
            }

            @Override
            public void onError(WebAuthError errorEntity) {
                iGoogleAccessTokenEntity.onError(errorEntity);
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
