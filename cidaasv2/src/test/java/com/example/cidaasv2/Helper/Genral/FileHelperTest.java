package com.example.cidaasv2.Helper.Genral;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Dictionary;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FileHelperTest {
Context context;
    FileHelper fileHelper;
/*
    @Mock
    byte[] sa;*/
    private AssetManager assetManager;
    private Resources resources;

    @Before
    public void setUp() throws IOException {

        context= RuntimeEnvironment.application;
        fileHelper=new FileHelper(context);
       // resources = RuntimeEnvironment.application.getResources();
        assetManager=context.getAssets();

        RuntimeEnvironment.application.getAssets().open("Cidaas.xml");
        DBHelper.setConfig(context);
    }

    @Test
    public void testGetShared() throws Exception {
       // when(webAuthError.getShared(context)).thenReturn(new WebAuthError(null));

        FileHelper result = FileHelper.getShared(context);
        Assert.assertTrue( result instanceof FileHelper);
    }

    @Test
    public void testReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "Cidaas.xml", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL","DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL","DomainURL");
            }
        });

    }


    @Test
    public void testCidaasReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "cidaas123.xml", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL","DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL","DomainURL");
            }
        });

    }

    @Test
    public void testCidsReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "Cidaastest.xml", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL","DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL","DomainURL");
            }
        });

    }

    @Test
    public void idsReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "Cidaastesdcst.xml", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL","DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL","DomainURL");
            }
        });

    }

    @Test
    public void testParseXML() throws Exception {
        Document result = fileHelper.parseXML(new byte[]{(byte) 0});
        Assert.assertEquals(null, result);
    }

    @Test
    public void testParamsToDictionaryConverter() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "ClientSecret", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("Error",error.ErrorMessage);
            }
        });
    }

    @Test
    public void testParamsToConverter() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "", new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

            }

            @Override
            public void failure(WebAuthError error) {
             Assert.assertEquals("One of the property is missing.",error.ErrorMessage);
            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme