package de.cidaas.cidaasv2.Helper.Logger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.FileHandler;

import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogFileTest {
    FileHandler logger;
    LogFile shared;
    LogFile logFile;

    Context context;

    @Before
    public void setUp() {
        //context= RuntimeEnvironment.application;
        Context context = mock(Context.class);

        when(context.checkPermission(eq(Manifest.permission.WRITE_EXTERNAL_STORAGE), anyInt(), anyInt())).thenReturn(
                PackageManager.PERMISSION_GRANTED);
    }


    @Test
    public void testGetShared() throws Exception {
        LogFile result = LogFile.getShared(context);
        Assert.assertTrue(result instanceof LogFile);
    }

    @Test
    public void testAddRecordToLog() throws Exception {
        LogFile.getShared(context).addFailureLog("message");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme