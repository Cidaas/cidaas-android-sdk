package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.controller.LoginController;


@RunWith(RobolectricTestRunner.class)
@Ignore
public class LoginControllerTest {
    Context context;
    LoginController loginController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        loginController = new LoginController(context);
    }

    @Test
    public void testGetShared() {
        LoginController result = LoginController.getShared(null);
        Assert.assertTrue(result instanceof LoginController);
    }


}