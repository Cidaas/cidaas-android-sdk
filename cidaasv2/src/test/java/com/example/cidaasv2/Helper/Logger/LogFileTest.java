package com.example.cidaasv2.Helper.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.FileHandler;

public class LogFileTest {
    FileHandler logger;
    LogFile shared;
    LogFile logFile;

    @Before
    public void setUp() {

    }

    @Test
    public void testGetShared() throws Exception {
        LogFile result = LogFile.getShared();
        Assert.assertTrue(result instanceof LogFile);
    }

    @Test
    public void testAddRecordToLog() throws Exception {
        LogFile.addRecordToLog("message");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme