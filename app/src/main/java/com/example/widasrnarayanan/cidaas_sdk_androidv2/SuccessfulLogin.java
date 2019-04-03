package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA.EnrollPattern;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessfulLogin extends AppCompatActivity {

    Cidaas cidaas;
    String sub,accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);

        Intent intent=getIntent();
        sub=intent.getStringExtra("sub");
        accessToken=intent.getStringExtra("accessToken");

        cidaas=new Cidaas(this);
    }

    public void changePassword(View view){

        Intent intent=new Intent(SuccessfulLogin.this,ChangeOldPasswordActivity.class);
        intent.putExtra("sub",sub);
        intent.putExtra("accessToken",accessToken);
        startActivity(intent);

    }

      public void  sendVerificationCodeToEmail(View view)
    {
        cidaas.initiateSMSVerification(sub, new Result<RegisterUserAccountInitiateResponseEntity>() {
            @Override
            public void success(RegisterUserAccountInitiateResponseEntity result) {
                Toast.makeText(SuccessfulLogin.this, "Success"+result.getData().getAccvid(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SuccessfulLogin.this,EmailAccountVerification.class);
                intent.putExtra("sub",sub);
                intent.putExtra("accvid",result.getData().getAccvid());
                startActivity(intent);
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(SuccessfulLogin.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetUpPattern(View view){

        Intent intent=new Intent(SuccessfulLogin.this,EnrollPattern.class);
        intent.putExtra("sub",sub);
        intent.putExtra("accessToken",accessToken);
        startActivity(intent);

    }


    public void ConfigIVR(View view){

     /*   Intent intent=new Intent(SuccessfulLogin.this,IVRConfigActivity.class);
        intent.putExtra("sub",sub);
        intent.putExtra("accessToken",accessToken);
        startActivity(intent);*/


        cidaas.configureSMS(sub, new Result<SetupSMSMFAResponseEntity>() {
            @Override
            public void success(SetupSMSMFAResponseEntity result) {
                Toast.makeText(SuccessfulLogin.this, "Success SMS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(SuccessfulLogin.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
