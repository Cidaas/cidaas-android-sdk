package com.example.cidaasv2.Controller.Repository.DocumentScanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import com.example.cidaasv2.Service.Repository.DocumemtScannerService.DocumentScannerService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class DocumentScannnerController {


    File imageFile= null;
    Context context;


    FileOutputStream out = null;

    static DocumentScannnerController shared;

    public DocumentScannnerController(Context contextFromCidaas) {
        this.context = contextFromCidaas;
    }
    //Create File


    public static DocumentScannnerController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new DocumentScannnerController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    @NonNull
    private File file() throws IOException {
        File sdDir = Environment.getExternalStorageDirectory();
        File pictureFileDir = new File(sdDir, "Cidaas-Faces-docs");
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Toast.makeText(context, "File Storage Permission Denied.Please enable it",
                    Toast.LENGTH_LONG).show();
        }
        imageFile = new File(pictureFileDir, "cidaasDocs.jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        imageFile.createNewFile();
        return imageFile;
    }

    public File convertImageJpeg(Bitmap bitmap) {

        Bitmap picture = bitmap;
        try {
            imageFile = file();

            Timber.d("Error");
            // Toast.makeText(FaceSetupActivity.this, imageFile.toString(), Toast.LENGTH_SHORT).show();
            if (imageFile == null) {
                // Toast.makeText(FaceSetupActivity.this, "NUll Image", Toast.LENGTH_SHORT).show();
                File sdDir = Environment.getExternalStorageDirectory();
                File pictureFileDir = new File(sdDir, "Cidaas-Faces-docs");
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                    Toast.makeText(context, "File Storage Permission Denied.Please enable it",
                            Toast.LENGTH_LONG).show();
                }
                imageFile = new File(pictureFileDir, "cidaasDocs.jpg");
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                imageFile.createNewFile();

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



    public void sendtoServicecall(String baseurl, File photo, String accessToken,Result<DocumentScannerServiceResultEntity> result)
    {
        try {

            if(baseurl!=null && baseurl!="" && accessToken!=null && accessToken!="") {
                DocumentScannerService.getShared(context).sendDocuemntToService(baseurl, photo, accessToken,null, result);
            }
            else {
                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.DOCUMENT_VERIFICATION_FAILURE,"Base URL or AccessToken must not be null",417));
            }
        }
        catch (Exception e)
        {

        }
    }
}
