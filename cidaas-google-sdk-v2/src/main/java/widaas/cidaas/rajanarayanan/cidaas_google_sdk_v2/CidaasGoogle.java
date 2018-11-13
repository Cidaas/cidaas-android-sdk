package widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Helper.GoogleResult;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Entity.GoogleAccessTokenEntity;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Entity.GoogleSettingsEntity;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Repository.CidaasService;

public class CidaasGoogle implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient client;
    private Activity activity;
    private GoogleSignInOptions signInOptions;
    public static final int REQ_CODE = 9001;
    private String access_token = "";

    private GoogleSettingsEntity googleSettingsEntity;

    public CidaasGoogle(Activity activity, AssetManager assetManager, String jsonFileName) {
        this.activity = activity;

        googleSettingsEntity = readFileInputs(assetManager, jsonFileName);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(googleSettingsEntity.getClientId())
                .requestEmail()
                .build();
        client = new GoogleApiClient.Builder(activity, this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

       /* CidaasSDK.iCidaasGoogle = new ICidaasGoogle() {
            @Override
            public void googleLogin(IAccessTokenEntity iAccessTokenEntity) {
                signIn();
            }

            @Override
            public void googleLogout() {
                signOut();
            }
        };*/
    }

    public void googleLogin(AccessTokenEntity accessTokenEntity) {
       // CidaasSDK.iAccessTokenEntity = accessTokenEntity;
        signIn();
    }

    public void googleLogout() {
        signOut();
    }

    public void signIn() {
        this.requestGoogleAccount(REQ_CODE);
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

        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setStatus(500);
        errorEntity.setError("Error occured");

       /* if (result.isSuccess() == true) {
            final GoogleSignInAccount signInAccount = result.getSignInAccount();
            if (signInAccount != null) {
                googleSettingsEntity.setCode(signInAccount.getServerAuthCode());
                getGoogleAccessToken(googleSettingsEntity, new GoogleResult<GoogleAccessTokenEntity>() {
                    @Override
                    public void success(GoogleAccessTokenEntity result) {
                        CidaasSDK cidaasSDK = new CidaasSDK(CidaasGoogle.this.activity);
                        cidaasSDK.getAccessTokenBySocial(tokenEntity.getAccess_token(), "google", "token");
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        CidaasSDK.iAccessTokenEntity.onError(errorEntity);
                    }

                });
            }
            else {
                CidaasSDK.iAccessTokenEntity.onError(errorEntity);
            }
        }
        else {

            CidaasSDK.iAccessTokenEntity.onError(errorEntity);
        }*/
    }

    private void requestGoogleAccount(int signInCode) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(client);
        activity.startActivityForResult(intent, signInCode);
    }

    private void getGoogleAccessToken(GoogleSettingsEntity googleSettingsEntity, final GoogleResult<GoogleAccessTokenEntity> callback) {
       /* CidaasService cidaasService = new CidaasService();
        cidaasService.getGoogleAccessToken(googleSettingsEntity, new GoogleResult<GoogleAccessTokenEntity>() {
            @Override
            public void success(GoogleAccessTokenEntity result) {
                callback.success(result);
            }

            @Override
            public void failure(WebAuthError error) {
               callback.failure(error);
            }

        });*/
    }

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

            googleSettingsEntity.setClientId(web.get("client_id").toString());
            googleSettingsEntity.setClientSecret(web.get("client_secret").toString());
            JSONArray redidecturis = web.getJSONArray("redirect_uris");
            googleSettingsEntity.setRedirectUrl(redidecturis.get(0).toString());
            googleSettingsEntity.setGrantType("authorization_code");
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

}

