package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Interface.ILoader;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import widaas.cidaas.rajanarayanan.cidaasfacebookv2.CidaasFacebook;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.CidaasGoogle;

public class Main2Activity extends AppCompatActivity implements ILoader {

    RelativeLayout relativeLayout;
    CidaasSDKLayout cidaasSDKLayout;
    ProgressDialog progressDialog;
    CidaasFacebook cidaasFacebook;
    CidaasGoogle cidaasGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        relativeLayout=findViewById(R.id.relative_layout_for_webView);

        CidaasSDKLayout.loader = this;
        callMethod();



    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    public void callMethod() {

        cidaasSDKLayout= new CidaasSDKLayout(this);

       // cidaasSDKLayout.enableFacebook(this);
        //cidaasSDKLayout.enableGoogle(this);


      cidaasFacebook=new CidaasFacebook(this);
      cidaasGoogle=new CidaasGoogle(this);
        cidaasSDKLayout.login(relativeLayout, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                Toast.makeText(Main2Activity.this, "Success"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showLoader() {
        progressDialog = new ProgressDialog(Main2Activity.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cidaasFacebook.authorize(requestCode, resultCode, data);
        cidaasGoogle.authorize(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
