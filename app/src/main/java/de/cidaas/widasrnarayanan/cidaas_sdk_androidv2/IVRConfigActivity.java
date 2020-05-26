/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Cidaas;
import Result;
import WebAuthError;
import EnrollIVRMFAResponseEntity;
import SetupIVRMFAResponseEntity;

import androidx.appcompat.app.AppCompatActivity;

public class IVRConfigActivity extends AppCompatActivity {


    Cidaas de.cidaas;
    String sub,statusid;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivrconfig);
        de.cidaas=new Cidaas(this);

        editText=findViewById(R.id.textView6);
        sub=getIntent().getStringExtra("sub");
    }

    public void clickVerifyIVRConfig(View view)
    {
        de.cidaas.configureIVR(sub, new Result<SetupIVRMFAResponseEntity>() {
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


        de.cidaas.enrollIVR(code, statusid, new Result<EnrollIVRMFAResponseEntity>() {
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
