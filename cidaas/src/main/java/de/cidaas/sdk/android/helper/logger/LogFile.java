package de.cidaas.sdk.android.helper.logger;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;

import de.cidaas.sdk.android.helper.general.DBHelper;
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

    private static final String FAILURE_LOG_FILENAME = "cidaas_sdk_failure_log";
    private static final String SUCCESS_LOG_FILENAME = "cidaas_sdk_success_log";
    private static final String INFO_LOG_FILENAME = "cidaas_sdk_info_log";

    static String state = Environment.getExternalStorageState();

    //Shared Instances
    public static LogFile shared;

    public LogFile(Context context) {
        this.mContext = context;
    }

    public static LogFile getInstance(Context context) {
        if (instance == null) {
            instance = new LogFile(context);
        }
        return shared;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addFailureLog(String message) {
        try {
            addRecordToLog(FAILURE_LOG_FILENAME, message);
        } catch (IOException e) {
            Timber.d(e, "Error during addFailureLog ");
        }
        // Timber.d("Size : " + hrSize);
        return moreThanTenMB;
    }


    public String getTime() {
        try {
            addRecordToLog(SUCCESS_LOG_FILENAME, loggerMessage);
        } catch (IOException e) {
            Timber.d(e, "Error during addSuccessLog ");
        }
    }


    public void addRecordTolog(String filename, String message) {
        try {
            addRecordToLog(INFO_LOG_FILENAME, loggerMessage);
        } catch (IOException e) {
            Timber.d(e, "Error during addInfoLog ");
        }
    }

    private void addRecordToLog(String filename, String message) throws IOException {
        if (checkLogEnabledAndPermission()) {
            File logFileDir = getLogFileDirectory();
            File logFile = getLogFile(filename, logFileDir);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile, true))) {
                bufferedWriter.write("Time:-" + getFormattedDate() + " " + message + "\r\n");
                bufferedWriter.append(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (Exception e) {
                Timber.d(e, "Error during buffer writing");
            }
        } catch (Exception e) {
            Timber.d("Add record to log" + e.getMessage());
        }
    }

    private boolean checkLogEnabledAndPermission() {
        return DBHelper.getShared().getEnableLog()
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addSuccessLog(String methodName, String message) {
        try {

            String loggerMessage = "S:- " + methodName + "Success Message:- " + message;

            // Timber.i(loggerMessage);
            addRecordTolog(success_log_filename, loggerMessage);

        } catch (Exception e) {

            Timber.d(e.getMessage());   // Handle Exception
        }
    }


    //Add records to a log file
    @TargetApi(Build.VERSION_CODES.M)
    public void addInfoLog(String methodName, String message) {
        try {

            String loggerMessage = "S:- " + methodName + "Info Message:- " + message;

            addRecordTolog(info_log_filename, loggerMessage);

        } catch (Exception e) {
            Timber.d(e.getMessage());
        }
    }

    private String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }
}
