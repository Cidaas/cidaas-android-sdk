package de.cidaas.cidaasv2.Controller;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)
public class CidaasTest {

    Context context;

    Cidaas cidaas;

    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        cidaas = new Cidaas(context);


    }

    @Test
    public void testGetInstance() throws Exception {

        Cidaas result = Cidaas.getInstance(context);

        Assert.assertTrue(result instanceof Cidaas);

    }
}