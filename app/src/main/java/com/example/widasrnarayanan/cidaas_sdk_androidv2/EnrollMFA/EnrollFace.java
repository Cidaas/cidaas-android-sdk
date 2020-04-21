package com.example.widasrnarayanan.cidaas_sdk_androidv2.EnrollMFA;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import timber.log.Timber;

public class EnrollFace extends AppCompatActivity {

    File imageFile;
    FileOutputStream out;
    String ssub = "825ef0f8-4f2d-46ad-831d-08a30561305d";

//    FaceOverlayView faceOverlayView;
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest captureRequest;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    CameraManager manager;

    private TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_face);

        try {

            if (checkGooglePlayAvailability()) {
                //            requestPermissionThenOpenCamera();

                // mPreview.setOnTouchListener(CameraPreviewTouchListener);
            }


            textureView = (TextureView) findViewById(R.id.texture);
            assert textureView != null;
            textureView.setSurfaceTextureListener(textureListener);

//            faceOverlayView = new FaceOverlayView(getApplicationContext());
        } catch (Exception e) {
            Timber.d(e.getMessage());

        }


    }




    //Check Google play Availability
    public boolean checkGooglePlayAvailability() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, 2404).show();
            }
        }
        return false;
    }



    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            Log.e("Tt", "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };
    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            createCameraPreview();
        }
    };


    CameraCaptureSession.CaptureCallback mcaptureCallBack = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {
/*
                if (textureView.getBitmap() != null) {
                    faceOverlayView.setBitmap(textureView.getBitmap());

            }
            if (faceOverlayView.getBlinkCount() > 0) {
                takeImageJpeg();
                faceOverlayView.resetBlinkCount();


                // hideLoader();
                // closeCamera();
                //Toast.makeText(MainActivity.this, "Photo Saved", Toast.LENGTH_SHORT).show();


            }*/

        }


        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            process(result);

        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

        @Override
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
        }

        @Override
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
            super.onCaptureSequenceAborted(session, sequenceId);
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
        }
    };


    protected void startBackgroundThread() {
        try {
            mBackgroundThread = new HandlerThread("Camera Background");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        } catch (Exception e) {
            Timber.e(e.getMessage());
//            e.printStackTrace();

        }
    }

    protected void stopBackgroundThread() {
        if (mBackgroundHandler != null) {
            mBackgroundThread.quitSafely();
            try {
                mBackgroundThread.join();
                mBackgroundThread = null;
                mBackgroundHandler = null;
            } catch (Exception e) {
                Timber.e(e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public void onClickButton(View view)
    {
        final Cidaas cidaas=Cidaas.getInstance(getApplicationContext());

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);


        cidaas.getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                // passwordlessEntity.setTrackId();
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(ssub);
                passwordlessEntity.setEmail("raja.narayanan@widas.in");



               /* cidaas.loginWithFaceRecognition(convertImageJpeg(bitmap), passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Toast.makeText(EnrollFace.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EnrollFace.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/


               cidaas.configureFaceRecognition(convertImageJpegForFace(bitmap), ssub,"",1, new Result<EnrollFaceMFAResponseEntity>() {
                   @Override
                   public void success(EnrollFaceMFAResponseEntity result) {
                       Toast.makeText(EnrollFace.this, "Face Configured Successfully "+result.getData().getSub(), Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void failure(WebAuthError error) {
                       Toast.makeText(EnrollFace.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });



    }


  /*  @NonNull
    private File file() throws IOException {
        File sdDir = Environment.getExternalStorageDirectory();
        File pictureFileDir = new File(sdDir, "Cidaas-Faces-docs");
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Toast.makeText(getApplicationContext(), "Permission Denied",
                    Toast.LENGTH_LONG).show();
        }
        imageFile = new File(pictureFileDir, "cidaas_photo.jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        imageFile.createNewFile();
        return imageFile;
    }*/

    public File convertImageJpeg(Bitmap bitmap) {

        Bitmap picture = bitmap;
        try {
            //imageFile = file();

            Timber.d("Error");
            // Toast.makeText(FaceSetupActivity.this, imageFile.toString(), Toast.LENGTH_SHORT).show();
            if (imageFile == null) {
                // Toast.makeText(FaceSetupActivity.this, "NUll Image", Toast.LENGTH_SHORT).show();
                File sdDir = Environment.getExternalStorageDirectory();
                File pictureFileDir = new File(sdDir, "Cidaas-Faces-docs");
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                    Toast.makeText(getApplicationContext(), "Permission Denied",
                            Toast.LENGTH_LONG).show();
                }
                imageFile = new File(pictureFileDir, "cidaas_photo.jpg");
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

    //Create File
    @NonNull
    private File file() throws IOException {
        File sdDir = Environment.getExternalStorageDirectory();
        File pictureFileDir = new File(sdDir, "Cidaas-Faces");
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Toast.makeText(getApplicationContext(), "Permission Denied",
                    Toast.LENGTH_LONG).show();
        }
        imageFile = new File(pictureFileDir, "cidaas.png");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        imageFile.createNewFile();
        return imageFile;
    }

    protected void takeImageJpeg() {

        Bitmap picture = textureView.getBitmap();
        try {
            imageFile = file();

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
                imageFile = new File(pictureFileDir, "cidaas.png");
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                imageFile.createNewFile();
                // imageFile=new File(Environment.(), "Cidaas-Faces/cidaas.png");
            }


            out = new FileOutputStream(imageFile);
            picture.compress(Bitmap.CompressFormat.JPEG, 95, out);


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
    }

/*

    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        int sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);

        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;

        // Reverse device orientation for front-facing cameras
        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront) deviceOrientation = -deviceOrientation;

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;

        return jpegOrientation;
    }*/

    public Range<Integer> getRange() {
        CameraCharacteristics chars = null;
        try {
            if (manager == null)
                manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            chars = manager.getCameraCharacteristics(cameraDevice.getId());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Range<Integer>[] ranges = chars.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);

        Range<Integer> result = null;

        for (Range<Integer> range : ranges) {
            int upper = range.getUpper();

            // 10 - min range upper for my needs
            if (upper >= 10) {
                if (result == null || upper < result.getUpper().intValue()) {
                    result = range;
                }
            }
        }

        if (result == null) {
            result = ranges[0];
        }

        return result;
    }


    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);


            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                 //   Toast.makeText(.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
       // Log.e(TAG, "is camera open");
        try {
            cameraId = manager.getCameraIdList()[1];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EnrollFace.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
       // Log.e(TAG, "openCamera X");
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
        //    Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), mcaptureCallBack, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }


}
