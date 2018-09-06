/*
package com.example.cidaasv2.Service.Repository.Verification.Face;

import android.content.Context;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import okhttp3.RequestBody;

public class FaceVerificationServiceTest {
    Context context;
    FaceVerificationService faceVerificationService;

    @Before
    public void setUp() {
      faceVerificationService=new FaceVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        FaceVerificationService result = FaceVerificationService.getShared(null);
        Assert.assertEquals(new FaceVerificationService(null), result);
    }

    @Test
    public void testSetupFaceMFA() throws Exception {

        faceVerificationService.setupFaceMFA("baseurl", "accessToken", "codeChallenge", new SetupFaceMFARequestEntity(), null);
    }

    @Test
    public void testScannedFace() throws Exception {

        faceVerificationService.scannedFace("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testStringtoRequestBody() throws Exception {
        RequestBody result = faceVerificationService.StringtoRequestBody("value");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testEnrollFace() throws Exception {

        faceVerificationService.enrollFace("baseurl", "accessToken", new EnrollFaceMFARequestEntity(), null);
    }

    @Test
    public void testInitiateFace() throws Exception {

        faceVerificationService.initiateFace("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticateFace() throws Exception {

        faceVerificationService.authenticateFace("baseurl", new AuthenticateFaceRequestEntity(), null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme*/
