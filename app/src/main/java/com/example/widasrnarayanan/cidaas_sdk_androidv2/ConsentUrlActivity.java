package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.ConsentAcceptRequestEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultDataEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsErrorDataEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

public class ConsentUrlActivity extends AppCompatActivity {

    Cidaas cidaas;
    TextView title,description,user_agreement;
   // WebView webView;
    String consentName,version,sub,url,trackid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_url);

        cidaas=new Cidaas(this);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        user_agreement=findViewById(R.id.userAgreement);
      //  webView=findViewById(R.id.webview);


        Intent intent=getIntent();
         consentName=intent.getStringExtra("consentName");
         version=intent.getStringExtra("consentVersion");
         sub=intent.getStringExtra("sub");
         url=intent.getStringExtra("url");
         trackid=intent.getStringExtra("trackid");

        // webView.loadUrl(url);
        if(consentName!=null) {




            cidaas.getConsentDetails(consentName, version,trackid,new Result<ConsentDetailsResultEntity>() {


                @Override
                public void success(ConsentDetailsResultEntity result) {
/*
                    String titleFor=((LinkedHashMap) result).get("title").toString();
                    title.setText(titleFor);
                    description.setText(((LinkedHashMap) result).get("description").toString());
                    user_agreement.setText(((LinkedHashMap) result).get("userAgreeText").toString());*/


                    String titleFor=result.getData().getTitle();
                    title.setText(titleFor);
                    description.setText(result.getData().getDescription());
                    user_agreement.setText(result.getData().getUserAgreeText());


                    Toast.makeText(ConsentUrlActivity.this, result.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(ConsentUrlActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void acceptButtononClick(View view){
         ConsentAcceptRequestEntity consentAcceptRequestEntity=new ConsentAcceptRequestEntity();
        consentAcceptRequestEntity.setConsentVersion(version);
        consentAcceptRequestEntity.setSub(sub);
        consentAcceptRequestEntity.setConsentName(consentName);
        consentAcceptRequestEntity.setAccepted(true);

         cidaas.loginAfterConsent(sub,true, new Result<LoginCredentialsResponseEntity>() {
             @Override
             public void success(LoginCredentialsResponseEntity result) {
                 Toast.makeText(ConsentUrlActivity.this, "consent Management is accepted", Toast.LENGTH_SHORT).show();

               /*  ResumeConsentRequestEntity resumeConsentRequestEntity=new ResumeConsentRequestEntity();

                 resumeConsentRequestEntity.setName(consentName);
                 resumeConsentRequestEntity.setSub(sub);
                 resumeConsentRequestEntity.setTrack_id(trackid);
                 resumeConsentRequestEntity.setVersion(version);

                 cidaas.resumeConsent(resumeConsentRequestEntity, new Result<AccessTokenEntity>() {
                     @Override
                     public void success(AccessTokenEntity result) {
                         Toast.makeText(ConsentUrlActivity.this, "AccessToken="+result.getAccess_token(), Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void failure(WebAuthError error) {
                         Toast.makeText(ConsentUrlActivity.this, "Error message "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                         if (error.ErrorMessage.equals("mfa_required")) {
                             final String sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();
                             Intent intent=new Intent(ConsentUrlActivity.this,MFAListActivity.class);
                             intent.putExtra("sub",sub);
                             intent.putExtra("trackid",trackid);
                             intent.putExtra("consentName",consentName);
                             intent.putExtra("version",version);
                             startActivity(intent);

                         }
                     }
                 });*/
               /*  Intent intent=new Intent(ConsentUrlActivity.this,LoginActivity.class);
                 startActivity(intent);*/
             }

             @Override
             public void failure(WebAuthError error) {
                 Toast.makeText(ConsentUrlActivity.this, "consent Management is Failed", Toast.LENGTH_SHORT).show();
             }
         });
    }
}
