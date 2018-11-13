package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;

public class IVRConfigActivity extends AppCompatActivity {


    Cidaas cidaas;
    String sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivrconfig);
        cidaas=new Cidaas(this);

        sub=getIntent().getStringExtra("sub");
    }

    public void clickVerifyIVRConfig(View view)
    {
        cidaas.configureIVR(sub, new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {
                Toast.makeText(IVRConfigActivity.this, "Success of IVR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(IVRConfigActivity.this, "Failure of IVR"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
