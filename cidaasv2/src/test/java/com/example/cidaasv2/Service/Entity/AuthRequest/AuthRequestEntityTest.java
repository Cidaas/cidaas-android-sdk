package com.example.cidaasv2.Service.Entity.AuthRequest;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthRequestEntityTest {

    @Test
    public void getClientSecret()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setClient_secret("clientSecret");
        assertTrue(authRequestEntity.getClient_secret() == "clientSecret");
    }
    @Test
    public void getClientId()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setClient_id("clientID");
        assertTrue(authRequestEntity.getClient_id() == "clientID");
    }
    @Test
    public void getCodeChallenge()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setCode_challenge("codeChallenge");
        assertTrue(authRequestEntity.getCode_challenge() == "codeChallenge");
    }

    @Test
    public void getcodeChallengeMethod()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setCode_challenge_method("codeChallengeMethod");
        assertTrue(authRequestEntity.getCode_challenge_method() == "codeChallengeMethod");
    }
    @Test
    public void getNonce()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setNonce("nonce");
        assertTrue(authRequestEntity.getNonce() == "nonce");
    }
    @Test
    public void getRedirectURI()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setRedirect_uri("redirectURI");
        assertTrue(authRequestEntity.getRedirect_uri() == "redirectURI");
    }

    @Test
    public void getResponseType()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setResponse_type("ResponseType");
        assertTrue(authRequestEntity.getResponse_type() == "ResponseType");
    }
    @Test
    public void getScope()
    {
        AuthRequestEntity authRequestEntity = new AuthRequestEntity();
        authRequestEntity.setScope("scope");
        assertTrue(authRequestEntity.getScope() == "scope");
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme