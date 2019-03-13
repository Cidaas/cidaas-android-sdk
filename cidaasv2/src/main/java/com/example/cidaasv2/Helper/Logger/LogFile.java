package com.example.cidaasv2.Helper.Logger;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.example.cidaasv2.Helper.Genral.DBHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.FileHandler;

import timber.log.Timber;

/**
 * Created by widasrnarayanan on 22/1/18.
 */

public class LogFile {

    public static FileHandler logger = null;
    private static String filename = "cidaassdklogfile";

    private final Context mContext;

  //Variables to Check permission
   static boolean isExternalStorageAvailable = false;
    static boolean isExternalStorageWriteable = false;


    static String state = Environment.getExternalStorageState();

    //Shared Instances
    public static LogFile shared;

    public LogFile(Context context) {
     this.mContext=context;
    }


    public static LogFile getShared(Context context)
    {

        if(shared==null)
        {
            shared=new LogFile(context);

        }
        return shared;
    }




    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addRecordToLog(String message) {
        try {

            if(DBHelper.getShared().getEnableLog()) {

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

                if (mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    File sddir = Environment.getExternalStorageDirectory();
                    File logFileDir = new File(sddir, "cidaas/");
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        if (!logFileDir.exists()) {

                            logFileDir.mkdirs();
                        }

                        File logFile = new File(logFileDir, filename + ".txt");

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
            }
            else
            {
                Timber.i("Log is not enabled");
            }

        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());   //todo Handle Exception
        }
    }
}
