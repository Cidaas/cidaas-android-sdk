package de.cidaas.sdk.android.helper.general;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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

    public static FileHelper getShared(Context context) {
        if (instance == null) {
            instance = new FileHelper(context);
        }
        return instance;
    }

    private FileHelper(Context context) {
        this.context = context;
        webAuthError = WebAuthError.getShared(this.context);
    }

    //Read Properties
    public void readProperties(AssetManager assetManager, String fileNameFromBase, EventResult<Dictionary<String, String>> result) {
        String methodName = "FileHelper: readProperties()";
        InputStream inputStream;
        Dictionary<String, String> dictObject = new Hashtable<>();

        // check the file name
        try {

            inputStream = assetManager.open(fileNameFromBase);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            Document xml = parseXML(outputStream.toByteArray());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//resources/item[@string]");
            NodeList nl = (NodeList) expr.evaluate(xml, XPathConstants.NODESET);
            NodeList nodeList = xml.getElementsByTagName("item");
            String xmlString = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(CLIENT_ID)) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    //Assign Value in a string Dictionary
                    if (isNotEmpty(xmlString)) {
                        dictObject.put(CLIENT_ID, xmlString);
                    } else {
                        throw new Exception("property clientid cannot be null");
                    }
                } else if (nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(DOMAIN_URL)) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    if (isNotEmpty(xmlString)) {
                        dictObject.put(DOMAIN_URL, xmlString);
                    } else {
                        throw new Exception("property domainurl cannot be null");
                    }
                } else if (nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(REDIRECT_URL)) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    if (isNotEmpty(xmlString)) {
                        dictObject.put(REDIRECT_URL, xmlString);
                    } else {
                        throw new Exception("property redirecturl cannot be null");
                    }
                } else {
                    webAuthError.propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty", "Error" + methodName);
                    result.failure(webAuthError);
                }
            }
            if (dictObject.size() == 3) {
                result.success(dictObject);
            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            if (e instanceof FileNotFoundException) {
                webAuthError = webAuthError.fileNotFoundException(methodName);
                LogFile.getInstance(context).addFailureLog(webAuthError.getErrorMessage() + webAuthError.getErrorCode());
                result.failure(webAuthError);
            } else if (e instanceof XPathExpressionException || e instanceof NullPointerException) {
                webAuthError = webAuthError.noContentInFileException(methodName);
                LogFile.getInstance(context).addFailureLog(e.getMessage() + WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(webAuthError);
            } else {
                LogFile.getInstance(context).addFailureLog(e.getMessage() + WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(WebAuthError.getShared(context).methodException("Exception :FileHelper :readProperties()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
            }

            result.failure(WebAuthError.getShared(context).methodException("Exception :FileHelper :readProperties()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }

    }

    //Parse XML
    public Document parseXML(byte[] inputStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(inputStream));
        } catch (Exception e) {
            LogFile.getInstance(context).addFailureLog(e.getMessage() + WebAuthErrorCode.PARSE_XML);
            return null;

        }
    }

    //Convert parameter into a Dictionary Object
    public void paramsToDictionaryConverter(@NonNull String domainUrl, @NonNull String clientId, @NonNull String redirectURL, @NonNull String clientSecret, EventResult<Dictionary<String, String>> callback) {
        try {

            if (isNotEmpty(clientId)
                    && isNotEmpty(domainUrl)
                    && isNotEmpty(redirectURL)
                    && isNotEmpty(clientSecret)) {


                CidaasHelper.baseurl = domainUrl;

                //Disable PKCE Flow
                DBHelper.getShared().setEnablePKCE(false);
                CidaasHelper.ENABLE_PKCE = false;
                Dictionary<String, String> loginProperties = new Hashtable<>();
                loginProperties.put(CLIENT_ID, clientId);
                loginProperties.put(DOMAIN_URL, domainUrl);
                loginProperties.put(REDIRECT_URL, redirectURL);
                loginProperties.put(CLIENT_SECRET, clientSecret);

                DBHelper.getShared().addLoginProperties(loginProperties);

                callback.success(loginProperties);
            } else {
                //T handle Error
                callback.failure(webAuthError.CidaaspropertyMissingException("", "Error:-FileHelper :paramsToDictionaryConverter()"));
            }
        } catch (Exception e) {
            callback.failure(webAuthError.methodException("Exception :FileHelper :paramsToDictionaryConverter()", WebAuthErrorCode.PARAMS_TO_DICTIONARY_CONVERTER_ERROR, e.getMessage()));
        }
    }


    //Convert parameter into a Dictionary Object
    public void paramsToDictionaryConverter(@NonNull String domainUrl, @NonNull String clientId, @NonNull String redirectURL, EventResult<Dictionary<String, String>> callback) {
        String methodName = "FileHelper :paramsToDictionaryConverter()";
        try {
            Dictionary<String, String> loginProperties = new Hashtable<>();
            if (isNotEmpty(clientId)
                    && isNotEmpty(domainUrl)
                    && isNotEmpty(redirectURL)) {

                CidaasHelper.baseurl = domainUrl;

                loginProperties.put(CLIENT_ID, clientId);
                loginProperties.put(DOMAIN_URL, domainUrl);
                loginProperties.put(REDIRECT_URL, redirectURL);

                DBHelper.getShared().addLoginProperties(loginProperties);
                callback.success(loginProperties);
            } else {
                //T handle Error
                callback.failure(webAuthError.propertyMissingException("ClientId or DomainURL or RedirectURL must not be empty", "Error:" + methodName));
            }
        } catch (Exception e) {
            callback.failure(webAuthError.methodException("Exception :" + methodName, WebAuthErrorCode.PARAMS_TO_DICTIONARY_CONVERTER_ERROR, e.getMessage()));
        }
    }
}
