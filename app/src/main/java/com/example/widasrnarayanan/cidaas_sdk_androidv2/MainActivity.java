package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Loaders.ICustomLoader;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA.EnrollPattern;
import com.google.firebase.iid.FirebaseInstanceId;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements ICustomLoader {

    ProgressDialog progressDialog;
     Cidaas cidaas;
     String requestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         cidaas = Cidaas.getInstance(this);
         Cidaas.loader=this;
         getFCMToken();

        String url = getIntent().getDataString();
        if (url != null) {
            cidaas.resume(url);

        }
        else {
        }
    }

   /* @Override
    protected void onResume() {
        super.onResume();
       *//*if(cidaas!=null) {
            String url = getIntent().getDataString();
            if (url != null) {
                cidaas.resume(url);

            }
            else
            {
                Toast.makeText(getApplicationContext(), "URL null", Toast.LENGTH_LONG).show();
            }
        }
       else
       {
           Toast.makeText(getApplicationContext(), "OBJECT NULL", Toast.LENGTH_LONG).show();
       }*//*
    }*/


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
                Toast.makeText(MainActivity.this, "Request id Failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //get ClientInfo
    public void getClientInfo(View view){
        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
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

    //Redirect to Enroll
    public void redirectToEnrollMFA(View view){
        Intent intent=new Intent(MainActivity.this,EnrollPattern.class);
        //intent.putExtra("sub",sub);
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
}
