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
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    Cidaas de.cidaas;
    EditText email;
    ResetPasswordRequestEntity resetPasswordRequestEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
       de.cidaas=new Cidaas(this);
       email=findViewById(R.id.emailid);

    }

    public void intiateResetPassword(View v)
    {
        resetPasswordRequestEntity=new ResetPasswordRequestEntity();
        de.cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                resetPasswordRequestEntity.setRequestId(result.getData().getRequestId());
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ForgotPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        resetPasswordRequestEntity.setEmail(email.getText().toString());
        resetPasswordRequestEntity.setResetMedium("email");
        de.cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {



                de.cidaas.initiateResetPasswordByEmail(result.getData().getRequestId(),email.getText().toString(), new Result<ResetPasswordResponseEntity>() {
                    @Override
                    public void success(ResetPasswordResponseEntity result) {
                        if(result.getData().isReset_initiated()) {
                            Intent intent=new Intent(ForgotPassword.this,ResetPasswordCodeConfirmationActivity.class);
                            intent.putExtra("resetRequestId",result.getData().getRprq());
                            startActivity(intent);
                        }
                        Toast.makeText(ForgotPassword.this, ""+result.getData().getRprq(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ForgotPassword.this, "Reset Password "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ForgotPassword.this, "Request_id Failure "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
*/
