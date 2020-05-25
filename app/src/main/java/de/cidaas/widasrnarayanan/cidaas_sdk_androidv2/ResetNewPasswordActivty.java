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
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;

import androidx.appcompat.app.AppCompatActivity;

public class ResetNewPasswordActivty extends AppCompatActivity {


    String resetRequestId,exchangeId;
    Cidaas de.cidaas;
    EditText passwordText,confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_new_password_activty);

        passwordText=findViewById(R.id.newpassword);
        confirmPasswordText=findViewById(R.id.confirmpassword);

        Intent intent=getIntent();
        resetRequestId=intent.getStringExtra("ResetRequestId");
        exchangeId=intent.getStringExtra("ExchangeId");

        de.cidaas=new Cidaas(this);
    }

    public void resetPassword(View view)
    {
        String newPassword=passwordText.getText().toString();
        String confirmPassword=confirmPasswordText.getText().toString();

        if(newPassword.equals(confirmPassword)) {

            ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();
            resetPasswordEntity.setPassword(newPassword);
            resetPasswordEntity.setConfirmPassword(confirmPassword);
            resetPasswordEntity.setExchangeId(exchangeId);
            resetPasswordEntity.setResetRequestId(resetRequestId);


            de.cidaas.resetPassword(resetPasswordEntity, new Result<ResetNewPasswordResponseEntity>() {
                @Override
                public void success(ResetNewPasswordResponseEntity result) {
                    Toast.makeText(ResetNewPasswordActivty.this, "Password Changed SuccessFully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(ResetNewPasswordActivty.this, "Change Password  Failed" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Password and Confirm Password must be same", Toast.LENGTH_SHORT).show();
        }
    }
}
*/
