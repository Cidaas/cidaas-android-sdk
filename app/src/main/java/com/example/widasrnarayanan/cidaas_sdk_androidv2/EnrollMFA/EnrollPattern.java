package com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.TOTPEntity.TOTPEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import timber.log.Timber;

public class EnrollPattern extends AppCompatActivity {

    Cidaas cidaas;
    String sub,accessToken,trackId,code;

    public String FCMTOKENFORAUTH="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_pattern);
        cidaas=Cidaas.getInstance(this);

        Intent intent=getIntent();
        sub=intent.getStringExtra("sub");
        accessToken=intent.getStringExtra("accessToken");

        if(sub=="" || sub==null)
        {
            sub = "825ef0f8-4f2d-46ad-831d-08a30561305d";
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TOTPListener"));


    }

    public void verifyPattern(View view){
        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {


                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setEmail("");
                passwordlessEntity.setMobile("");
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setTrackId(trackId);
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

                cidaas.loginWithPatternRecognition("RED[1,2,3,4]", passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                            @Override
                            public void success(LoginCredentialsResponseEntity result) {
                                Toast.makeText(EnrollPattern.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(EnrollPattern.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });


}


public void enrollPattern(View view)
{


    try{

       cidaas.configurePatternRecognition("RED[1,2,3,4]", sub, "", new Result<EnrollPatternMFAResponseEntity>() {
           @Override
           public void success(EnrollPatternMFAResponseEntity result) {
               Toast.makeText(EnrollPattern.this, "Sucess", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void failure(WebAuthError error) {
               Toast.makeText(EnrollPattern.this, "Failure"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }
    catch (Exception e)
    {

    }


}


/*public void FIDO(View view)
{
    cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
        @Override
        public void success(AuthRequestResponseEntity result) {


            PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
            passwordlessEntity.setEmail("");
            passwordlessEntity.setMobile("");
            passwordlessEntity.setSub(sub);
            passwordlessEntity.setRequestId(result.getData().getRequestId());
            passwordlessEntity.setTrackId(trackId);
            passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

            FidoSignTouchResponse fidoTouchResponse=new FidoSignTouchResponse();
    fidoTouchResponse.setFidoRequestId("reqid");
    fidoTouchResponse.setClientData("challenge");

    cidaas.loginWithFIDO(fidoTouchResponse, passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
        @Override
        public void success(LoginCredentialsResponseEntity result) {

        }

        @Override
        public void failure(WebAuthError error) {

        }
    });
        }

        @Override
        public void failure(WebAuthError error) {

        }
    });
}*/



    public void enrollPatternOnly(View view){

        String statusId="7f359ef4-fae4-4b99-9b34-cc1eac7fee14";
        cidaas.enrollPattern("RED[1,2,3,4]",sub,statusId,new Result<EnrollPatternMFAResponseEntity>() {
            @Override
            public void success(EnrollPatternMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success Pattern", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Fails Pattern"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void SetupSmartPush(View view)
    {
     /*  cidaas.setupSmartPushMFA(sub, new Result<SetupSmartPushMFAResponseEntity>() {
            @Override
            public void success(SetupSmartPushMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success push", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Fails push "+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
*/

     cidaas.configureSmartPush(sub,"", new Result<EnrollSmartPushMFAResponseEntity>() {
         @Override
         public void success(EnrollSmartPushMFAResponseEntity result) {
             Toast.makeText(EnrollPattern.this, "Success push", Toast.LENGTH_SHORT).show();
         }

         @Override
         public void failure(WebAuthError error) {
             Toast.makeText(EnrollPattern.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
         }
     });

    }

    public void EnrollSmartPush(View view){

/*
        cidaas.enrollSmartPushMFA(enrollSmartPushMFARequestEntity, new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Enroll Sucessfully"+result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Enroll failure", Toast.LENGTH_SHORT).show();
            }
        });
   */ }
    public void AuthenticateSmartPush(View view){


        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setTrackId(trackId);
                passwordlessEntity.setMobile("+919787113989");

                cidaas.loginWithSmartPush(passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(EnrollPattern.this, "Success Push"+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EnrollPattern.this, "Error push "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });



       }


    public void SetupFace(View view)
    {
       Intent intent=new Intent(getApplicationContext(),FaceDetection.class);
       startActivity(intent);


    }

    public void EnrollFace(View view){
       // String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
try {

    File imgae;//= new File(R.drawable "cidaas.png");
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
  FileOutputStream out;

    Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.sample);
// convert drawable to bitmap

    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

    File sdDir = Environment.getExternalStorageDirectory();
    File pictureFileDir = new File(sdDir, "Cidaas-sdk-Faces");

    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
        Toast.makeText(getApplicationContext(), "Permission Denied",
                Toast.LENGTH_LONG).show();
    }
    imgae = new File(pictureFileDir, "cidaas.png");
    if (imgae.exists()) {
        imgae.delete();
    }

    imgae.createNewFile();


    out = new FileOutputStream(imgae);
    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);


    //enrollFaceMFARequestEntity.setUserDeviceId(userdeviceID);
    enrollFaceMFARequestEntity.setStatusId("6e32f505-f5d8-4ddb-a2d5-cb239be93eca");
    //enrollFaceMFARequestEntity.setImagetoSend(imgae);

    cidaas.enrollFace(imgae, sub, "6e32f505-f5d8-4ddb-a2d5-cb239be93eca", 1, new Result<EnrollFaceMFAResponseEntity>() {
        @Override
        public void success(EnrollFaceMFAResponseEntity result) {
            Toast.makeText(EnrollPattern.this, "Success ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(WebAuthError error) {
            Toast.makeText(EnrollPattern.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    });

}
catch (Exception ec){
    Toast.makeText(this, "Test"+ec, Toast.LENGTH_SHORT).show();
}
   /* cidaas.enrollFaceMFA(enrollFaceMFARequestEntity, new Result<EnrollFaceMFAResponseEntity>() {
        @Override
        public void success(EnrollFaceMFAResponseEntity result) {
            Toast.makeText(EnrollPattern.this, "Enroll Sucessfully" + result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(WebAuthError error) {
            Toast.makeText(EnrollPattern.this, "Enroll failure", Toast.LENGTH_SHORT).show();
        }
    });
*/
    }
    public void AuthenticateFace(View view){
        final String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();
        physicalVerificationEntity.setSub(sub);
        physicalVerificationEntity.setUserDeviceId(userdeviceID);

        physicalVerificationEntity.setUsageType("MULTIFACTOR_AUTHENTICATION");
}


public void EnrollTOTP(View view){
 try{
     cidaas.configureTOTP(sub,"", new Result<EnrollTOTPMFAResponseEntity>() {
         @Override
         public void success(EnrollTOTPMFAResponseEntity result) {
             Toast.makeText(EnrollPattern.this, "Result"+result.getData().getSub(), Toast.LENGTH_SHORT).show();
         }

         @Override
         public void failure(WebAuthError error) {
             Toast.makeText(EnrollPattern.this, "Error:"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
         }
     });
 }

 catch (Exception e)
 {

 }
}


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            TOTPEntity totpEntity = (TOTPEntity) intent.getSerializableExtra("TOTP");
            Toast.makeText(context, "Message:"+totpEntity.getTotp_string()+"Timer:"+totpEntity.getTimer_count(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    public void listenTOTP(View view)
    {
     try
     {
        cidaas.listenTOTP(sub);
     }
     catch (Exception e){

     }
    }



    public void cancelTOTP(View view){
        try
        {
            cidaas.cancelListenTOTP();
        }
        catch (Exception e){

        }
    }

public void LoginTOTP(View view){
try{


    PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
    passwordlessEntity.setUsageType(UsageType.MFA);

   // passwordlessEntity.setRequestId(result.getData().getRequestId());
    passwordlessEntity.setSub(sub);
    passwordlessEntity.setMobile("");
    passwordlessEntity.setEmail("");

    cidaas.loginWithTOTP(passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
        @Override
        public void success(LoginCredentialsResponseEntity result) {

        }

        @Override
        public void failure(WebAuthError error) {

        }
    });
}
catch (Exception e)
{
    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
}
}


    private String getFCMToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Timber.i("FCM TOKEN" + token);

        return token;
        // save device info


    }



    public void enrollFinger(View view)
    {


        cidaas.configureFingerprint(EnrollPattern.this,sub, "", null,new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Enroll SuccssFull", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Enroll Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void usageFinger(View view)
    {
        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setTrackId(trackId);
                passwordlessEntity.setMobile("+919787113989");

                FingerPrintEntity fingerPrintEntity=new FingerPrintEntity();
                fingerPrintEntity.setTitle("Hi Please AUTHENTICATE");
                fingerPrintEntity.setSubtitle("A");

                cidaas.loginWithFingerprint(EnrollPattern.this,passwordlessEntity, fingerPrintEntity,new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(EnrollPattern.this, "Success Finger"+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EnrollPattern.this, "Error Finger "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Error Finger "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
