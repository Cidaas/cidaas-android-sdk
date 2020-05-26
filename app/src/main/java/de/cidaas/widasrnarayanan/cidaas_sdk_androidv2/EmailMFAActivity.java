/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Cidaas;
import Result;
import WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import androidx.appcompat.app.AppCompatActivity;

public class EmailMFAActivity extends AppCompatActivity {

    Cidaas de.cidaas;
    String sub,statusId;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_mfa);

        verificationCodeTextbox=findViewById(R.id.verificationCode);
        de.cidaas=new Cidaas(this);
        Intent intent=getIntent();

        sub=intent.getStringExtra("sub");
        statusId=intent.getStringExtra("statusId");

    }

    public void verifyClick(View view){
        String verificationCode=verificationCodeTextbox.getText().toString();

        de.cidaas.verifyEmail( verificationCode, statusId,new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                Intent intent=new Intent(EmailMFAActivity.this,SuccessfulLogin.class);
                intent.putExtra("accessToken",result.getData().getAccess_token());
                intent.putExtra("sub",sub);
                startActivity(intent);

                Toast.makeText(EmailMFAActivity.this, "Sub"+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EmailMFAActivity.this, "Result Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }
}
*/
