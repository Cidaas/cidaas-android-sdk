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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.TOTPEntity.TOTPEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.FileOutputStream;

import timber.log.Timber;

public class EnrollPattern extends AppCompatActivity {

    Cidaas cidaas;
    String sub,accessToken,trackId,code;

    public String FCMTOKENFORAUTH="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_pattern);
        cidaas=new Cidaas(this);

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



    public void SetupPattern(View view){
        cidaas.configurePatternRecognition("RED[1,2,3,4]",sub,null,new Result<EnrollPatternMFAResponseEntity>() {
            @Override
            public void success(EnrollPatternMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success Pattern", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Failes Pattern"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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


        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
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
        String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
try {

    File imgae;// = new File("/home/rajanarayanan/Project/cidaas-sdk-android-v2/app/src/main/res/drawable-v24/raja.jpeg", "cidaas.png");
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


    enrollFaceMFARequestEntity.setUserDeviceId(userdeviceID);
    enrollFaceMFARequestEntity.setStatusId("6e32f505-f5d8-4ddb-a2d5-cb239be93eca");
    enrollFaceMFARequestEntity.setImagetoSend(imgae);
    enrollFaceMFARequestEntity.setSub(sub);
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
}
