package com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;

import java.io.File;

public class FaceDetection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);
    }

    public void buttonClick(View view)
    {
        Cidaas cidaas=new Cidaas(this);
        String ssub = "825ef0f8-4f2d-46ad-831d-08a30561305d";


        File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","cidaas_face_photo.jpg");

        cidaas.configureFaceRecognition(imagefile, ssub, new Result<EnrollFaceMFAResponseEntity>() {
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

    public void voiceClick(View view)
    {
        Cidaas cidaas=new Cidaas(this);
        String ssub = "825ef0f8-4f2d-46ad-831d-08a30561305d";


        File imagefile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cidaas-Faces","voice.wav");

        cidaas.configureVoiceRecognition(imagefile, ssub, new Result<EnrollVoiceMFAResponseEntity>() {
            @Override
            public void success(EnrollVoiceMFAResponseEntity result) {
                Toast.makeText(FaceDetection.this, "Success Voice"+result.getData().getUsageType(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(FaceDetection.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
