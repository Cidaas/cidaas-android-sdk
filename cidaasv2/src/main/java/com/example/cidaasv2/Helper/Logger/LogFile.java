package com.example.cidaasv2.Helper.Logger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.cidaasv2.Helper.Genral.FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;

import timber.log.Timber;

/**
 * Created by widasrnarayanan on 22/1/18.
 */

public class LogFile {

    public static FileHandler logger = null;
    private static String filename = "cidaassdklogfile";

  //Variables to Check permission
   static boolean isExternalStorageAvailable = false;
    static boolean isExternalStorageWriteable = false;


    static String state = Environment.getExternalStorageState();

    //Shared Instances
    public static LogFile shared;


    public static LogFile getShared()
    {
        if(shared==null)
        {
            shared=new LogFile();
        }
        return shared;
    }




    //Add records to a log file
    public static void addRecordToLog(String message) {
        try {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                isExternalStorageAvailable = isExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                isExternalStorageAvailable = true;
                isExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states, but all we need
                //  to know is we can neither read nor write
                isExternalStorageAvailable = isExternalStorageWriteable = false;
            }



             /*   try {
                    File sdDir = Environment.getExternalStorageDirectory();
                    File pictureFileDir = new File(sdDir, "Cidaas-Voices");
                    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    }
                    File audioFile = new File(pictureFileDir, "voice.wav");
                    if (audioFile.exists()) {
                        audioFile.delete();
                    }
                    audioFile.createNewFile();

                }
                catch (Exception e)
                {

                }
*/

            File sddir = Environment.getExternalStorageDirectory();
            File logFileDir=new File(sddir,"cidaas/");
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (!logFileDir.exists()) {

                    logFileDir.mkdirs();
                }

                File logFile = new File(logFileDir,  filename + ".txt");

                if (!logFile.exists()) {
                    try {
                        // Log.d("File created ", "File created ");
                        logFile.createNewFile();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Timber.d(e.getMessage());
                        e.printStackTrace();
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));


                    buf.write(message + "\r\n");
                    //buf.append(message);
                    buf.newLine();
                    buf.flush();
                    buf.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Timber.d(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());   //todo Handle Exception
        }
    }
}
