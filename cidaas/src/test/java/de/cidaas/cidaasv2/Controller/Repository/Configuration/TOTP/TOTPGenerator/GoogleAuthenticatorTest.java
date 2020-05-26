package de.cidaas.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

import org.junit.Test;

public class GoogleAuthenticatorTest {

    @Test
    public void testGetRandomSecretKey() throws Exception {
        String result = GoogleAuthenticator.getRandomSecretKey();
        //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetTOTPCode() throws Exception {
        String result = GoogleAuthenticator.getTOTPCode("secretKey");
        // Assert.assertEquals(any(), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme