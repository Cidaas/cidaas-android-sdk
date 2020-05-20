/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.presentation.View.CidaasVerification;

import androidx.appcompat.app.AppCompatActivity;

public class EmailAccountVerification extends AppCompatActivity {


    Cidaas cidaas;
  //  String accvid;
    String exchangeId,statusId,sub;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_account_verification);


        cidaas=new Cidaas(this);
        verificationCodeTextbox=findViewById(R.id.emailaccountverification);
        Intent intent=getIntent();

       // accvid=intent.getStringExtra("accvid");

        statusId=intent.getStringExtra("status_id");
        exchangeId=intent.getStringExtra("exchange_id");
        sub=intent.getStringExtra("sub");
        LoginEntity loginEntity=new LoginEntity();
    }
    public void ButtonClickVerifyEmail(View view){
        try {
            String verificationCode = verificationCodeTextbox.getText().toString();
            */
/*if (accvid != null && accvid!="") {
                cidaas.verifyAccount( verificationCode, accvid,new Result<RegisterUserAccountVerifyResponseEntity>() {
                    @Override
                    public void success(RegisterUserAccountVerifyResponseEntity result) {
                        Toast.makeText(EmailAccountVerification.this, "Success"+result.getStatus(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EmailAccountVerification.this, "Error on Verifying"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                }

                @Override
                public void failure(WebAuthError error) {

                }
            });

            }*//*


            if (statusId != null && !statusId.equals("") && exchangeId != null && !exchangeId.equals("")) {
                CidaasVerification.getInstance(getApplicationContext()).enrollIVR(verificationCode, sub, exchangeId, new Result<EnrollResponse>() {
                    @Override
                    public void success(EnrollResponse result) {
                        Toast.makeText(EmailAccountVerification.this, ""+result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EmailAccountVerification.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
*/
