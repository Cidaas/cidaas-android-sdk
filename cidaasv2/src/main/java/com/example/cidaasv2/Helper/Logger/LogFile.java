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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;

import androidx.core.content.ContextCompat;
import timber.log.Timber;

/**
 * Created by widasrnarayanan on 22/1/18.
 */

public class LogFile {

    public static FileHandler logger = null;
    private static String failure_log_filename = "cidaas_sdk_failure_log";
    private static String success_log_filename = "cidaas_sdk_success_log";
    private static String info_log_filename = "cidaas_sdk_info_log";
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


    public static boolean size(long byteSize) {
        String hrSize = "";
        double kilobyteSize = byteSize / 1024.0;
        double megaByteSize = kilobyteSize / 1024.0;
        boolean moreThanTenMB = false;
        DecimalFormat dec = new DecimalFormat("0.00");

        if (megaByteSize > 10) {
            hrSize = dec.format(megaByteSize).concat(" MB");
            moreThanTenMB = true;
        } else {
            moreThanTenMB = false;
            hrSize = dec.format(kilobyteSize).concat(" KB");
        }
       // Timber.d("Size : " + hrSize);
        return moreThanTenMB;
    }


    public String getTime()
    {
        try
        {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
           // Timber.d(formattedDate+"Date");

            return  formattedDate;
        }
        catch (Exception e)
        {
            return "";
        }
    }



    public void addRecordTolog(String filename,String message)
    {
        try
        {
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

                if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


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
                        else
                        {
                                if (size(logFile.length())) {
                                    Timber.d("File deleted !");
                                    logFile.delete();
                                }
                        }
                        try {
                            //BufferedWriter for performance, true to set append to file flag
                            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));


                            buf.write("Time:-"+getTime()+" "+message + "\r\n");
                            buf.append(message);
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
            Timber.d("Add record to log"+e.getMessage());
        }
    }






    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addFailureLog(String message) {
        try {

             addRecordTolog(failure_log_filename,message);

        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());   //todo Handle Exception
        }
    }




    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addSuccessLog(String methodName,String message) {
        try {

            String loggerMessage = "S:- "+methodName+ "Success Message:- " +message;

           // Timber.i(loggerMessage);
            addRecordTolog(success_log_filename,loggerMessage);

        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());   //todo Handle Exception
        }
    }


    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addInfoLog(String methodName,String message) {
        try {

            String loggerMessage = "S:- "+methodName+ "Info Message:- " +message;

            addRecordTolog(info_log_filename,loggerMessage);

        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());   //todo Handle Exception
        }
    }
}
