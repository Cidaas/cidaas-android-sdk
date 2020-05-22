package com.example.cidaasv2.Helper.Extension;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class WebAuthErrorTest {

    private Context context;
    @Mock
    Object error;

    private WebAuthError shared;

    private WebAuthError webAuthError;

    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        webAuthError=WebAuthError.getShared(context);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        WebAuthError result = WebAuthError.getShared(context);
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testFileNotFoundException() {
        WebAuthError result = webAuthError.fileNotFoundException("testFileNotFoundException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testNoContentInFileException() {
        WebAuthError result = webAuthError.noContentInFileException("testNoContentInFileException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testPropertyMissingException() throws Exception {
        WebAuthError result = webAuthError.propertyMissingException("WebError Testing", "testPropertyMissingException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testServiceFailureException() throws Exception {
        WebAuthError result = webAuthError.serviceCallFailureException(0, "errorMessage", "testServiceFailureException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testLoginURLMissingException() throws Exception {
        WebAuthError result = webAuthError.loginURLMissingException("testLoginURLMissingException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testRedirectURLMissingException() throws Exception {
        WebAuthError result = webAuthError.redirectURLMissingException("testRedirectURLMissingException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testUserCancelledException() throws Exception {
        WebAuthError result = webAuthError.userCancelledException("testUserCancelledException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testCodeNotFoundException() throws Exception {
        WebAuthError result = webAuthError.codeNotFoundException("testCodeNotFoundException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testEmptyCallbackException() throws Exception {
        WebAuthError result = webAuthError.emptyCallbackException("testEmptyCallbackException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testNoUserFoundException() throws Exception {
        WebAuthError result = webAuthError.noUserFoundException("testNoUserFoundException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testDeviceVerificationFailureException() throws Exception {
        WebAuthError result = webAuthError.deviceVerificationFailureException("testDeviceVerificationFailureException");
        Assert.assertTrue( result instanceof WebAuthError);
    }

    @Test
    public void testCustomException() throws Exception {
        WebAuthError result = webAuthError.customException(0, "errorMessage", "testCustomException");
        Assert.assertTrue( result instanceof WebAuthError);
    }


    @Test
    public void testErrorCode() throws Exception {
      //WebAuthError.getShared(context).setConsentUrl("ConsentURL");
        WebAuthError.getShared(context).setError(error);
        WebAuthError.getShared(context).setErrorCode(27);
        WebAuthError.getShared(context).setErrorMessage("Message");
        WebAuthError.getShared(context).setStatusCode(17);


       // Assert.assertEquals("ConsentURL",WebAuthError.getShared(context).getConsentUrl());
        Assert.assertEquals("Message",WebAuthError.getShared(context).getErrorMessage());
        Assert.assertEquals(27,WebAuthError.getShared(context).getErrorCode());
        Assert.assertEquals(17,WebAuthError.getShared(context).getStatusCode());
        Assert.assertEquals(error,WebAuthError.getShared(context).getError());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme