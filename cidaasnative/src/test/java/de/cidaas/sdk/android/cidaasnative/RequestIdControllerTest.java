package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.requestid.RequestIdController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;


@RunWith(RobolectricTestRunner.class)
@Ignore
public class RequestIdControllerTest {
    Context context;
    RequestIdController shared;
    RequestIdController requestIdController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        requestIdController = new RequestIdController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        requestIdController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        RequestIdController result = RequestIdController.getShared(null);
        Assert.assertTrue(result instanceof RequestIdController);
    }

    @Test
    public void testGetRequestId() throws Exception {
        Dictionary<String, String> loginProperties = new Hashtable<>();
        loginProperties.put("DomainURL", "");
        loginProperties.put("ClientId", "");
        loginProperties.put("RedirectURL", "RedirectURL");

        requestIdController.getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void nullRedirect() throws Exception {
        Dictionary<String, String> loginProperties = new Hashtable<>();
        loginProperties.put("DomainURL", "Dom");
        loginProperties.put("ClientId", "Clien");
        loginProperties.put("RedirectURL", "");

        requestIdController.getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void nulLogint() throws Exception {
        Dictionary<String, String> loginProperties = new Hashtable<>();
        loginProperties.put("DomainURL", "Dom");
        loginProperties.put("ClientId", "Clien");
        loginProperties.put("RedirectURL", "");

        requestIdController.getRequestId(null, new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void nullClientId() throws Exception {
        Dictionary<String, String> loginProperties = new Hashtable<>();
        loginProperties.put("DomainURL", "Domain");
        loginProperties.put("ClientId", "");
        loginProperties.put("RedirectURL", "RedirectURL");

        requestIdController.getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme