/*
package com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import Cidaas;
import PasswordlessEntity;
import Result;
import UsageType;
import WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import EnrollFaceMFAResponseEntity;
import EnrollVoiceMFAResponseEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;

import java.io.File;

public class FaceDetection extends Activity {
    Cidaas de.cidaas;
    String ssub = "825ef0f8-4f2d-46ad-831d-08a30561305d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);

        de.cidaas=new Cidaas(this);
    }

    public void buttonClick(View view)
    {


        File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","de.cidaas.png");

        de.cidaas.configureFaceRecognition(imagefile, ssub,"", 1,new Result<EnrollFaceMFAResponseEntity>() {
            @Override
            public void success(EnrollFaceMFAResponseEntity result) {
                Toast.makeText(FaceDetection.this, "Success Face"+result.getData().getUsageType(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(FaceDetection.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void vverifyFace(View view)
    {

        final File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","de.cidaas.png");

        final PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
        passwordlessEntity.setEmail("raja.narayanan@widas.in");
        passwordlessEntity.setSub(ssub);
        passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

        de.cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                passwordlessEntity.setRequestId(result.getData().getRequestId());
                de.cidaas.loginWithFaceRecognition(imagefile, passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(FaceDetection.this, "Login Successfully"+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(FaceDetection.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });





    }


    public void verifyVoice(View view)
    {
        final File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","voice.wav");

        final PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
        passwordlessEntity.setEmail("raja.narayanan@widas.in");
        passwordlessEntity.setSub(ssub);
        passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

        de.cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                passwordlessEntity.setRequestId(result.getData().getRequestId());
                de.cidaas.loginWithVoiceRecognition(imagefile, passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(FaceDetection.this, "Login Successfully"+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(FaceDetection.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(FaceDetection.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void voiceClick(View view)
    {

        File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","voice.wav");

        de.cidaas.configureVoiceRecognition(imagefile, "",ssub, new Result<EnrollVoiceMFAResponseEntity>() {
            @Override
            public void success(EnrollVoiceMFAResponseEntity result) {
                Toast.makeText(FaceDetection.this, "Success Voice"+result.getData().getUsageType(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(FaceDetection.this, "Failure voice"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
*/
