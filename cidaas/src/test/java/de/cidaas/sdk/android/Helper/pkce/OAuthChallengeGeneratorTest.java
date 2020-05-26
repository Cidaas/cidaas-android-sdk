package de.cidaas.sdk.android.Helper.pkce;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.helper.pkce.OAuthChallengeGenerator;


public class OAuthChallengeGeneratorTest {
    @Mock
    OAuthChallengeGenerator shared;
    @InjectMocks
    OAuthChallengeGenerator oAuthChallengeGenerator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        OAuthChallengeGenerator result = OAuthChallengeGenerator.getShared();
        Assert.assertTrue(result instanceof OAuthChallengeGenerator);
    }

    @Test
    public void testGetCodeChallenge() throws Exception {
        String result = oAuthChallengeGenerator.getCodeChallenge("codeVerifier");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testGetCodeVerifier() throws Exception {
        String result = oAuthChallengeGenerator.getCodeVerifier();
        Assert.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme