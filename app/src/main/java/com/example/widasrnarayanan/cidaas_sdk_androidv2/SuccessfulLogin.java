package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA.EnrollPattern;

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
    }

    public void changePassword(View view){

        Intent intent=new Intent(SuccessfulLogin.this,ChangeOldPasswordActivity.class);
        intent.putExtra("sub",sub);
        intent.putExtra("accessToken",accessToken);
        startActivity(intent);

    }
    public void SetUpPattern(View view){

        Intent intent=new Intent(SuccessfulLogin.this,EnrollPattern.class);
        intent.putExtra("sub",sub);
        intent.putExtra("accessToken",accessToken);
        startActivity(intent);

    }
}
