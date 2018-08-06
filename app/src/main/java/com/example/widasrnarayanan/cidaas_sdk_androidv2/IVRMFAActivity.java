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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

public class IVRMFAActivity extends AppCompatActivity {


    Cidaas cidaas;
    String statusId,deviceID,trackid,consentName,sub;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivrmfa);

        verificationCodeTextbox=findViewById(R.id.ivrverificationcode);
        cidaas=new Cidaas(this);
        Intent intent=getIntent();

        statusId=intent.getStringExtra("statusId");
        deviceID=intent.getStringExtra("deviceID");
        trackid=intent.getStringExtra("trackid");
        consentName=intent.getStringExtra("consentName");
        sub=intent.getStringExtra("sub");
    }

    public void ivrVerifyCode(View view){
        String verificationCode=verificationCodeTextbox.getText().toString();

        cidaas.verifyIVR(verificationCode, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                Toast.makeText(IVRMFAActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(IVRMFAActivity.this,SuccessfulLogin.class);
                intent.putExtra("sub",sub);intent.putExtra("accessToken",result.getData().getAccess_token());
                startActivity(intent);
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
       /* cidaas.loginWithIVR(statusId, verificationCode, new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {
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
                            Toast.makeText(IVRMFAActivity.this, ""+result.getAccess_token(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(IVRMFAActivity.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getSub());intent.putExtra("accessToken",result.getAccess_token());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(IVRMFAActivity.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(IVRMFAActivity.this, "Track id is null"+trackid, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(IVRMFAActivity.this, "Sub"+result.getData().getSub()+" Tracking code = "+result.getData().getTrackingCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(IVRMFAActivity.this, "Result Failure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
       */ Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }
}
