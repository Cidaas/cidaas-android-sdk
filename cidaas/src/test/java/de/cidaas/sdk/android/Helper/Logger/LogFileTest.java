package de.cidaas.sdk.android.Helper.Logger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.FileHandler;

import de.cidaas.sdk.android.helper.logger.LogFile;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogFileTest {
    private FileHandler logger;
    private LogFile shared;
    private LogFile logFile;

    private Context context;

    @Before
    public void setUp() {
        context = mock(Context.class);
        when(context.checkPermission(eq(Manifest.permission.WRITE_EXTERNAL_STORAGE), anyInt(), anyInt())).thenReturn(
                PackageManager.PERMISSION_GRANTED);
    }

    @Test
    public void testGetShared() throws Exception {
        LogFile result = LogFile.getInstance(context);
        Assert.assertTrue(result instanceof LogFile);
    }

    @Test
    public void testAddRecordToLog() throws Exception {
        LogFile.getInstance(context).addFailureLog("message");
    }
}