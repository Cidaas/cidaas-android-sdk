package com.widas.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import de.cidaas.sdk.android.Cidaas;
import de.cidaas.sdk.android.CidaasSDKLayout;
import de.cidaas.sdk.android.cidaasnative.view.CidaasNative;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;

public class MainActivity extends AppCompatActivity {
    CidaasNative cidaasNative;
    Cidaas cidaas;
    RelativeLayout relativeLayout;
    CidaasSDKLayout cidaasSDKLayout;
    SharedPreference sharedPreference;

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
}