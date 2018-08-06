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

public class SMSMFAActivity extends AppCompatActivity {

    Cidaas cidaas;
    String statusId,deviceID,trackid,consentName,sub;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsmfa);

        verificationCodeTextbox=findViewById(R.id.smsVerificationCode);
        cidaas=new Cidaas(this);
        Intent intent=getIntent();

        statusId=intent.getStringExtra("statusId");
        deviceID=intent.getStringExtra("deviceID");
        trackid=intent.getStringExtra("trackid");
        consentName=intent.getStringExtra("consentName");
        sub=intent.getStringExtra("sub");
    }

    public void smsVerifyCode(View view){
        String verificationCode=verificationCodeTextbox.getText().toString();
        cidaas.verifySMS(verificationCode, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity authresult) {

                Toast.makeText(SMSMFAActivity.this, ""+authresult.getData().getAccess_token(), Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(SMSMFAActivity.this,SuccessfulLogin.class);
                intent.putExtra("sub",sub);
                intent.putExtra("accessToken",authresult.getData().getAccess_token());
                startActivity(intent);
                    /*ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                     resumeLoginRequestEntity.setSub(authresult.getData().getSub());
                    resumeLoginRequestEntity.setTrack_id(trackid);
                    resumeLoginRequestEntity.setTrackingCode(authresult.getData().getTrackingCode());
                    resumeLoginRequestEntity.setVerificationType("SMS");
                    resumeLoginRequestEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                    cidaas.resumeLogin(resumeLoginRequestEntity, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {


                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(SMSMFAActivity.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*/


               // Toast.makeText(SMSMFAActivity.this, "Sub"+result.getData().getSub()+" Tracking code = "+result.getData().getTrackingCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(SMSMFAActivity.this, "Result Failure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }
}
