package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Repository.RequestId.RequestIdService;

public class Smartpush extends AppCompatActivity {

    Cidaas cidaas;
    EditText smartpush;
    String statusId,deviceID,trackid,RandomNumber,sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartpush);
        cidaas=new Cidaas(this);
        smartpush=findViewById(R.id.smartpushtext);

        Intent intent=getIntent();

        statusId=intent.getStringExtra("statusId");
        deviceID=intent.getStringExtra("deviceID");
        trackid=intent.getStringExtra("trackid");
        RandomNumber=intent.getStringExtra("RandomNumber");
        sub=intent.getStringExtra("sub");



    }
    public void verifyPush(View view){
     final String smartpushNumber=smartpush.getText().toString();
        String accessToken="eyJhbGciOiJSUzI1NiIsImtpZCI6IjEwMjM2ZWZiLWRlMjEtNDI5Mi04ZDRlLTRmZGIxNjhhZDg4ZSJ9.eyJzaWQiOiIzYTRkYTMxMS00MDE4LTRjYTAtYjRjYy1jMTQwZWJhODE2MzMiLCJzdWIiOiI4MjVlZjBmOC00ZjJkLTQ2YWQtODMxZC0wOGEzMDU2MTMwNWQiLCJpc3ViIjoiODYzODdmYTUtNTNhMi00ODY5LWJiMjItZmNjZmMzOGFhOGNiIiwiYXVkIjoiNGQ1ZTZlMjAtOTM0Ny00MjU1LTk3OTAtNWI3MTk2ODQzMTAzIiwiaWF0IjoxNTMxODE5NDEwLCJhdXRoX3RpbWUiOjE1MzE4MTkzOTUsImlzcyI6Imh0dHBzOi8vbmlnaHRseWJ1aWxkLmNpZGFhcy5kZSIsImp0aSI6IjdhZGMzZmQ3LWNlNzgtNDI2MS1hMzcwLWE5YjE4MTk3YTYxMSIsIm5vbmNlIjoiMTIzNDUiLCJzY29wZXMiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIiwib2ZmbGluZV9hY2Nlc3MiXSwicm9sZXMiOlsiVVNFUiJdLCJyb2xlIjoiVVNFUiIsImNsaWVudGlkIjoiNGQ1ZTZlMjAtOTM0Ny00MjU1LTk3OTAtNWI3MTk2ODQzMTAzIiwiZXhwIjoxNTMxOTA1ODEwOTU1LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIG9mZmxpbmVfYWNjZXNzIiwiZW1haWwiOiJyYWphLm5hcmF5YW5hbkB3aWRhcy5pbiIsImdpdmVuX25hbWUiOiJSYWphIHJuIn0.pk1DruFNhRoct56TyRATaVhUqxzs0u21Kv0vFdPOsINaZWcI0HoN9bQim6lz8rqRQXdmAL1VsWXXBfN3g0I9-GZgHGbjqruuK36--FZM8C9n0tpa5jCgmD8gaScM5rDNGRqqIazEehy0YsldlGXd4CAdFlGZckAPBt3kyinMtp8";
     cidaas.getAccessToken(sub, new Result<AccessTokenEntity>() {
         @Override
         public void success(AccessTokenEntity result) {

         }

         @Override
         public void failure(WebAuthError error) {
             Toast.makeText(Smartpush.this, "Access Token Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
         }
     });

     cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
         @Override
         public void success(AuthRequestResponseEntity result) {
             cidaas.loginWithSmartPush("",sub,"", result.getData().getRequestId(),trackid,UsageType.PASSWORDLESS,new Result<LoginCredentialsResponseEntity>() {
                 @Override
                 public void success(LoginCredentialsResponseEntity result) {
                     if(trackid!=null && trackid!=""){
                         ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                         //resumeLoginRequestEntity.setSub(result.getData().getSub());
                         resumeLoginRequestEntity.setTrack_id(trackid);
                         // resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                         resumeLoginRequestEntity.setVerificationType("PUSH");
                         resumeLoginRequestEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                   /* cidaas.resumeLogin(resumeLoginRequestEntity, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            Toast.makeText(Smartpush.this, ""+result.getAccess_token(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Smartpush.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getSub());
                            intent.putExtra("accessToken",result.getAccess_token());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(Smartpush.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
*/
                     }
                     else
                     {
                         Toast.makeText(Smartpush.this, "Track id is null"+trackid, Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void failure(WebAuthError error) {
                     Toast.makeText(Smartpush.this, "Push"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                 }
             });


         }

         @Override
         public void failure(WebAuthError error) {

         }
     });



    }
}
