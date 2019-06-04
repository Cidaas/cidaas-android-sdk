package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.os.Bundle;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.presentation.View.CidaasVerification;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    Cidaas cidaas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

      cidaas =new Cidaas(this);





    //Handle Forgot Password
        String email="raja.narayanan@widas.in";
        String Type="email";
        String Code="CODE";
        String requestID="";

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String re=result.getData().getRequestId();
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });

     ResetPasswordRequestEntity resetPasswordRequestEntity=new ResetPasswordRequestEntity();
     resetPasswordRequestEntity.setEmail(email);
     resetPasswordRequestEntity.setProcessingType(Type);


     cidaas.getDeduplicationDetails("", new Result<DeduplicationResponseEntity>() {
         @Override
         public void success(DeduplicationResponseEntity result) {
         }

         @Override
         public void failure(WebAuthError error) {

         }
     });

     cidaas.registerUser("", new Result<RegisterDeduplicationEntity>() {
         @Override
         public void success(RegisterDeduplicationEntity result) {

         }

         @Override
         public void failure(WebAuthError error) {

         }

     });



     cidaas.loginWithSocial(getApplicationContext(), "linkedin", "optinalColorParameterInColorCode", new Result<AccessTokenEntity>() {
         @Override
         public void success(AccessTokenEntity result) {

         }

         @Override
         public void failure(WebAuthError error) {

         }
     });


     cidaas.loginWithBrowser(getApplicationContext(), "optionalColorParameterInColorCode", new Result<AccessTokenEntity>() {
         @Override
         public void success(AccessTokenEntity result) {

         }

         @Override
         public void failure(WebAuthError error) {

         }
     });

     cidaas.getMFAList("your_sub", new Result<MFAListResponseEntity>() {
         @Override
         public void success(MFAListResponseEntity result) {

         }

         @Override
         public void failure(WebAuthError error) {

         }
     });

    // resetPasswordRequestEntity.setRequestId();

      //  cidaas.resetPassword();

    }


}
