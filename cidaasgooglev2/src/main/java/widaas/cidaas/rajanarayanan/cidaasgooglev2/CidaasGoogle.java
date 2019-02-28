package widaas.cidaas.rajanarayanan.cidaasgooglev2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Interface.ICidaasGoogle;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.Entity.GoogleSettingsEntity;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.Interface.IGoogleAccessTokenEntity;

public class CidaasGoogle implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,ICidaasGoogle{


    private static CidaasGoogle cidaasGoogleInstance;
    private GoogleApiClient client;
    Activity activity;
    public static final int REQ_CODE = 9001;
    private GoogleSignInOptions signInOptions;

    Result<AccessTokenEntity> localAccessTokenEntityResult;


    private GoogleSettingsEntity googleSettingsEntity;

    public static CidaasGoogle getInstance(Activity YourActivity) {
        if (cidaasGoogleInstance == null) {
            cidaasGoogleInstance = new CidaasGoogle(YourActivity);
        }

        return cidaasGoogleInstance;
    }


    //Constructor
    public CidaasGoogle(Activity activity) {
        this.activity = activity;

        googleSettingsEntity = readFileInputs(activity.getAssets(), "google-service.json");

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(googleSettingsEntity.getClientId())
                .requestEmail()
                .build();
        client = new GoogleApiClient.Builder(activity, this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

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
        this.requestGoogleAccount(REQ_CODE);
        localAccessTokenEntityResult=accessTokenEntityResult;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(client);
    }

    public void onStart() {
        client.connect();
    }

    public void onStop() {
        client.disconnect();
    }



    private void requestGoogleAccount(int signInCode) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(client);
        activity.startActivityForResult(intent, signInCode);
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

    //Read input Files from jsonFile
    private GoogleSettingsEntity readFileInputs(AssetManager assetManager, String fileName) {

        GoogleSettingsEntity googleSettingsEntity = new GoogleSettingsEntity();

        try {
            InputStream inputStream= assetManager.open(fileName);
            int size= inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json=new String(buffer,"UTF-8");

            JSONObject jsonObject=new JSONObject(json);
            JSONObject web =  jsonObject.getJSONObject("web");

            if(web!=null) {

                googleSettingsEntity.setClientId(web.get("client_id").toString());
                googleSettingsEntity.setClientSecret(web.get("client_secret").toString());
                JSONArray redidecturis = web.getJSONArray("redirect_uris");
                googleSettingsEntity.setRedirectUrl(redidecturis.get(0).toString());
                googleSettingsEntity.setGrantType("authorization_code");
            }
            else
            {
                WebAuthError.getShared(activity).customException(
                        WebAuthErrorCode.GOOGLE_ERROR,"Place the correct google service json from web", HttpStatusCode.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return  googleSettingsEntity;
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


    public void authorize(int requestCode, int resultCode, Intent data) {

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

      /*  ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setStatus(500);
        errorEntity.setMessage("Error occured");*/

        if (result.isSuccess() == true) {
            final GoogleSignInAccount signInAccount = result.getSignInAccount();
            if (signInAccount != null) {
                googleSettingsEntity.setCode(signInAccount.getServerAuthCode());
                getGoogleAccessToken(googleSettingsEntity, new IGoogleAccessTokenEntity() {
                    @Override
                    public void onSuccess(GoogleAccessTokenEntity tokenEntity) {

                        CidaasSDKLayout.getInstance(CidaasGoogle.this.activity).getAccessTokenBySocialWithLoader(tokenEntity.getAccess_token(),
                                "google", Cidaas.baseurl, "login", localAccessTokenEntityResult);
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
    }

    @Override
    public void login(Result<AccessTokenEntity> accessTokenEntityResult) {
        signIn(accessTokenEntityResult);
    }

    @Override
    public void logout() {
           signOut();
    }
}
