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

    private String failure_log_filename = "cidaas_sdk_failure_log";
    private String success_log_filename = "cidaas_sdk_success_log";
    private String info_log_filename = "cidaas_sdk_info_log";

    private LogFile(Context context) {
        this.mContext = context;
    }


    public static LogFile getInstance(Context context) {
        if (instance == null) {
            instance = new LogFile(context);
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addFailureLog(String message) {
        try {
            addRecordToLog(failure_log_filename, message);
        } catch (IOException e) {
            Timber.e(e, "Error during addFailureLog ");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addSuccessLog(String methodName, String message) {
        String loggerMessage = "S:- " + methodName + "Success Message:- " + message;
        try {
            addRecordToLog(success_log_filename, loggerMessage);
        } catch (IOException e) {
            Timber.e(e, "Error during addSuccessLog ");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void addInfoLog(String methodName, String message) {
        String loggerMessage = "I:- " + methodName + "Info Message:- " + message;
        try {
            addRecordToLog(info_log_filename, loggerMessage);
        } catch (IOException e) {
            Timber.e(e, "Error during addInfoLog ");
        }
    }

    private String getFormattedDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void addRecordToLog(String filename, String message) throws IOException {
        if (checkLogEnabledAndPermission()) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
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
            }
        } else {
            Timber.i("Log is not enabled or no permissions");
        }
    }

    private boolean checkLogEnabledAndPermission() {
        return DBHelper.getShared().getEnableLog() &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private File getLogFile(String filename, File logFileDir) throws IOException {
        File logFile = new File(logFileDir, filename + ".txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        } else if (logFile.length() > 10000000l) { // emptied when bigger than 10mb
            Timber.d("more than 10mb, file emptied");
            logFile.delete();
            logFile.createNewFile();
        }
        return logFile;
    }

    private File getLogFileDirectory() {
        // TODO Check if Environment.DIRECTORY_DOCUMENTS ok for us (https://gitlab.widas.de/cidaas-public-devkits/cidaas-public-devkit-documentation/-/issues/58)
        File storageDirectory = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File logFileDir = new File(storageDirectory, "cidaas/");
        if (!logFileDir.exists()) {
            logFileDir.mkdirs();
        }
        return logFileDir;
    }
}