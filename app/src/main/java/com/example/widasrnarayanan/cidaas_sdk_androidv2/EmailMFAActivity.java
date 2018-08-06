package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

public class EmailMFAActivity extends AppCompatActivity {

    Cidaas cidaas;
    String sub;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_mfa);

        verificationCodeTextbox=findViewById(R.id.verificationCode);
        cidaas=new Cidaas(this);
        Intent intent=getIntent();

        sub=intent.getStringExtra("sub");

    }

    public void verifyClick(View view){
        String verificationCode=verificationCodeTextbox.getText().toString();

        cidaas.verifyEmail( verificationCode, new Result<LoginCredentialsResponseEntity>() {
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
                Toast.makeText(EmailMFAActivity.this, "Result Failure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }
}
