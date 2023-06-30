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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.cidaas.sdk.android.helper.general.DBHelper;
import timber.log.Timber;

public class LogFile {
    private static LogFile instance;
    private final Context mContext;

    private static final String FAILURE_LOG_FILENAME = "cidaas_sdk_failure_log";
    private static final String SUCCESS_LOG_FILENAME = "cidaas_sdk_success_log";
    private static final String INFO_LOG_FILENAME = "cidaas_sdk_info_log";
    private static final String API_LOG_FILENAME = "cidaas_sdk_api_log";

    private LogFile(Context context) {
        this.mContext = context;
    }

    public static LogFile getShared(Context context) {
        if (instance == null) {
            instance = new LogFile(context);
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addFailureLog(String message) {
        try {
            addRecordToLog(FAILURE_LOG_FILENAME, message);
        } catch (IOException e) {
            Timber.d(e, "Error during addFailureLog ");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addSuccessLog(String methodName, String message) {
        String loggerMessage = "S:- " + methodName + "Success Message:- " + message;
        try {
            addRecordToLog(SUCCESS_LOG_FILENAME, loggerMessage);
        } catch (IOException e) {
            Timber.d(e, "Error during addSuccessLog ");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addInfoLog(String methodName, String message) {
        String loggerMessage = "I:- " + methodName + "Info Message:- " + message;
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
        } else {
            Timber.i("Log is not enabled or no permissions");
        }
    }

    private boolean checkLogEnabledAndPermission() {
        return DBHelper.getShared().getEnableLog()
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private File getLogFile(String filename, File logFileDir) throws IOException {
        File logFile = new File(logFileDir, filename + ".txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        } else if (logFile.length() > 10000000L) { // emptied when bigger than 10mb
            Timber.d("more than 10mb, file emptied");
            logFile.delete();
            logFile.createNewFile();
        }
        return logFile;
    }

    private File getLogFileDirectory() {
        //  Environment.DIRECTORY_DOCUMENTS is ok for  (https://gitlab.widas.de/cidaas-public-devkits/cidaas-public-devkit-documentation/-/issues/58)
        File storageDirectory = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File logFileDir = new File(storageDirectory, "cidaas/");
        if (!logFileDir.exists()) {
            logFileDir.mkdirs();
        }
        return logFileDir;
    }

    private String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addAPILog(String message) {
        try {
            addRecordToLog(API_LOG_FILENAME, message);
        } catch (IOException e) {
            Timber.d(e, "Error during addFailureLog ");
        }
    }
}
