package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsErrorDataEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;

public class LoginActivity extends AppCompatActivity {

    //Declare Global Variables
    Cidaas cidaas;

    //Declare the UI elements
     EditText username,password;
     Button loginButton,ClearButton;
     String trackId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Intialise the UI elements
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        //Intialise variables
        cidaas=new Cidaas(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        if(intent!=null){

            boolean isMFASuccessfull=intent.getBooleanExtra("is MFA Successfull",false);
            if(isMFASuccessfull){

            }
        }

    }

    //Perform Login
    public void loginButtonFunction(View view)
    {
        // Perform Login

        // Get the username and password from textbox
         final String Username=username.getText().toString();
         final String Password=password.getText().toString();

        // call the requestId method
        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(final AuthRequestResponseEntity requestIdresult) {
                // onSuccess of RequestId call the login with Credentials by psssing username,password request id as Arguments
                LoginEntity loginEntity=new LoginEntity();
                loginEntity.setPassword(Password);
                loginEntity.setUsername(Username);
                loginEntity.setUsername_type("email");
                cidaas.loginWithCredentials(requestIdresult.getData().getRequestId(), loginEntity,new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        // onSuccess of Login go to next Activity and Display the Access Token
                        Toast.makeText(LoginActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,SuccessfulLogin.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        // onError of Login Display the error Message
                        String consentName = "";
                        String consentVersion = "";
                        String sub="";


                        //Todo handle ConsentRequired
                        if (error.ErrorMessage.equals("ConsentRequired")) {
                            consentName = ((LoginCredentialsErrorDataEntity) error.getError()).getConsent_name();
                              consentVersion = ((LoginCredentialsErrorDataEntity) error.getError()).getConsent_version();
                              sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();
                            trackId=((LoginCredentialsErrorDataEntity) error.getError()).getTrack_id();


/*

                            cidaas.getConsentURL(consentName, consentVersion, new Result<String>() {
                                @Override
                                public void success(String result) {

                                    String consentContentUrl = result;
                                    Intent intent=new Intent(LoginActivity.this,ConsentUrlActivity.class);
                                    intent.putExtra("consentName",consentName);
                                    intent.putExtra("consentVersion",consentVersion);
                                    intent.putExtra("sub",sub);
                                    intent.putExtra("trackid",trackId);
                                    intent.putExtra("url",consentContentUrl);
                                    startActivity(intent);
                                   // Toast.makeText(LoginActivity.this, consentContentUrl, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    Toast.makeText(LoginActivity.this, "Login fails " + error.ErrorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
*/


                        }
                        else  if (error.ErrorMessage.equals("mfa_required")) {
                            //final String sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();
                           sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();

                            trackId=((LoginCredentialsErrorDataEntity) error.getError()).getTrack_id();
                            Intent intent=new Intent(LoginActivity.this,MFAListActivity.class);
                            intent.putExtra("sub",sub);
                            intent.putExtra("trackid",trackId);
                            intent.putExtra("consentName",consentName);
                            intent.putExtra("consentVersion",consentVersion);
                            startActivity(intent);

                        }
                        else if(error.ErrorMessage.equals("email_not_verified")){
                           cidaas.initiateAccountVerificationByEmail(sub, requestIdresult.getData().getRequestId(), new Result<RegisterUserAccountInitiateResponseEntity>() {
                               @Override
                               public void success(RegisterUserAccountInitiateResponseEntity result) {
                                   Toast.makeText(LoginActivity.this, "Email Verification Initiated", Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(LoginActivity.this,EmailAccountVerification.class);
                                   intent.putExtra("accvid",result.getData().getAccvid());
                                   startActivity(intent);

                               }

                               @Override
                               public void failure(WebAuthError error) {
                                   Toast.makeText(LoginActivity.this, "Login fails " + error.ErrorMessage, Toast.LENGTH_SHORT).show();
                               }
                           });
                        }

                    }

                });
            }


            @Override
            public void failure(WebAuthError error) {
            // on Error Display the error message
                Toast.makeText(LoginActivity.this, "RequestId Fails"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void callResumeLogin(@NonNull ResumeLoginRequestEntity resumeLoginRequestEntity , @NonNull final Result<AccessTokenEntity> result){

    }
    //Clear
    public void clearButtonFunction(View view)
    {
        // Clear the Text Fields
        username.setText("");
        password.setText("");
    }

}
