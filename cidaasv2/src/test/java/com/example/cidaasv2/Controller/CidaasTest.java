package com.example.cidaasv2.Controller;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CidaasTest {

    Context context;

    Cidaas cidaas;

    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        cidaas=new Cidaas(context);



    }

    @Test
    public void testGetInstance() throws Exception {

       Cidaas result = Cidaas.getInstance(context);

        Assert.assertTrue(result instanceof Cidaas );

    }

    @Test
    public void testGetTenantInfo() throws Exception {


        cidaas=Cidaas.getInstance(context);

        cidaas.getTenantInfo(new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                 Assert.assertEquals("Tenant Name",result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });



        // when(cidaas.checkSavedProperties(result)).thenReturn("samp");

        //spy(testCheckSavedProperties());

    }

    @Test
    public void testGetClientInfo() throws Exception {
     cidaas.getClientInfo("RequestId", new Result<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }



  /*  @Test
    public void testCheckSavedProperties() throws Exception {
        final FileHelper fileHelper=Mockito.mock(FileHelper.class);

        AssetManager assetManager=Mockito.mock(AssetManager.class);
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(context.getAssets()).thenReturn(assetManager);


      //  when(FileHelper.getShared(context)).thenReturn(fileHelper);

        cidaas.checkSavedProperties(null);
    }*/

    /*@Test
    public void testIsENABLE_PKCE() throws Exception {
        boolean result = cidaas.isENABLE_PKCE();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetENABLE_PKCE() throws Exception {
        cidaas.setENABLE_PKCE(true);
    }

    @Test
    public void testSetFCMToken() throws Exception {
        cidaas.setFCMToken("FCMToken");
    }

    @Test
    public void testSetremoteMessage() throws Exception {
        Cidaas.setremoteMessage(new HashMap<String, String>() {{
            put("String", "String");
        }});
    }

    @Test
    public void testGetRequestId() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getRequestId("DomainUrl", "ClientId", "RedirectURL", "ClientSecret", null);
    }

    @Test
    public void testGetRequestId2() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getRequestId(null);
    }




    @Test
    public void testLoginWithCredentials() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithCredentials("requestId", null, null);
    }

    @Test
    public void testGetConsentDetails() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.getConsentDetails("consentName", null);
    }

    @Test
    public void testLoginAfterConsent() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginAfterConsent(new ConsentEntity(), null);
    }

    @Test
    public void testGetMFAList() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.getMFAList("sub", null);
    }

    @Test
    public void testConfigureEmail() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.configureEmail("sub", null);
    }

    @Test
    public void testEnrollEmail() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.enrollEmail("code", "statusId", null);
    }

    @Test
    public void testLoginWithEmail() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithEmail(new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyEmail() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifyEmail("code", "statusId", null);
    }

    @Test
    public void testConfigureSMS() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureSMS("sub", null);
    }

    @Test
    public void testEnrollSMS() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.enrollSMS("code", "statusId", null);
    }

    @Test
    public void testLoginWithSMS() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithSMS(new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifySMS() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.verifySMS("code", "statusId", null);
    }

    @Test
    public void testConfigureIVR() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureIVR("sub", null);
    }

    @Test
    public void testEnrollIVR() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.enrollIVR("code", "statusId", null);
    }

    @Test
    public void testLoginWithIVR() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithIVR(new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyIVR() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.verifyIVR("code", "statusId", null);
    }

    @Test
    public void testConfigureBackupcode() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureBackupcode("sub", null);
    }

    @Test
    public void testLoginWithBackupcode() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithBackupcode("code", new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyBackupcode() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.verifyBackupcode("code", "statusId", null);
    }

    @Test
    public void testConfigurePatternRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.configurePatternRecognition("pattern", "sub", null);
    }

    @Test
    public void testLoginWithPatternRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithPatternRecognition("pattern", new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyPattern() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifyPattern("patternString", "statusId", null);
    }

    @Test
    public void testConfigureFaceRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureFaceRecognition(new File(getClass().getResource("/com/example/cidaasv2/Controller/PleaseReplaceMeWithTestFile.txt").getFile()), "sub", null);
    }

    @Test
    public void testLoginWithFaceRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithFaceRecognition(new File(getClass().getResource("/com/example/cidaasv2/Controller/PleaseReplaceMeWithTestFile.txt").getFile()), new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyFace() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.verifyFace("statusId", null);
    }

    @Test
    public void testConfigureFingerprint() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.configureFingerprint("sub", null);
    }

    @Test
    public void testLoginWithFingerprint() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithFingerprint(new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyFingerprint() throws Exception {ul
ut
nR
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifyFingerprint("statusId", null);
    }

    @Test
    public void testConfigureSmartPush() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.configureSmartPush("sub", null);
    }

    @Test
    public void testLoginWithSmartPush() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithSmartPush(new PasswordlessEntity(), null);
    }
ul
ut
nR
    @Test
    public void testVerifySmartPush() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifySmartPush("randomNumber", "statusId", null);
    }

    @Test
    public void testConfigureTOTP() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.configureTOTP("sub", null);
    }

    @Test
    public void testLoginWithTOTP() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithTOTP(new PasswordlessEntity(), null);
    }

    @Test
    public void testListenTOTP() throws Exception {
        cidaas.listenTOTP("sub");
    }

    @Test
    public void testCancelListenTOTP() throws Exception {
        cidaas.cancelListenTOTP();
    }

    @Test
    public void testConfigureVoiceRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureVoiceRecognition(new File(getClass().getResource("/com/example/cidaasv2/Controller/PleaseReplaceMeWithTestFile.txt").getFile()), "sub", null);
    }

    @Test
    public void testLoginWithVoiceRecognition() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithVoiceRecognition(new File(getClass().getResource("/com/example/cidaasv2/Controller/PleaseReplaceMeWithTestFile.txt").getFile()), new PasswordlessEntity(), null);
    }

    @Test
    public void testVerifyVoice() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifyVoice(new File(getClass().getResource("/com/example/cidaasv2/Controller/PleaseReplaceMeWithTestFile.txt").getFile()), "statusId", null);
    }

    @Test
    public void testGetRegistrationFields() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.getRegistrationFields("requestId", "locale", null);
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.registerUser("requestId", null, null);
    }

    @Test
    public void testInitiateEmailVerification() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.initiateEmailVerification("sub", "requestId", null);
    }

    @Test
    public void testInitiateSMSVerification() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.initiateSMSVerification("sub", "requestId", null);
    }

    @Test
    public void testInitiateIVRVerification() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.initiateIVRVerification("sub", "requestId", null);
    }

    @Test
    public void testVerifyAccount() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.verifyAccount("code", "accvid", null);
    }

    @Test
    public void testGetDeduplicationDetails() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getDeduplicationDetails("trackId", null);
    }

    @Test
    public void testRegisterUser2() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.registerUser("trackId", null);
    }

    @Test
    public void testLoginWithDeduplication() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.loginWithDeduplication("requestId", "sub", "password", null);
    }

    @Test
    public void testInitiateResetPasswordByEmail() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.initiateResetPasswordByEmail("requestId", "email", null);
    }

    @Test
    public void testInitiateResetPasswordBySMS() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.initiateResetPasswordBySMS("requestId", "mobileNumber", null);
    }

    @Test
    public void testHandleResetPassword() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.handleResetPassword("verificationCode", "rprq", null);
    }

    @Test
    public void testResetPassword() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.resetPassword(null, null);
    }

    @Test
    public void testChangePassword() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.changePassword("sub", null, null);
    }

    @Test
    public void testGetAccessToken() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.getAccessToken("sub", null);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.customException(anyInt(), any(), anyInt())).thenReturn(new WebAuthError(null));

        cidaas.getUserInfo("sub", null);
    }

    @Test
    public void testRenewToken() throws Exception {
        cidaas.renewToken("refershtoken", null);
    }

    @Test
    public void testLoginWithBrowser() throws Exception {
        cidaas.loginWithBrowser("color", null);
    }

    @Test
    public void testGetLoginCode() throws Exception {
        when(cidaasInstance.getCodeFromUrl(any())).thenReturn("getCodeFromUrlResponse");

        cidaas.getLoginCode("url", null);
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
        cidaas.getAccessTokenByCode("code", null);
    }


    @Test
    public void testLoginWithFIDO() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.loginWithFIDO("usageType", "email", "sub", "trackId", null);
    }

    @Test
    public void testGetLoginURL() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getLoginURL(null);
    }

    @Test
    public void testGetLoginURL2() throws Exception {
        cidaas.getLoginURL("RequestId", null);
    }

    @Test
    public void testGetLoginURL3() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getLoginURL("DomainUrl", "ClientId", "RedirectURL", "ClientSecret", null);
    }

    @Test
    public void testGetLoginURL4() throws Exception {
        cidaas.getLoginURL(null, null);
    }

    @Test
    public void testResume() throws Exception {
        when(cidaasInstance.getCodeFromUrl(any())).thenReturn("getCodeFromUrlResponse");

        cidaas.resume("code");
    }

    @Test
    public void testConfigureFIDO() throws Exception {
        when(webAuthError.getShared(any())).thenReturn(new WebAuthError(null));
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.configureFIDO("sub", null);
    }*/
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme