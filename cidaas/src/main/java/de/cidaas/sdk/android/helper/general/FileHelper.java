package de.cidaas.sdk.android.helper.general;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class FileHelper {

    private static final String CLIENT_ID = "ClientId";
    private static final String DOMAIN_URL = "DomainURL";
    private static final String REDIRECT_URL = "RedirectURL";
    private static final String CLIENT_SECRET = "ClientSecret";
    //Shared Instances
    private static FileHelper instance;
    private Context context;
    private WebAuthError webAuthError;

    private FileHelper(Context context) {
        this.context = context;
        webAuthError = error();
    }

    public static FileHelper getShared(Context context) {
        if (instance == null) {
            instance = new FileHelper(context);
        }
        return instance;
    }

    public void readProperties(AssetManager assetManager, String fileNameFromBase, EventResult<Dictionary<String, String>> result) {
        try (InputStream inputStream = assetManager.open(fileNameFromBase)) {
            Document document = parseXML(inputStream);
            Dictionary<String, String> dictObject = documentToDictionary(result, document);
            if (dictObject.size() == 3) {
                result.success(dictObject);
            } else {
                String errorMessage = "All properties (" + CLIENT_ID + ", " + REDIRECT_URL + " AND " + DOMAIN_URL + ") must be set";
                LogFile.getShared(context).addFailureLog(errorMessage + WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(error().methodException("readProperties", WebAuthErrorCode.READ_PROPERTIES_ERROR, errorMessage));
            }
        } catch (Exception e) {
            handleException(result, e);
        }

    }

    public void paramsToDictionaryConverter(@NonNull String domainUrl, @NonNull String clientId, @NonNull String redirectURL, @NonNull String clientSecret, EventResult<Dictionary<String, String>> callback) {
        if (checkRequiredFields(domainUrl, clientId, redirectURL)
                && isNotEmpty(clientSecret)) {
            CidaasHelper.baseurl = domainUrl;
            //Disable PKCE Flow
            DBHelper.getShared().setEnablePKCE(false);
            CidaasHelper.ENABLE_PKCE = false;

            fillDictionary(domainUrl, clientId, redirectURL, clientSecret, callback);
        } else {
            callback.failure(webAuthError.cidaasPropertyMissingException("properties are missing", "paramsToDictionaryConverter"));
        }
    }

    public void paramsToDictionaryConverter(String domainUrl, String clientId, String redirectURL, EventResult<Dictionary<String, String>> callback) {
        if (checkRequiredFields(domainUrl, clientId, redirectURL)) {
            CidaasHelper.baseurl = domainUrl;
            fillDictionary(domainUrl, clientId, redirectURL, "", callback);
        } else {
            callback.failure(webAuthError.cidaasPropertyMissingException("properties are missing", "paramsToDictionaryConverter"));
        }
    }

    private Dictionary<String, String> documentToDictionary(EventResult<Dictionary<String, String>> result, Document document) {
        NodeList nodeList = document.getElementsByTagName("item");
        Dictionary<String, String> dictObject = new Hashtable<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (getNodeNameValue(node).equalsIgnoreCase(CLIENT_ID)) {
                handleNodeListEntry(dictObject, CLIENT_ID, getNodeContent(node), result);
            } else if (getNodeNameValue(node).equalsIgnoreCase(DOMAIN_URL)) {
                handleNodeListEntry(dictObject, DOMAIN_URL, getNodeContent(node), result);
            } else if (getNodeNameValue(node).equalsIgnoreCase(REDIRECT_URL)) {
                handleNodeListEntry(dictObject, REDIRECT_URL, getNodeContent(node), result);
            } else {
                webAuthError.invalidPropertiesException("invalid property entry for: " + getNodeNameValue(node), "readProperties");
                result.failure(webAuthError);
            }
        }
        return dictObject;
    }

    private void handleException(EventResult<Dictionary<String, String>> result, Exception e) {
        if (e instanceof FileNotFoundException) {
            webAuthError = webAuthError.fileNotFoundException("");
            LogFile.getShared(context).addFailureLog(webAuthError.getErrorMessage() + webAuthError.getErrorCode());
            result.failure(webAuthError);
        } else if (e instanceof SAXParseException || e instanceof XPathExpressionException || e instanceof NullPointerException) {
            webAuthError = webAuthError.noContentInFileException("");
            LogFile.getShared(context).addFailureLog(e.getMessage() + WebAuthErrorCode.READ_PROPERTIES_ERROR);
            result.failure(webAuthError);
        } else {
            result.failure(error().methodException("readProperties", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }
    }

    private void handleNodeListEntry(Dictionary<String, String> dictObject, String key, String xmlString, EventResult<Dictionary<String, String>> result) {

        if (key.equalsIgnoreCase(DOMAIN_URL) || key.equalsIgnoreCase(REDIRECT_URL)) {
            if (checkUrl(xmlString)) {
                checkEmptyAndAddString(dictObject, key, xmlString, result);
            } else {
                String errorMessage = "Property -" + key + "- must be a valid URL";
                LogFile.getShared(context).addFailureLog(errorMessage + WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(error().propertyMissingException(errorMessage, "readProperties"));
            }
        } else {
            checkEmptyAndAddString(dictObject, key, xmlString, result);
        }

    }

    private void checkEmptyAndAddString(Dictionary<String, String> dictObject, String key, String xmlString, EventResult<Dictionary<String, String>> result) {
        if (isNotEmpty(xmlString)) {
            dictObject.put(key, xmlString);
        } else {
            String errorMessage = "property -" + key + "- cannot be null or empty";
            LogFile.getShared(context).addFailureLog(errorMessage + WebAuthErrorCode.READ_PROPERTIES_ERROR);
            result.failure(error().propertyMissingException(errorMessage, "readProperties"));
        }
    }

    private boolean checkUrl(String urlString) {
        return urlString.contains("://");
    }

    private void fillDictionary(String domainUrl, String clientId, String redirectURL, String clientSecret, EventResult<Dictionary<String, String>> callback) {
        Dictionary<String, String> loginProperties = new Hashtable<>();
        loginProperties.put(CLIENT_ID, clientId);
        loginProperties.put(DOMAIN_URL, domainUrl);
        loginProperties.put(REDIRECT_URL, redirectURL);
        if (isNotEmpty(clientSecret)) {
            loginProperties.put(CLIENT_SECRET, clientSecret);
        }
        DBHelper.getShared().addLoginProperties(loginProperties);
        callback.success(loginProperties);
    }

    private Document parseXML(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
    }

    private boolean checkRequiredFields(String domainUrl, String clientId, String redirectURL) {
        return isNotEmpty(clientId)
                && isNotEmpty(domainUrl)
                && isNotEmpty(redirectURL);
    }

    private String getNodeContent(Node node) {
        return node.getTextContent().trim();
    }

    private String getNodeNameValue(Node node) {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    private WebAuthError error() {
        return WebAuthError.getShared(context);
    }
}