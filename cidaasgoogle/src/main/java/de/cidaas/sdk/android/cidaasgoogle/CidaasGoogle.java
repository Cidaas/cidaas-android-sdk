package de.cidaas.sdk.android.cidaasgoogle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import de.cidaas.sdk.android.CidaasSDKLayout;
import de.cidaas.sdk.android.cidaasgoogle.entity.GoogleSettingsEntity;
import de.cidaas.sdk.android.cidaasgoogle.interfaces.IGoogleAccessTokenEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.interfaces.ICidaasGoogle;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;


public class CidaasGoogle implements ICidaasGoogle {


    private static CidaasGoogle cidaasGoogleInstance;
    private GoogleSignInClient mGoogleSignInClient;
    Activity activity;
    public static final int REQ_CODE = 9001;
    private GoogleSignInOptions signInOptions;

    EventResult<AccessTokenEntity> localAccessTokenEntityResult;


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
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, signInOptions);


        CidaasSDKLayout.iCidaasGoogle = new ICidaasGoogle() {
            @Override
            public void login(EventResult<AccessTokenEntity> accessTokenEntityResult) {
                signIn(accessTokenEntityResult);
            }

            @Override
            public void logout() {
                signOut();
            }


        };
    }


    private void signIn(EventResult<AccessTokenEntity> accessTokenEntityResult) {
        this.requestGoogleAccount(REQ_CODE);
        localAccessTokenEntityResult = accessTokenEntityResult;
    }

    private void signOut() {
        mGoogleSignInClient.signOut();
    }

    private void requestGoogleAccount(int signInCode) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, signInCode);
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
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONObject web = jsonObject.getJSONObject("web");

            if (web != null) {

                googleSettingsEntity.setClientId(web.get("client_id").toString());
                googleSettingsEntity.setClientSecret(web.get("client_secret").toString());
                JSONArray redidecturis = web.getJSONArray("redirect_uris");
                googleSettingsEntity.setRedirectUrl(redidecturis.get(0).toString());
                googleSettingsEntity.setGrantType("authorization_code");
            } else {
                WebAuthError.getShared(activity).customException(
                        WebAuthErrorCode.GOOGLE_ERROR, "Place the correct google service json from web", "CidaasGoogle:readFileInputs");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return googleSettingsEntity;
    }


    public void authorize(int requestCode, int resultCode, Intent data) {

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (result.isSuccess() == true) {
            final GoogleSignInAccount signInAccount = result.getSignInAccount();
            if (signInAccount != null) {
                googleSettingsEntity.setCode(signInAccount.getServerAuthCode());
                getGoogleAccessToken(googleSettingsEntity, new IGoogleAccessTokenEntity() {
                    @Override
                    public void onSuccess(GoogleAccessTokenEntity tokenEntity) {

                        CidaasSDKLayout.getInstance(CidaasGoogle.this.activity).getAccessTokenBySocialWithLoader(tokenEntity.getAccess_token(),
                                "google", CidaasHelper.baseurl, "login", localAccessTokenEntityResult);
                    }

                    @Override
                    public void onError(WebAuthError errorEntity) {
                        localAccessTokenEntityResult.failure(errorEntity);
                    }
                });
            } else {
                localAccessTokenEntityResult.failure(WebAuthError.getShared(activity).googleError(result.getStatus().toString()));
            }
        } else {

            localAccessTokenEntityResult.failure(WebAuthError.getShared(activity).googleError(result.getStatus().toString()));
        }
    }

    @Override
    public void login(EventResult<AccessTokenEntity> accessTokenEntityResult) {
        signIn(accessTokenEntityResult);
    }

    @Override
    public void logout() {
        signOut();
    }
}
