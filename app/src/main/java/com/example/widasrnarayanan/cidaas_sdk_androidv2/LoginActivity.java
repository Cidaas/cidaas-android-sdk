package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsErrorDataEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                        intent.putExtra("sub",result.getData().getSub());
                        startActivity(intent);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        // onError of Login Display the error Message
                        String consentId = "";
                        String consentVersion = "";
                        String consentVersionId = "";
                        String sub="";

                        Toast.makeText(LoginActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        //Todo handle ConsentRequired
                        if (error.getErrorMessage().equals("ConsentRequired")) {
                            consentVersion = ((LoginCredentialsErrorDataEntity) error.getError()).getConsent_version();
                            consentVersionId = ((LoginCredentialsErrorDataEntity) error.getError()).getConsent_version_id();
                            consentId = ((LoginCredentialsErrorDataEntity) error.getError()).getConsent_id();
                              sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();
                            trackId=((LoginCredentialsErrorDataEntity) error.getError()).getTrack_id();


                            final String finalSub = sub;

                            Intent intent=new Intent(LoginActivity.this,ConsentUrlActivity.class);
                            intent.putExtra("consentVersionId",consentVersionId);
                            intent.putExtra("consentVersion",consentVersion);
                            intent.putExtra("sub", finalSub);
                            intent.putExtra("trackid",trackId);
                            intent.putExtra("consentId",consentId);
                            startActivity(intent);



                      /*      cidaas.getConsentDetails(consentName, new Result<ConsentDetailsResultEntity>() {
                                @Override
                                public void success(ConsentDetailsResultEntity result) {
                                    Toast.makeText(LoginActivity.this, ""+result.getData().getVersion(), Toast.LENGTH_SHORT).show();

                                    String consentContentUrl = result.getData().getPolicyUrl();

                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    Toast.makeText(LoginActivity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


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
                        else  if (error.getErrorMessage().equals("mfa_required")) {
                            //final String sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();
                           sub=((LoginCredentialsErrorDataEntity) error.getError()).getSub();

                            trackId=((LoginCredentialsErrorDataEntity) error.getError()).getTrack_id();
                            Intent intent=new Intent(LoginActivity.this,MFAListActivity.class);
                            intent.putExtra("sub",sub);
                            intent.putExtra("trackid",trackId);
                            startActivity(intent);

                        }
                        else if(error.getErrorMessage().equals("email_not_verified")){
                           cidaas.initiateEmailVerification(sub, requestIdresult.getData().getRequestId(), new Result<RegisterUserAccountInitiateResponseEntity>() {
                               @Override
                               public void success(RegisterUserAccountInitiateResponseEntity result) {
                                   Toast.makeText(LoginActivity.this, "Email Verification Initiated", Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(LoginActivity.this,EmailAccountVerification.class);
                                   intent.putExtra("accvid",result.getData().getAccvid());
                                   startActivity(intent);

                               }

                               @Override
                               public void failure(WebAuthError error) {
                                   Toast.makeText(LoginActivity.this, "Login fails " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });
                        }
                        else {
                            Toast.makeText(LoginActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    //loginWithSmartPush
    public void loginWithSmartPush(View view)
    {
        final PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
        passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);


        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                passwordlessEntity.setRequestId(result.getData().getRequestId());
                final String Username=username.getText().toString();

                passwordlessEntity.setEmail(Username);

                cidaas.loginWithSmartPush(passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(LoginActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,SuccessfulLogin.class);
                        intent.putExtra("sub",result.getData().getSub());
                        startActivity(intent);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(LoginActivity.this, "Failure Login", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(LoginActivity.this, "Failure Request Id", Toast.LENGTH_SHORT).show();
            }
        });
        // Clear the Text Fields

    }


    public void loginWithPattern(View view)
    {
        try
        {
            final PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
            passwordlessEntity.setEmail("raja.narayanan@widas.in");

            cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    passwordlessEntity.setRequestId(result.getData().getRequestId());
                    passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                    cidaas.loginWithPatternRecognition("RED[1,2,3,4]",passwordlessEntity , new Result<LoginCredentialsResponseEntity>() {
                        @Override
                        public void success(LoginCredentialsResponseEntity result) {
                            Toast.makeText(LoginActivity.this, "Success Login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getData().getSub());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(LoginActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(LoginActivity.this, "Request ID Faliure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
        catch (Exception e)
        {

        }
    }

    public void loginWithView(View view)
    {
        try
        {

            Cidaas.getInstance(this).getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                    passwordlessEntity.setEmail("raja.narayanan@widas.in");
                    passwordlessEntity.setSub("825ef0f8-4f2d-46ad-831d-08a30561305d");
                    passwordlessEntity.setRequestId(result.getData().getRequestId());
                    passwordlessEntity.setMobile("+919787113989");
                    passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

                    cidaas.loginWithEmail(passwordlessEntity, new Result<InitiateEmailMFAResponseEntity>() {
                        @Override
                        public void success(InitiateEmailMFAResponseEntity result) {
                            Toast.makeText(LoginActivity.this, "Success"+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {

                }
            });

        }
        catch (Exception e)
        {

        }
    }

}
