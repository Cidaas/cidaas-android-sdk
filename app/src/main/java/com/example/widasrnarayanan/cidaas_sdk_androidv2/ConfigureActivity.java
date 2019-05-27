package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.PushNotificationEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PushEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.presentation.View.CidaasVerification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

public class ConfigureActivity extends AppCompatActivity {

    PushEntity pushNotificationEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        if ((getIntent() != null) && (getIntent().getSerializableExtra("NotificationData") != null)) {
            pushNotificationEntity = (PushEntity) getIntent().getSerializableExtra("NotificationData");
        }


        if(pushNotificationEntity!=null) {
            if (pushNotificationEntity.getVerification_type().equals(AuthenticationType.TOUCHID)) {

            }
        }
    }

    String sub="825ef0f8-4f2d-46ad-831d-08a30561305d";
    File imageFile;FileOutputStream out;

    public void enrollPattern(View view)
    {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();


        SetupEntity setupEntity=new SetupEntity(sub, AuthenticationType.PATTERN);

        CidaasVerification.getInstance(this).setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResultresult) {


                ScannedEntity scannedEntity=new ScannedEntity(sub,setupResultresult.getData().getExchange_id().getExchange_id(),AuthenticationType.PATTERN);

                CidaasVerification.getInstance(getApplicationContext()).scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResponseresult) {
                        EnrollEntity enrollEntity=new EnrollEntity(scannedResponseresult.getData().getExchange_id().getExchange_id(),
                                "RED-1234",AuthenticationType.PATTERN);

                        CidaasVerification.getInstance(getApplicationContext()).enroll(enrollEntity, new Result<EnrollResponse>() {
                            @Override
                            public void success(EnrollResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Success Pattern", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ConfigureActivity.this, "Error enroll"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Error scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void enrollFinger(View view)
    {


        SetupEntity setupEntity=new SetupEntity(sub, AuthenticationType.TOUCHID);

        CidaasVerification.getInstance(this).setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResultresult) {

                final String clientId=setupResultresult.getData().getAuthenticator_client_id();
                ScannedEntity scannedEntity=new ScannedEntity(sub,setupResultresult.getData().getExchange_id().getExchange_id(),AuthenticationType.TOUCHID);


                final FingerPrintEntity fingerPrintEntity=new FingerPrintEntity(ConfigureActivity.this,"Authenticate to all","dkjvhbjhbvdjkhbdv");

                CidaasVerification.getInstance(getApplicationContext()).scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResponseresult) {
                        EnrollEntity enrollEntity=new EnrollEntity(scannedResponseresult.getData().getExchange_id().getExchange_id(),
                                AuthenticationType.TOUCHID,fingerPrintEntity);

                        CidaasVerification.getInstance(getApplicationContext()).enroll(enrollEntity, new Result<EnrollResponse>() {
                            @Override
                            public void success(EnrollResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Success TOUCHID", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ConfigureActivity.this, "Error enroll"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Error scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deletePattern(View view)
    {
        DeleteEntity deleteEntity=new DeleteEntity(sub,AuthenticationType.PATTERN);
        CidaasVerification.getInstance(this).delete(deleteEntity, new Result<DeleteResponse>() {
            @Override
            public void success(DeleteResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success Response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deletePush(View view)
    {
        DeleteEntity deleteEntity=new DeleteEntity(sub,AuthenticationType.PUSH);
        CidaasVerification.getInstance(this).delete(deleteEntity, new Result<DeleteResponse>() {
            @Override
            public void success(DeleteResponse result) {
                Toast.makeText(ConfigureActivity.this, "Success Response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void enrollPush(View view)
    {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();


        SetupEntity setupEntity=new SetupEntity(sub, AuthenticationType.PUSH);

        CidaasVerification.getInstance(this).setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(final SetupResponse setupResultresult) {

                final String clientId=setupResultresult.getData().getAuthenticator_client_id();
                ScannedEntity scannedEntity=new ScannedEntity(sub,setupResultresult.getData().getExchange_id().getExchange_id(),AuthenticationType.PUSH);

                CidaasVerification.getInstance(getApplicationContext()).scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResponseresult) {
                        EnrollEntity enrollEntity=new EnrollEntity(scannedResponseresult.getData().getExchange_id().getExchange_id(),
                                setupResultresult.getData().getPush_selected_number(),AuthenticationType.PUSH);

                        CidaasVerification.getInstance(getApplicationContext()).enroll(enrollEntity, new Result<EnrollResponse>() {
                            @Override
                            public void success(EnrollResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Success Push", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ConfigureActivity.this, "Error enroll"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Error scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enrollFace(View view)
    {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();


        SetupEntity setupEntity=new SetupEntity(sub, AuthenticationType.FACE);

        CidaasVerification.getInstance(this).setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResultresult) {

                final String clientId=setupResultresult.getData().getAuthenticator_client_id();
                ScannedEntity scannedEntity=new ScannedEntity(sub,setupResultresult.getData().getExchange_id().getExchange_id(),AuthenticationType.FACE);

                CidaasVerification.getInstance(getApplicationContext()).scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResponseresult) {

                        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);



                        EnrollEntity enrollEntity=new EnrollEntity(scannedResponseresult.getData().getExchange_id().getExchange_id(),
                                AuthenticationType.FACE,convertImageJpegForFace(bitmap),0);

                        // enrollEntity.setFileToSend(convertImageJpegForFace(bitmap));

                        CidaasVerification.getInstance(getApplicationContext()).enroll(enrollEntity, new Result<EnrollResponse>() {
                            @Override
                            public void success(EnrollResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Success Face", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                error.getErrorEntity().getCode();
                                Toast.makeText(ConfigureActivity.this, "Error enroll"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Error scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    public void enrollVoice(View view)
    {

        //CidaasVerification.getInstance(getApplicationContext()).
        // EnrollEntity enrollEntity=new EnrollEntity("","",);
        // CidaasVerification.getInstance(this).enroll();


        SetupEntity setupEntity=new SetupEntity(sub, AuthenticationType.VOICE);

        CidaasVerification.getInstance(this).setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResultresult) {

                final String clientId=setupResultresult.getData().getAuthenticator_client_id();
                ScannedEntity scannedEntity=new ScannedEntity(sub,setupResultresult.getData().getExchange_id().getExchange_id(),AuthenticationType.PATTERN);

                CidaasVerification.getInstance(getApplicationContext()).scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResponseresult) {
                        EnrollEntity enrollEntity=new EnrollEntity(scannedResponseresult.getData().getExchange_id().getExchange_id(),
                                "RED-1234",AuthenticationType.VOICE);

                        CidaasVerification.getInstance(getApplicationContext()).enroll(enrollEntity, new Result<EnrollResponse>() {
                            @Override
                            public void success(EnrollResponse result) {
                                Toast.makeText(ConfigureActivity.this, "Success Voice", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ConfigureActivity.this, "Error enroll"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Error scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Error setup"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void authenticatePattern(View view)
    {
        Cidaas.getInstance(getApplicationContext()).getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                InitiateEntity initiateEntity=new InitiateEntity(sub,result.getData().getRequestId(),
                        "c3d6a9bb-8be7-48a8-a8e5-f4f650ec8a92", UsageType.PASSWORDLESS,AuthenticationType.PATTERN);
                CidaasVerification.getInstance(getApplicationContext()).initiate(initiateEntity, new Result<InitiateResponse>() {
                    @Override
                    public void success(InitiateResponse result) {
                        Toast.makeText(ConfigureActivity.this, "Initiate Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ConfigureActivity.this, "Initiate fail"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ConfigureActivity.this, "Fail reqid"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void authenticateFinger(View view)
    {
        callTouchID();

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
