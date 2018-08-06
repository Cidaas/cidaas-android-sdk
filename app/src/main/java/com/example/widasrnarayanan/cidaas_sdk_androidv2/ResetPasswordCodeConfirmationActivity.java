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
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;

public class ResetPasswordCodeConfirmationActivity extends AppCompatActivity {

    String resetRequestId;
    Cidaas cidaas;
    EditText codeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_code_confirmation);

         codeTextbox=findViewById(R.id.resetPasswordCode);

        Intent intent=getIntent();
        resetRequestId=intent.getStringExtra("resetRequestId");

        cidaas=new Cidaas(this);
    }

    public void verifyCode(View view){
        String verificationCode=codeTextbox.getText().toString();

   /*     cidaas.resetPasswordValidateCode(resetRequestId, verificationCode, new Result<ResetPasswordValidateCodeResponseEntity>() {
            @Override
            public void success(ResetPasswordValidateCodeResponseEntity result) {
                Intent intent=new Intent(ResetPasswordCodeConfirmationActivity.this,ResetNewPasswordActivty.class);
                intent.putExtra("ExchangeId",result.getData().getExchangeId());
                intent.putExtra("ResetRequestId",result.getData().getResetRequestId());
                startActivity(intent);
                Toast.makeText(ResetPasswordCodeConfirmationActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ResetPasswordCodeConfirmationActivity.this, "Fails"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
   */ }
}
