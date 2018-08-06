package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

public class BackupCodeMFAActivity extends AppCompatActivity {


    Cidaas cidaas;
    String sub,trackid;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_code_mfa);

        verificationCodeTextbox=findViewById(R.id.backupcodeVerificationCode);
        cidaas=new Cidaas(this);
        Intent intent=getIntent();

trackid=intent.getStringExtra("trackid");
        sub=intent.getStringExtra("sub");
    }

    public void backupCodeVerifyCode(View view){

        final String verificationCode=verificationCodeTextbox.getText().toString();

        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                cidaas.loginWithBackupCode(verificationCode, "","",sub, UsageType.MFA,trackid,result.getData().getRequestId(),new Result<LoginCredentialsResponseEntity>()
                {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(BackupCodeMFAActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(BackupCodeMFAActivity.this,SuccessfulLogin.class);
                        intent.putExtra("sub",sub);intent.putExtra("accessToken",result.getData().getAccess_token());
                        startActivity(intent);
                    }

                    @Override
                    public void failure(WebAuthError error) {

                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
       /* cidaas.authenticateBackupCodeMFA(statusId, verificationCode, new Result<AuthenticateBackupCodeResponseEntity>() {
            @Override
            public void success(AuthenticateBackupCodeResponseEntity result) {
               *//* Intent intent=new Intent(BackupCodeMFAActivity.this,LoginActivity.class);
                intent.putExtra("isMFASuccessfull",result.isSuccess());
                intent.putExtra("trackid",trackid);
                startActivity(intent);*//*

                if(trackid!=null && trackid!=""){
                    ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                    resumeLoginRequestEntity.setSub(result.getData().getSub());
                    resumeLoginRequestEntity.setTrack_id(trackid);
                    resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                    resumeLoginRequestEntity.setVerificationType("SMS");
                    resumeLoginRequestEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                    cidaas.resumeLogin(resumeLoginRequestEntity, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            Toast.makeText(BackupCodeMFAActivity.this, ""+result.getAccess_token(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BackupCodeMFAActivity.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getSub());intent.putExtra("accessToken",result.getAccess_token());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(BackupCodeMFAActivity.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                Toast.makeText(BackupCodeMFAActivity.this, "Sub"+result.getData().getSub()+" Tracking code = "+result.getData().getTrackingCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(BackupCodeMFAActivity.this, "Result Failure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
       */ Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }
}
