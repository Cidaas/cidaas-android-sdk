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
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;

public class ResetNewPasswordActivty extends AppCompatActivity {


    String resetRequestId,exchangeId;
    Cidaas cidaas;
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

        cidaas=new Cidaas(this);
    }

    public void resetPassword(View view)
    {
        String newPassword=passwordText.getText().toString();
        String confirmPassword=confirmPasswordText.getText().toString();

        if(newPassword.equals(confirmPassword)) {

            ResetNewPasswordRequestEntity resetNewPasswordRequestEntity = new ResetNewPasswordRequestEntity();
            resetNewPasswordRequestEntity.setPassword(newPassword);
            resetNewPasswordRequestEntity.setConfirmPassword(confirmPassword);
            resetNewPasswordRequestEntity.setExchangeId(exchangeId);
            resetNewPasswordRequestEntity.setResetRequestId(resetRequestId);


       /*     cidaas.resetNewPassword(resetNewPasswordRequestEntity, new Result<ResetNewPasswordResponseEntity>() {
                @Override
                public void success(ResetNewPasswordResponseEntity result) {
                    Toast.makeText(ResetNewPasswordActivty.this, "Password Changed SuccessFully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(ResetNewPasswordActivty.this, "Change Password  Failed" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
       */ }
        else {
            Toast.makeText(this, "Password and Confirm Password must be same", Toast.LENGTH_SHORT).show();
        }
    }
}
