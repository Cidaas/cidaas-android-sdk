/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import widas.raja.cidaasconsentv2.Presenter.CidaasConsent;
import widas.raja.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import widas.raja.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import widas.raja.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;


public class ConsentUrlActivity extends AppCompatActivity {

    Cidaas cidaas;
    TextView title,description,user_agreement;
   // WebView webView;
    String consentVersionId,consentId,sub,url,trackid;
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
         consentVersionId = intent.getStringExtra("consentVersionId");
        String requestId = intent.getStringExtra("requestId");
         sub=intent.getStringExtra("sub");
         consentId = intent.getStringExtra("consentId");
         trackid=intent.getStringExtra("trackid");



            ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity=new ConsentDetailsV2RequestEntity(sub, requestId,trackid,consentId,consentVersionId);


            CidaasConsent.getInstance(getApplicationContext()).getConsentDetailsV2(consentDetailsV2RequestEntity, new Result<ConsentDetailsV2ResponseEntity>() {
                @Override
                public void success(ConsentDetailsV2ResponseEntity result) {
                    Toast.makeText(ConsentUrlActivity.this, ""+result.getData().getConsent_name(), Toast.LENGTH_SHORT).show();

                  */
/*  String titleFor=result.getData().getConsent_name()
                    title.setText(titleFor);
                    description.setText(((LinkedHashMap) result).get("description").toString());
                    user_agreement.setText(((LinkedHashMap) result).get("userAgreeText").toString());*//*



                    String titleFor=result.getData().getConsent_name();
                    title.setText(titleFor);
                    description.setText(result.getData().getContent());
                    //user_agreement.setText();


                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(ConsentUrlActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        */
/*    cidaas.getConsentDetails(consentName,new Result<ConsentDetailsResultEntity>() {


                @Override
                public void success(ConsentDetailsResultEntity result) {
*//*
*/
/*
                    String titleFor=((LinkedHashMap) result).get("title").toString();
                    title.setText(titleFor);
                    description.setText(((LinkedHashMap) result).get("description").toString());
                    user_agreement.setText(((LinkedHashMap) result).get("userAgreeText").toString());*//*
*/
/*


                    String titleFor=result.getData().getName();
                    title.setText(titleFor);
                    description.setText(result.getData().getDescription());
                    user_agreement.setText(result.getData().getUserAgreeText());


                    Toast.makeText(ConsentUrlActivity.this, result.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(ConsentUrlActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });*//*

    }


    public void acceptButtononClick(View view){
      */
/*   ConsentEntity consentAcceptRequestEntity=new ConsentEntity(consentName,version,sub,trackid,true);
        consentAcceptRequestEntity.setConsentVersion(version);
        consentAcceptRequestEntity.setSub(sub);
        consentAcceptRequestEntity.setConsentName(consentName);
        consentAcceptRequestEntity.setAccepted(true);*//*


        AcceptConsentV2Entity acceptConsentV2Entity=new AcceptConsentV2Entity(sub,consentId,consentVersionId,trackid);

        CidaasConsent.getInstance(getApplicationContext()).acceptConsentV2(acceptConsentV2Entity, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                Toast.makeText(ConsentUrlActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConsentUrlActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       */
/*  cidaas.loginAfterConsent(consentAcceptRequestEntity, new Result<LoginCredentialsResponseEntity>() {
             @Override
             public void success(LoginCredentialsResponseEntity result) {
                 Toast.makeText(ConsentUrlActivity.this, "consent Management is accepted", Toast.LENGTH_SHORT).show();

               *//*
*/
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
                 });*//*
*/
/*
               *//*
*/
/*  Intent intent=new Intent(ConsentUrlActivity.this,LoginActivity.class);
                 startActivity(intent);*//*
*/
/*
             }

             @Override
             public void failure(WebAuthError error) {
                 Toast.makeText(ConsentUrlActivity.this, "consent Management is Failed", Toast.LENGTH_SHORT).show();
             }
         });*//*

    }
}
*/
