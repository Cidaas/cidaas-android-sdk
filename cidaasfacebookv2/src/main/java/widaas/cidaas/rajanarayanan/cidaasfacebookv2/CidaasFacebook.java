package widaas.cidaas.rajanarayanan.cidaasfacebookv2;

import android.app.Activity;
import android.content.Intent;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Interface.ICidaasFacebook;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import timber.log.Timber;

public class CidaasFacebook implements ICidaasFacebook {

    private CallbackManager callbackManager;
    private Activity activity;



    private static CidaasFacebook cidaasFacebookInstance;

    public static CidaasFacebook getInstance(Activity YourActivity) {
        if (cidaasFacebookInstance == null) {
            cidaasFacebookInstance = new CidaasFacebook(YourActivity);
        }

        return cidaasFacebookInstance;
    }



    public CidaasFacebook(Activity activity) {
        callbackManager = CallbackManager.Factory.create();
        this.activity = activity;

        CidaasSDKLayout.iCidaasFacebook=this;

    }



    @Override
    public void login(Result<AccessTokenEntity> accessTokenEntityResult) {
        signOut();
         signIn(accessTokenEntityResult);
    }

    @Override
    public void logout() {
        signOut();
    }

    private void signOut() {
        try{
        LoginManager.getInstance().logOut();
    }
        catch (Exception e)
    {
        //result.failure(WebAuthError.getShared(activity).customException(400,"Exception: CidaasFacebook: signIn"+e.getMessage(),400));
        Timber.e(e.getMessage());
     }
    }

    private void signIn(final Result<AccessTokenEntity> result) {
        try {


            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    String access_token = AccessToken.getCurrentAccessToken().getToken();
                    if (access_token != null || !access_token.equals("")) {


                        CidaasSDKLayout.getInstance(CidaasFacebook.this.activity).getAccessTokenBySocialWithLoader(access_token, "facebook", Cidaas.baseurl, "login", result);
                    }
                }

                @Override
                public void onCancel() {
                    result.failure(WebAuthError.getShared(activity).facebookOnCancelException());
                }

                @Override
                public void onError(FacebookException error) {
                    WebAuthError webAuthError=WebAuthError.getShared(activity);
                    webAuthError.setErrorMessage(error.getMessage());
                    result.failure(webAuthError);
                }
            });
        }
        catch (Exception e)
        {
            Timber.e("Exception");
            result.failure(WebAuthError.getShared(activity).customException(400,"Exception: CidaasFacebook: signIn"+e.getMessage(),"signin"));
        }
    }

    public void authorize(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
