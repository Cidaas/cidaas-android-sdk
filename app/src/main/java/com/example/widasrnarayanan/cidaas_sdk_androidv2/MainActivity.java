package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Entity.LocalAuthenticationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Interface.ILoader;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA.EnrollPattern;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import timber.log.Timber;
import widaas.cidaas.rajanarayanan.cidaasfacebookv2.CidaasFacebook;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.CidaasGoogle;


public class MainActivity extends AppCompatActivity implements ILoader{

    ProgressDialog progressDialog;
     Cidaas cidaas;
     String requestId;
    CidaasFacebook cidaasFacebook;
    CidaasGoogle cidaasGoogle;
    Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         cidaas = Cidaas.getInstance(this);
         CidaasSDKLayout.loader=this;
         getFCMToken();

         logoutButton=findViewById(R.id.logoutbutton);
         logoutButton.setVisibility(View.INVISIBLE);

        cidaasFacebook=new CidaasFacebook(this);
        cidaasGoogle=new CidaasGoogle(this);


        requestLocationPermission();
        String token = getIntent().getDataString();
        if (token != null) {
           cidaas.handleToken(token);


        }
        else {
        }
    }

    private void requestLocationPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            //return;
        }
    }

    public void openAlertFinger(View view)
    {
        cidaas.callFingerPrint(this, null, new Result<String>() {
            @Override
            public void success(String result) {
                Toast.makeText(MainActivity.this, ""+result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginWithBrowser(View view)
    {
        try
        {

            HashMap<String,String> extraParam=new HashMap<>();
            extraParam.put("scope","openid profile email phone offline_access");
            Cidaas.extraParams=extraParam;
            cidaas.loginWithBrowser(this,"#009900", new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity result) {
                    Toast.makeText(MainActivity.this, "Access Token"+result.getAccess_token(), Toast.LENGTH_SHORT).show();


                    Intent intent=new Intent(MainActivity.this,SuccessfulLogin.class);
                    intent.putExtra("sub",result.getSub());
                    intent.putExtra("accessToken",result.getAccess_token());
                    startActivity(intent);
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(MainActivity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "Failure"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }



    public void loginWithSocial(View view)
    {
        try
        {

            HashMap<String,String> extraParam=new HashMap<>();
            extraParam.put("scope","openid profile email phone offline_access");
            Cidaas.extraParams=extraParam;


            cidaas.loginWithSocial(this, "linkedin", null, new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity result) {
                    Toast.makeText(MainActivity.this, "Access Token"+result.getAccess_token(), Toast.LENGTH_SHORT).show();


                    Intent intent=new Intent(MainActivity.this,SuccessfulLogin.class);
                    intent.putExtra("sub",result.getSub());
                    intent.putExtra("accessToken",result.getAccess_token());
                    startActivity(intent);
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(MainActivity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });

         /*   cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    cidaas.loginWithSocial(this,"linkedin","#009900", new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            Toast.makeText(MainActivity.this, "Access Token"+result.getAccess_token(), Toast.LENGTH_SHORT).show();


                            Intent intent=new Intent(MainActivity.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getSub());
                            intent.putExtra("accessToken",result.getAccess_token());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(MainActivity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }*/

              /*  @Override
                public void failure(WebAuthError error) {

                }
            });*/

        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "Failure"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //get Request Id
    public void getRequestIdMethod(View view)
    {
        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                requestId=result.getData().getRequestId();
                Toast.makeText(MainActivity.this, "Request id ="+result.getData().getRequestId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, "Request id Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //get ClientInfo
    public void getClientInfo(View view){
        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            //cidaas.getRequestId(obj,new Result<String>() {
            @Override
            public void success(AuthRequestResponseEntity requestIdresult) {

               // Toast.makeText(MainActivity.this, result.getData().getRequestId(), Toast.LENGTH_SHORT).show();

                cidaas.getClientInfo(requestIdresult.getData().getRequestId(), new Result<ClientInfoEntity>() {
                    @Override
                    public void success(ClientInfoEntity result) {
                        Toast.makeText(MainActivity.this, "ClientInfo = "+result.getData().getClient_name(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MainActivity.this, "clientInfo Faliure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(getApplicationContext(), error.ErrorMessage, Toast.LENGTH_LONG).show();

            }
        });



    }

    private void getFCMToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
      cidaas.setFCMToken(token);




        Timber.i("FCM TOKEN" + token);
        Toast.makeText(this, "Token"+token, Toast.LENGTH_SHORT).show();
        // save device info
        //getPresenter().saveDeviceInfo(token, getDeviceId());

    }


    //get Tenant Info
    public void getTenantInfo(View view){
        cidaas.getTenantInfo(new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Toast.makeText(MainActivity.this, "Tenenat info = "+result.getData().getTenant_name(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //openWebView

    public void openWebview(View view)
    {


        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra("key","Normal");
        startActivity(intent);



    }



    //Reset Password
    public void ResetPassword(View v)
    {

        Intent intent=new Intent(MainActivity.this,ForgotPassword.class);
        startActivity(intent);
    }

    // redirect To register
    public void redirectToRegister(View view){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    //Redierct to Login Page
    public void redirectToLoginPage(View view)
    {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int message = intent.getIntExtra("TOTP",27);
            Toast.makeText(context, "Message"+message, Toast.LENGTH_SHORT).show();
            Log.d("receiver", "Got message: " + message);
        }
    };








    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


        public void listenTOTP(View view)
        {
            try
            {
                cidaas.listenTOTP("");
            }
            catch (Exception e){

            }
        }



    //Redirect to Enroll
    public void redirectToEnrollMFA(View view){
        Intent intent=new Intent(MainActivity.this,EnrollPattern.class);
        //intent.putExtra("sub",sub);
    }


    public void passwordlessSmartpush(View view){
        /*cidaas.loginWithSmartPush("raja.narayanan@widas.in", "", "", requestId, null, UsageType.PASSWORDLESS, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                Toast.makeText(MainActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, "Error Message:"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

            cidaas.configureFingerprint(MainActivity.this,"sub","",null, new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Override
    public void showLoader() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setTitle("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public void documentScanner(View view)
    {
      //  cidaas.startDocumentScanner(this);

    }

    public void callLocalAuthentication(View view)
    {
        cidaas.localAuthentication(this, new Result<LocalAuthenticationEntity>() {
            @Override
            public void success(LocalAuthenticationEntity result) {
                Toast.makeText(MainActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void printhashkey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.widasrnarayanan.cidaas_sdk_androidv2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        cidaasGoogle.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cidaasGoogle.onStop();
    }


    public void enableFacebookOnly(View view)
    {

        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra("key","NativeFacebook");
        startActivity(intent);
    }

    public void enableGoogleOnly(View view)
    {

        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra("key","NativeGoogle");
        startActivity(intent);
    }

    public void enableBoth(View view)
    {

        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra("key","both");
        startActivity(intent);
    }




    public void nativeFacebook(View view)
    {

        printhashkey();




        cidaasFacebook.login(new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                Toast.makeText(MainActivity.this, "Native Facebook Succs"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void logout(View view)
    {
        cidaasGoogle.logout();
        logoutButton.setVisibility(View.INVISIBLE);
      //  cidaasFacebook.logout();
    }


    public void nativeGoogle(View view)
    {

        printhashkey();




        cidaasGoogle.login(new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                logoutButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Native google Succs"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MainActivity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9001) {
            cidaasGoogle.authorize(requestCode, resultCode, data);

        }
        else {
            cidaasFacebook.authorize(requestCode, resultCode, data);
        }

       cidaas.onActivityResult(requestCode,resultCode,data);
    }
}
