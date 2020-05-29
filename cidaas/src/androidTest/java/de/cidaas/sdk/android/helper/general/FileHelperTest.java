package de.cidaas.sdk.android.helper.general;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Dictionary;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;

@RunWith(AndroidJUnit4.class)
public class FileHelperTest {

    private static final String CIDAAS_XML = "cidaas.xml";
    private static final String CIDAAS_MISSING_PROPERTIES = "cidaas_missingproperties.xml";
    private static final String CIDAAS_EMPTY_PROPERTIES = "cidaas_emptyproperties.xml";
    private static final String CIDAAS_UNKNOWN_PROPERTIES = "cidaas_unknownproperties.xml";
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
    public void setUp() {
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
    public void testReadProperties() {
        fileHelper.readProperties(assetManager, CIDAAS_XML, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals(DOMAIN_URL_TESTENTRY, result.get(DOMAIN_URL));
                Assert.assertEquals(CLIENT_ID_TESTENTRY, result.get(CLIENT_ID));
                Assert.assertEquals(REDIRECT_URL_TESTENTRY, result.get(REDIRECT_URL));
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Must not be called", false);
            }
        });
    }

    @Test
    public void readMissingProperties() {
        fileHelper.readProperties(assetManager, CIDAAS_MISSING_PROPERTIES, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Should be get errormessage 'properties must be set'",
                        error.getErrorEntity().getError().equalsIgnoreCase("All properties (" + CLIENT_ID + ", " + REDIRECT_URL + " AND " + DOMAIN_URL + ") must be set"));
            }
        });
    }

    @Test
    public void readEmptyProperties() {
        fileHelper.readProperties(assetManager, CIDAAS_EMPTY_PROPERTIES, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Should be get errormessage 'properties cannot be null or empty'",
                        error.getDetailedErrorMessage().equalsIgnoreCase("property -" + DOMAIN_URL + "- cannot be null or empty"));
            }
        });
    }

    @Test
    public void readUnknownProperties() {
        fileHelper.readProperties(assetManager, CIDAAS_UNKNOWN_PROPERTIES, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Should be get errormessage 'unknown property'",
                        error.getDetailedErrorMessage().equalsIgnoreCase("invalid property entry for: UNKNOWN"));
            }
        });
    }

    @Test
    public void readEmptyFile() {
        fileHelper.readProperties(assetManager, CIDAAS_EMPTY_FILE, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Should be get errormessage 'No content in property file.'",
                        error.getErrorEntity().getError().equalsIgnoreCase("No content in property file."));
            }
        });
    }

    @Test
    public void readNoFile() {
        fileHelper.readProperties(assetManager, CIDAAS_NO_FILE, new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Should be get errormessage 'Property file is missing.'",
                        error.getErrorEntity().getError().equalsIgnoreCase("Property file is missing."));
            }
        });
    }


    @Test
    public void testFourParamsToDictionaryConverter() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "ClientSecret", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals(4, result.size());
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Must not be called", false);
            }
        });
    }

    @Test
    public void testThreeParamsToDictionaryConverter() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertEquals(3, result.size());
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue("Must not be called", false);
            }
        });
    }

    @Test
    public void testFourParamsToConverterWithEmptyField() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "RedirectURL", "", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL or ClientId or Redirect is missing.", error.getErrorMessage());
            }
        });
    }

    @Test
    public void testThreeParamsToConverterWithEmptyField() throws Exception {

        fileHelper.paramsToDictionaryConverter("DomainUrl", "ClientId", "", new EventResult<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Assert.assertTrue("Must not be called", false);
            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("DomainURL or ClientId or Redirect is missing.", error.getErrorMessage());
            }
        });
    }
}