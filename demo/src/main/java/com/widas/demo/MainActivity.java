package com.widas.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import de.cidaas.sdk.android.Cidaas;
import de.cidaas.sdk.android.CidaasSDKLayout;
import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.login.LoginEntity;
import de.cidaas.sdk.android.cidaasnative.view.CidaasNative;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.entity.UserinfoEntity;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;

public class MainActivity extends AppCompatActivity {
    CidaasNative cidaasNative;
    Cidaas cidaas;
    RelativeLayout relativeLayout;
    CidaasSDKLayout cidaasSDKLayout;
    SharedPreference sharedPreference;
    //String accessToken="";
    String accessToken,sub;

   // SessionManager sessionManager;

    ProgressDialog defaultProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidaas = new Cidaas(this);
        cidaasNative = new CidaasNative(this);
        relativeLayout = findViewById(R.id.relative);
        cidaasSDKLayout = CidaasSDKLayout.getInstance(this);
        sharedPreference =  SharedPreference.getInstance(this);
    }

    public void loginWithEmbeddedBrowser(View view) {

        cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new EventResult<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                //Your Success Code
                Log.d("MainActivity", ""+result.getAccess_token());
                sharedPreference.saveData("AccessToken", result.getAccess_token());

            }

            @Override
            public void failure(WebAuthError error) {
                //Your Failure Code
                Log.d("MainActivity",""+error.getErrorMessage());
            }
        });




    }



    public void logout(View view) {

        CidaasNative.getInstance(this).logout(sharedPreference.getData("AccessToken"), new EventResult<Boolean>() {
            @Override
            public void success(Boolean result) {
                Log.d("logout", ""+result);
            }

            @Override
            public void failure(WebAuthError error) {
                Log.d("logout", ""+error.getErrorMessage());
            }
        });
    }

    public void loginwithcredential(View view) {
        callCidaasLogin();
    }

    private void callCidaasLogin() {



        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUsername("narasimha.vaidya@widas.in");
        loginEntity.setPassword("654321");
        loginEntity.setUsername_type("email");

        //getAccessToken(loginEntity);

    }

    private void getAccessToken(LoginEntity loginEntity) {
        cidaasNative.getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                if(result.isSuccess()){
                    String reqId = result.getData().getRequestId();
                    cidaasNative.loginWithCredentials(reqId, loginEntity, new EventResult<LoginCredentialsResponseEntity>() {
                        @Override
                        public void success(LoginCredentialsResponseEntity result) {
                            if(result.isSuccess()){
                                accessToken = result.getData().getAccess_token();
                                Log.i("access_token", accessToken);
                                SharedPreference.getInstance(MainActivity.this).saveData("newAccessToken", accessToken);
                                getLoginUserInfo(result.getData().getSub());


                              //  sessionManager.setAccessToken(accessToken);

                              //  EventBus.getDefault().post(new LoginAccessTokenEvent(result.getData(), result.getStatus())); //Network call success
                            }else {
                              //  EventBus.getDefault().post(new LoginAccessTokenEvent(null,result.getStatus()));
                            }

                        }

                        @Override
                        public void failure(WebAuthError error) {

                            Log.e("access_token_error : ", error.getErrorMessage());

                           // EventBus.getDefault().post(new LoginErrorEvent(error,error.getErrorCode()));

                        }
                    });

                }

            }

            @Override
            public void failure(WebAuthError error) {
                Log.e("access_token_error : ", error.getErrorMessage());

            }
        });
    }

   /* private void getLoginUserInfo(String sub) {
        try{
            Cidaas cidaas = Cidaas.getInstance(this);
            cidaas.getUserInfo(sub, new EventResult<UserinfoEntity>() {
                @Override
                public void success(UserinfoEntity result) {

                    if(result!=null && result.getSub()!=null){
//                        EventBus.getDefault().post(new UserInfoEvent(result)); //Network call success
//                        sessionManager.setUserInfo(result);
//                        sessionManager.setUserSub(result.getSub());
                        Log.d("MainArviui", "Success");
                    }
                    else {
                       // displayErrorMessageDialog("Can't fetch user info");
                        Log.d("MainArviui", "Failure");

                    }
                }
                @Override
                public void failure(WebAuthError error) {
                 //   displayErrorMessageDialog(error.getErrorMessage());
                    Log.d("MainArviui", "Failure");

                }
            });
        }catch (Exception e){
            Log.e("User info error : ",e.getMessage());
           // displayErrorMessageDialog(e.getMessage());
        }
    }*/


    public void userInfo(View view) {

    }

    public void LoginWithInternalBroswer(View view) {
        cidaas.loginWithBrowser(MainActivity.this, "#aabccd", new EventResult<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                Log.d("Main", ""+result);
            }

            @Override
            public void failure(WebAuthError error) {
                Log.d("Main", ""+error.getErrorMessage());

            }
        });
    }

    public void getSub(View view) {
        cidaasNative.getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String reqId = result.getData().getRequestId();
                LoginEntity loginEntity = new LoginEntity();
//                loginEntity.setUsername("narasimha.vaidya@widas.in");
//                loginEntity.setPassword("654321");
                loginEntity.setUsername("arulmuthujyothi.s@widas.in");
                loginEntity.setPassword("123456");
                loginEntity.setUsername_type("email");

                cidaasNative.loginWithCredentials(reqId, loginEntity, new EventResult<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Log.d("sdf",""+result.getData().getSub());
                        getLoginUserInfo(result.getData().getSub());
                    }

                    @Override
                    public void failure(WebAuthError error) {
                    Log.d("sd",""+error.getErrorMessage());
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Log.d("djfk",""+error.getErrorMessage());
            }
        });
    }

    private void getLoginUserInfo(String sub) {
        try{
            Cidaas cidaas = Cidaas.getInstance(this);
            cidaas.getUserInfo(sub, new EventResult<UserinfoEntity>() {
                @Override
                public void success(UserinfoEntity result) {

                   Log.d("d",""+result);
                }
                @Override
                public void failure(WebAuthError error) {
                   // displayErrorMessageDialog(error.getErrorMessage());
                    Log.d("d",""+error.getErrorMessage());

                }
            });
        }catch (Exception e){
            Log.e("User info error : ",e.getMessage());
           // displayErrorMessageDialog(e.getMessage());
        }
    }

}