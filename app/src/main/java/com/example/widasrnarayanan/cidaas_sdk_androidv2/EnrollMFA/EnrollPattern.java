package com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
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


    }

    public void verifyPattern(View view){
        String userdeviceID="8d0937c3-b2a8-4651-9bdc-cfce8a5eba14";
        PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();
        physicalVerificationEntity.setSub(sub);
        physicalVerificationEntity.setUserDeviceId(userdeviceID);

        physicalVerificationEntity.setUsageType("MULTIFACTOR_AUTHENTICATION");

        physicalVerificationEntity.setPhysicalVerificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
      /*  cidaas.initiatePatternMFA(physicalVerificationEntity, new Result<InitiatePatternMFAResponseEntity>() {
            @Override
            public void success(InitiatePatternMFAResponseEntity result) {

                String verificationcode="RED[1,2,3,4]";
               cidaas.authenticatePatternMFA(result.getData().getStatusId(), verificationcode, new Result<AuthenticatePatternResponseEntity>() {
                   @Override
                   public void success(AuthenticatePatternResponseEntity result) {
                       Toast.makeText(EnrollPattern.this, "Successfully Authenticated"+result.getData().getTrackingCode(), Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void failure(WebAuthError error) {
                       Toast.makeText(EnrollPattern.this, "Failed Authenitcated", Toast.LENGTH_SHORT).show();
                   }
               });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Failed initiate"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
   */ }


    public void EnrollPattern(View view){

        String userdeviceID="8d0937c3-b2a8-4651-9bdc-cfce8a5eba14";
        EnrollPatternMFARequestEntity enrollPatternMFARequestEntity=new EnrollPatternMFARequestEntity();
        enrollPatternMFARequestEntity.setUserDeviceId(userdeviceID);
        enrollPatternMFARequestEntity.setStatusId("2f7dfee0-b521-4e9c-ab01-bedb00ad4f3c");
        enrollPatternMFARequestEntity.setVerifierPassword("RED[1,2,3,4]");
        enrollPatternMFARequestEntity.setSub(sub);



        cidaas.initiateAccountVerificationByEmail("", "", new Result<RegisterUserAccountInitiateResponseEntity>() {
            @Override
            public void success(RegisterUserAccountInitiateResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });



        cidaas.configureEmail(sub, new Result<SetupEmailMFAResponseEntity>() {
            @Override
            public void success(SetupEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });

        cidaas.enrollEmail(code, new Result<EnrollEmailMFAResponseEntity>() {
            @Override
            public void success(EnrollEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });



         cidaas.loginWithTOTP("yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result<LoginCredentialsResponseEntity>() {
             @Override
             public void success(LoginCredentialsResponseEntity result) {

             }

             @Override
             public void failure(WebAuthError error) {

             }
         });


      cidaas.configureTOTP("Your Sub", new Result<EnrollTOTPMFAResponseEntity>() {
    @Override
    public void success(EnrollTOTPMFAResponseEntity result) {

    }

    @Override
    public void failure(WebAuthError error) {

    }
});



   /*     cidaas.enrollPatternMFA(enrollPatternMFARequestEntity, new Result<EnrollPatternMFAResponseEntity>() {
            @Override
            public void success(EnrollPatternMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Enroll Sucessfully"+result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Enroll Failed"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
   */ }


    public void SetupPattern(View view){
   /*     cidaas.setupPatternMFA(sub,new Result<SetupPatternMFAResponseEntity>() {
            @Override
            public void success(SetupPatternMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success Pattern", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Failes Pattern"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
   */ }

    public void SetupSmartPush(View view)
    {
       /*cidaas.setupSmartPushMFA(sub, new Result<SetupSmartPushMFAResponseEntity>() {
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
    }

    public void EnrollSmartPush(View view){
        String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity=new EnrollSmartPushMFARequestEntity();
        enrollSmartPushMFARequestEntity.setUserDeviceId(userdeviceID);
        enrollSmartPushMFARequestEntity.setStatusId("6e32f505-f5d8-4ddb-a2d5-cb239be93eca");
        enrollSmartPushMFARequestEntity.setVerifierPassword("72");
        enrollSmartPushMFARequestEntity.setSub(sub);
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
        String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();
        physicalVerificationEntity.setSub(sub);
        physicalVerificationEntity.setUserDeviceId(userdeviceID);

        physicalVerificationEntity.setUsageType("MULTIFACTOR_AUTHENTICATION");

        physicalVerificationEntity.setPhysicalVerificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");


    /*    cidaas.initiateSmartPushMFA(physicalVerificationEntity, new Result<InitiateSmartPushMFAResponseEntity>() {
            @Override
            public void success(InitiateSmartPushMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success "+result.getData().getRandomNumber(), Toast.LENGTH_SHORT).show();

                cidaas.authenticateSmartPushMFA( result.getData().getStatusId(), result.getData().getRandomNumber(), new Result<AuthenticateSmartPushResponseEntity>() {
                    @Override
                    public void success(AuthenticateSmartPushResponseEntity result) {
                        Toast.makeText(EnrollPattern.this, "Sucesss Push", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EnrollPattern.this, "Failed Push", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Error:", Toast.LENGTH_SHORT).show();
            }
        });
   */ }


    public void SetupFace(View view)
    {
  /*      cidaas.setupFaceMFA(sub, new Result<SetupFaceMFAResponseEntity>() {
            @Override
            public void success(SetupFaceMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success push", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Fails push "+error.ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    public void EnrollFace(View view){
        String userdeviceID="7557f73c-8ea8-4cee-979f-4609878894aa";
        EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
try {

    File imgae;// = new File("/home/rajanarayanan/Project/cidaas-sdk-android-v2/app/src/main/res/drawable-v24/raja.jpeg", "cidaas.png");
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
  FileOutputStream out;

    Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.raja);
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

        physicalVerificationEntity.setPhysicalVerificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");

/*
        cidaas.initiateFaceMFA(physicalVerificationEntity, new Result<InitiateFaceMFAResponseEntity>() {
            @Override
            public void success(InitiateFaceMFAResponseEntity result) {
                Toast.makeText(EnrollPattern.this, "Success "+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();

                File imgae=new File("/home/rajanarayanan/Project/cidaas-sdk-android-v2/app/src/main/res/drawable-v24/raja.jpeg");

                cidaas.authenticateFaceMFA( result.getData().getStatusId(),imgae, userdeviceID,new Result<AuthenticateFaceResponseEntity>() {
                    @Override
                    public void success(AuthenticateFaceResponseEntity result) {
                        Toast.makeText(EnrollPattern.this, "Sucesss Push", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EnrollPattern.this, "Failed Push", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(EnrollPattern.this, "Error:", Toast.LENGTH_SHORT).show();
            }
        });
    */}

    private String getFCMToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Timber.i("FCM TOKEN" + token);

        return token;
        // save device info


    }
}
