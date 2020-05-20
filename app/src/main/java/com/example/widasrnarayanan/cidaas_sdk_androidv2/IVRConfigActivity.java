/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;

import androidx.appcompat.app.AppCompatActivity;

public class IVRConfigActivity extends AppCompatActivity {


    Cidaas cidaas;
    String sub,statusid;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivrconfig);
        cidaas=new Cidaas(this);

        editText=findViewById(R.id.textView6);
        sub=getIntent().getStringExtra("sub");
    }

    public void clickVerifyIVRConfig(View view)
    {
        cidaas.configureIVR(sub, new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {
                statusid=result.getData().getStatusId();

                Toast.makeText(IVRConfigActivity.this, "Success of IVR", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(IVRConfigActivity.this, "Failure of IVR"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void clickConfirmButton(View view)
    {
        String code=editText.getText().toString();


        cidaas.enrollIVR(code, statusid, new Result<EnrollIVRMFAResponseEntity>() {
                    @Override
                    public void success(EnrollIVRMFAResponseEntity result) {
                        Toast.makeText(IVRConfigActivity.this, "Success of Login IVR", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(IVRConfigActivity.this, "Failure of  Login IVR"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }
}
*/
