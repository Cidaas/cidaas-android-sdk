package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.TOTPEntity.TOTPEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.ConfigureRequest.ConfigurationRequest;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.LoginRequest.LoginRequest;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PushEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.presentation.View.CidaasVerification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

public class ConfigureActivity extends AppCompatActivity {

    PushEntity pushNotificationEntity;
   //Sub For nightlybuild
    String sub = "825ef0f8-4f2d-46ad-831d-08a30561305d";

    //Sub For Venkat Free
  //  String sub="468ceb0a-2cce-4151-b3dc-632521158cff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        if ((getIntent() != null) && (getIntent().getSerializableExtra("NotificationData") != null)) {
            pushNotificationEntity = (PushEntity) getIntent().getSerializableExtra("NotificationData");
        }


        if (pushNotificationEntity != null) {
            if (pushNotificationEntity.getVerification_type().equals(AuthenticationType.FINGERPRINT)) {

            }
        }

       // sub = getIntent().getStringExtra("sub");

        if (getIntent() != null) {

        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TOTPListener"));
    }



    public void configureIVR(View view)
    {
         CidaasVerification.getInstance(getApplicationContext()).setupIVR(sub, new Result<SetupResponse>() {
             @Override
             public void success(SetupResponse result) {
                 Toast.makeText(ConfigureActivity.this, ""+result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();

                 Intent intent=new Intent(ConfigureActivity.this,EmailAccountVerification.class);
                 intent.putExtra("status_id",result.getData().getStatus_id());
                 intent.putExtra("exchange_id",result.getData().getExchange_id().getExchange_id());
                 intent.putExtra("sub",result.getData().getSub());
                 startActivity(intent);

             }

             @Override
             public void failure(WebAuthError error) {
                 Toast.makeText(ConfigureActivity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
             }
         });
    }



    File imageFile;
    FileOutputStream out;

    public void enrollPattern(View view) {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();

        ConfigurationRequest configurationRequest = new ConfigurationRequest(sub, "RED-1234");

        CidaasVerification.getInstance(this).configurePattern(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse enrollResponse) {

                Toast.makeText(ConfigureActivity.this, "Pattern Configured successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            TOTPEntity totpEntity = (TOTPEntity) intent.getSerializableExtra("TOTP");
            Toast.makeText(context, "Message:"+totpEntity.getTotp_string()+"Timer:"+totpEntity.getTimer_count(), Toast.LENGTH_SHORT).show();

        }
    };

    public void enrollFinger(View view) {
        final FingerPrintEntity fingerPrintEntity = new FingerPrintEntity(ConfigureActivity.this, "Authenticate to all", "Description");

        ConfigurationRequest configurationRequest = new ConfigurationRequest(sub, fingerPrintEntity);

        CidaasVerification.getInstance(this).configureFingerprint(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
                Toast.makeText(ConfigureActivity.this, "success fing" + result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error fing" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deletePattern(View view) {
        DeleteEntity deleteEntity = new DeleteEntity(sub, AuthenticationType.PATTERN);
        CidaasVerification.getInstance(this).delete(deleteEntity, new Result<DeleteResponse>() {
            @Override
            public void success(DeleteResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success Response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deletePush(View view) {
        DeleteEntity deleteEntity = new DeleteEntity(sub, AuthenticationType.SMARTPUSH);
        CidaasVerification.getInstance(this).delete(deleteEntity, new Result<DeleteResponse>() {
            @Override
            public void success(DeleteResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success Response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void enrollPush(View view) {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();

        ConfigurationRequest configurationRequest = new ConfigurationRequest(sub);

        CidaasVerification.getInstance(this).configureSmartPush(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });

    }

    public void enrollFace(View view) {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();


        File file = null;

        ConfigurationRequest configurationRequest = new ConfigurationRequest(sub, file, 0);


        CidaasVerification.getInstance(this).configureFaceRecognition(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse enrollResponse) {

                Toast.makeText(ConfigureActivity.this, "Voice Configured successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public File convertImageJpegForFace(Bitmap bitmap) {

        Bitmap picture = bitmap;
        try {
            //imageFile = file();

            Timber.d("Error");
            // Toast.makeText(FaceSetupActivity.this, imageFile.toString(), Toast.LENGTH_SHORT).show();
            if (imageFile == null) {
                // Toast.makeText(FaceSetupActivity.this, "NUll Image", Toast.LENGTH_SHORT).show();
                File sdDir = Environment.getExternalStorageDirectory();
                File pictureFileDir = new File(sdDir, "Cidaas-Faces");
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                    Toast.makeText(getApplicationContext(), "Permission Denied",
                            Toast.LENGTH_LONG).show();
                }
                imageFile = new File(pictureFileDir, "cidaas_face_photo.png");
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                imageFile.createNewFile();
                // imageFile=new File(Environment.(), "Cidaas-Faces/cidaas.png");
            }


            out = new FileOutputStream(imageFile);
            picture.compress(Bitmap.CompressFormat.JPEG, 95, out);
            return imageFile;

        } catch (Exception e) {
            Log.d("Raja file", e.getLocalizedMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.d("Raja io", e.getLocalizedMessage());
            }
        }
        return imageFile;
    }

    public void enrollVoice(View view) {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();

        File file = null;

        ConfigurationRequest configurationRequest = new ConfigurationRequest(sub, file, 0);


        CidaasVerification.getInstance(this).configureVoiceRecognition(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse enrollResponse) {

                Toast.makeText(ConfigureActivity.this, "Voice Configured successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void authenticatePattern(View view) {
        /*Cidaas.getInstance(getApplicationContext()).getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                InitiateEntity initiateEntity = new InitiateEntity(sub, result.getData().getRequestId(),
                         UsageType.PASSWORDLESS, AuthenticationType.PATTERN);
                CidaasVerification.getInstance(getApplicationContext()).initiate(initiateEntity, new Result<InitiateResponse>() {
                    @Override
                    public void success(InitiateResponse result) {
                        Toast.makeText(ConfigureActivity.this, "Initiate Success", Toast.LENGTH_SHORT).show();

                        AuthenticateEntity authenticateEntity=new AuthenticateEntity();
                        authenticateEntity.setVerificationType(AuthenticationType.PATTERN);
                        authenticateEntity.setPass_code("RED-1234");
                        authenticateEntity.setExchange_id(result.getData().getExchange_id().getExchange_id());
                        CidaasVerification.getInstance(getApplicationContext()).authenticate(authenticateEntity, new Result<AuthenticateResponse>() {
                            @Override
                            public void success(AuthenticateResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Authenticateion Success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ConfigureActivity.this, "Error Auteh"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Initiate fail" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Fail reqid" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/
        LoginRequest loginRequest=LoginRequest.getPasswordlessPatternLoginRequestEntity("RED-1234",sub,"");

        CidaasVerification.getInstance(getApplicationContext()).loginWithPattern(loginRequest, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                Toast.makeText(ConfigureActivity.this, "Success Authenticated" + result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Fail " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void configureTOTP(View view)
    {
        ConfigurationRequest configurationRequest=new ConfigurationRequest(sub);
        CidaasVerification.getInstance(getApplicationContext()).configureTOTP(configurationRequest, new Result<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success"+result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void displayTotp(View view)
    {
        CidaasVerification.getInstance(getApplicationContext()).listenTOTP(sub);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    public void loginWithTOTP(View view)
    {
        Cidaas.getInstance(getApplicationContext()).getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                LoginRequest loginRequest = LoginRequest.getPasswordlessTOTPRequestEntity(sub,result.getData().getRequestId());
                CidaasVerification.getInstance(getApplicationContext()).loginWithTOTP(loginRequest, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(ConfigureActivity.this, "Success Authenticated" + result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Fail " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Fail " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelTOTP(View view)
    {
        CidaasVerification.getInstance(getApplicationContext()).cancelListenTOTP();
    }

    public void authenticateFinger(View view) {

        Cidaas.getInstance(getApplicationContext()).getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                FingerPrintEntity fingerPrintEntity = new FingerPrintEntity(ConfigureActivity.this, "Authenticate to all", "Description");
                LoginRequest loginRequest=LoginRequest.getPasswordlessFingerprintLoginRequestEntity(sub,result.getData().getRequestId(),fingerPrintEntity);

                CidaasVerification.getInstance(getApplicationContext()).loginWithFingerprint(loginRequest, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(ConfigureActivity.this, "Success Authenticated" + result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Fail " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });


    /* Cidaas.getInstance(getApplicationContext()).getRequestId(new Result<AuthRequestResponseEntity>() {
         @Override
         public void success(AuthRequestResponseEntity result) {
             InitiateEntity initiateEntity=new InitiateEntity(sub,result.getData().getRequestId(),UsageType.PASSWORDLESS,AuthenticationType.FINGERPRINT);
             CidaasVerification.getInstance(getApplicationContext()).initiate(initiateEntity, new Result<InitiateResponse>() {
                 @Override
                 public void success(InitiateResponse result) {
                     Toast.makeText(ConfigureActivity.this, "Success Fing"+result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();
                     final FingerPrintEntity fingerPrintEntity = new FingerPrintEntity(ConfigureActivity.this, "Authenticate to all", "Description");

                     AuthenticateEntity authenticateEntity=new AuthenticateEntity();
                     authenticateEntity.setVerificationType(AuthenticationType.FINGERPRINT);
                     authenticateEntity.setFingerPrintEntity(fingerPrintEntity);
                     authenticateEntity.setExchange_id(result.getData().getExchange_id().getExchange_id());
                     CidaasVerification.getInstance(getApplicationContext()).authenticate(authenticateEntity, new Result<AuthenticateResponse>() {
                         @Override
                         public void success(AuthenticateResponse result) {
                             Toast.makeText(ConfigureActivity.this, "Authenticateion Success", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void failure(WebAuthError error) {
                             Toast.makeText(ConfigureActivity.this, "Error Auteh"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });
                 }

                 @Override
                 public void failure(WebAuthError error) {
                     Toast.makeText(ConfigureActivity.this, "Fail finger auth" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }

         @Override
         public void failure(WebAuthError error) {

         }
     });
*/

    }


    public void setupEmail(View view) {
        CidaasVerification.getInstance(getApplicationContext()).setupEmail(sub, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse result) {
                Toast.makeText(ConfigureActivity.this, "" + result.getData().getStatus_id(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "" + error.getErrorEntity().getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enrollEmail(){

}


    public void callTouchID()
    {
        FingerPrintEntity fingerPrintEntity=new FingerPrintEntity(this);
        fingerPrintEntity.setTitle("This is Authenticateion");
        fingerPrintEntity.setSubtitle("Subtitle");

        AuthenticateEntity authenticateEntity=new AuthenticateEntity();
        authenticateEntity.setVerificationType(pushNotificationEntity.getVerification_type());
        authenticateEntity.setExchange_id(pushNotificationEntity.getExchange_id().getExchange_id());
        authenticateEntity.setFingerPrintEntity(fingerPrintEntity);
        CidaasVerification.getInstance(this).authenticate(authenticateEntity, new Result<AuthenticateResponse>() {
            @Override
            public void success(AuthenticateResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success Authentication"+result.getData().getSub(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
