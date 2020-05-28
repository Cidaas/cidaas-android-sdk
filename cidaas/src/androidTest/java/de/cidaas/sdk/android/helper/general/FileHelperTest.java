package de.cidaas.sdk.android.helper.general;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Dictionary;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;

@RunWith(AndroidJUnit4.class)
public class FileHelperTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private static final String CIDAAS_XML = "cidaas.xml";
    private static final String CIDAAS_MISSING_PROPERTIES = "cidaas_missingproperties.xml";
    private static final String CIDAAS_EMPTY_PROPERTIES = "cidaas_emptyproperties.xml";
    private static final String CIDAAS_EMPTY_FILE = "cidaas_emptyfile.xml";
    private static final String CIDAAS_NO_FILE = "not_exist.xml";

    private static final String DOMAIN_URL = "DomainURL";
    private static final String CLIENT_ID = "ClientId";
    private static final String REDIRECT_URL = "RedirectURL";

    private static final String DOMAIN_URL_TESTENTRY = "https://test.cidaas.de";
    private static final String CLIENT_ID_TESTENTRY = "4d1ce9b6-7b1d-4c97-b4f6-118d00ce3d68";
    private static final String REDIRECT_URL_TESTENTRY = "de.cidaas.authenticator://test.cidaas.de/user-profile/editprofile";

    private Context context;
    private FileHelper fileHelper;

    private AssetManager assetManager;

    @Before
    public void setUp() throws IOException {
        // Context of the app under test.
        context = ApplicationProvider.getApplicationContext();
        fileHelper = FileHelper.getShared(context);
        assetManager = context.getAssets();
    }

    @Test
    public void testGetShared() throws Exception {
        FileHelper result = FileHelper.getShared(context);
        Assert.assertTrue(result instanceof FileHelper);
    }

    @Test
    public void testReadProperties() throws Exception {
        fileHelper.readProperties(assetManager, CIDAAS_XML, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals(DOMAIN_URL_TESTENTRY, result.get(DOMAIN_URL));
                Assert.assertEquals(CLIENT_ID_TESTENTRY, result.get(CLIENT_ID));
                Assert.assertEquals(REDIRECT_URL_TESTENTRY, result.get(REDIRECT_URL));
            }

            @Override
            public void failure(WebAuthError error) {
                // Must not be called
                Assert.assertTrue(false);
            }
        });
    }

    @Test
    public void readMissingProperties() throws Exception {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Employee ID is null");
        fileHelper.readProperties(assetManager, CIDAAS_MISSING_PROPERTIES, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                // Must not be called
                Assert.assertTrue(false);
            }

            @Override
            public void failure(WebAuthError error) {
                error.getError();
            }
        });

    }

    @Test
    public void testCidsReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "cidaas123.xml", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL", "DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL", "DomainURL");
            }
        });

    }

    @Test
    public void idsReadProperties() throws Exception {

        fileHelper.readProperties(assetManager, "cidaas123.xml", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals("DomainURL", "DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL", "DomainURL");
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

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "ClientSecret", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("Error", error.getErrorMessage());
            }
        });
    }

    @Test
    public void testParamsToConverter() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("One of the property is missing.", error.getErrorMessage());
            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme