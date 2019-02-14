package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cidaasv2.Controller.CidaasSDKLayout;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Interface.ILoader;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import widaas.cidaas.rajanarayanan.cidaasfacebookv2.CidaasFacebook;
import widaas.cidaas.rajanarayanan.cidaasgooglev2.CidaasGoogle;

public class Main2Activity extends AppCompatActivity  implements ILoader {

    RelativeLayout relativeLayout;
    CidaasSDKLayout cidaasSDKLayout;
    ProgressDialog progressDialog;
     CidaasFacebook cidaasFacebook;
     CidaasGoogle cidaasGoogle;

     Button logoutButton,enableGoogle,enableFacebook;



     String sub="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        relativeLayout=findViewById(R.id.relative_layout_for_webView);
        logoutButton=findViewById(R.id.buttonForWebView);


        logoutButton.setVisibility(View.INVISIBLE);


        CidaasSDKLayout.loader = this;

        String key=getIntent().getStringExtra("key");

        if((DBHelper.getShared().getAccessToken(sub)==null)) {

            if (key.equals("Normal")) {
                callMethod();
            } else if (key.equals("NativeFacebook")) {
                enableNativeFacebook();
            } else if (key.equals("NativeGoogle")) {
                enableNativeGoogle();
            } else if (key.equals("both")) {
                enableBoth();
            }
        }

        else {
            logoutButton.setVisibility(View.VISIBLE);
        }


    }

    private void enableBoth() {
        //CidaasSDKLayout cidaasSDKLayout=CidaasSDKLayout.getInstance(this);


        cidaasSDKLayout= new CidaasSDKLayout(this);

         cidaasSDKLayout.enableFacebook(this);
         cidaasSDKLayout.enableGoogle(this);


        cidaasFacebook=new CidaasFacebook(this);
        cidaasGoogle=new CidaasGoogle(this);

        cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                sub=result.getSub();

                logoutButton.setVisibility(View.VISIBLE);

                Toast.makeText(Main2Activity.this, "Success"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    public void callMethod() {


        //CidaasSDKLayout cidaasSDKLayout=CidaasSDKLayout.getInstance(this);


        cidaasSDKLayout= new CidaasSDKLayout(this);

       // cidaasSDKLayout.enableFacebook(this);
       // cidaasSDKLayout.enableGoogle(this);


      cidaasFacebook=new CidaasFacebook(this);
      cidaasGoogle=new CidaasGoogle(this);

        cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                sub=result.getSub();

                logoutButton.setVisibility(View.VISIBLE);

                Toast.makeText(Main2Activity.this, "Success"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void enableNativeFacebook()
    {
        cidaasSDKLayout= new CidaasSDKLayout(this);

         cidaasSDKLayout.enableFacebook(this);
        // cidaasSDKLayout.enableGoogle(this);


        cidaasFacebook=new CidaasFacebook(this);
        cidaasGoogle=new CidaasGoogle(this);

        cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                sub=result.getSub();

                logoutButton.setVisibility(View.VISIBLE);

                Toast.makeText(Main2Activity.this, "Success"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enableNativeGoogle()
    {
        cidaasSDKLayout= new CidaasSDKLayout(this);

        // cidaasSDKLayout.enableFacebook(this);
         cidaasSDKLayout.enableGoogle(this);


        cidaasFacebook=new CidaasFacebook(this);
        cidaasGoogle=new CidaasGoogle(this);

        cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {
                sub=result.getSub();

                logoutButton.setVisibility(View.VISIBLE);

                Toast.makeText(Main2Activity.this, "Success"+result.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void logout(View view)
    {
        cidaasSDKLayout.logout(sub,"https://www.google.com", new Result<String>() {
            @Override
            public void success(String result) {
                Toast.makeText(Main2Activity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                logoutButton.setVisibility(View.INVISIBLE);
               /* Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);*/

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(Main2Activity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        cidaasSDKLayout.onBackPressed();

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
        if(requestCode==9001) {
            cidaasGoogle.authorize(requestCode, resultCode, data);

        }
        else {
            cidaasFacebook.authorize(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
