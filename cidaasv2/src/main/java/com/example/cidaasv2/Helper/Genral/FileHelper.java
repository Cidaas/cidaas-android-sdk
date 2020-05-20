package com.example.cidaasv2.Helper.Genral;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;

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

import androidx.annotation.NonNull;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class FileHelper {

    //Shared Instances
    public static FileHelper shared;
    public Context context;
    WebAuthError webAuthError;
    public static FileHelper getShared(Context context)
    {
        if(shared==null)
        {
            shared=new FileHelper(context);
        }
        return shared;
    }

    FileHelper(Context context) {
        this.context = context;
        webAuthError=WebAuthError.getShared(this.context);
    }

    //Read Properties
    public void readProperties(AssetManager assetManager,String fileNameFromBase,Result<Dictionary<String,String>> result)
    {
        String methodName="FileHelper: readProperties()";
        InputStream inputStream;
        AssetManager asstManager= assetManager;
        String fileName=fileNameFromBase;
        Dictionary<String, String> dictObject=new Hashtable<>();

        //todo check the file name

        Activity activity;
        try {

            inputStream = assetManager.open(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            }catch (IOException e){
                 //e.printStackTrace();
            }
            Document xml=parseXML(outputStream.toByteArray());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//resources/item[@string]");
            NodeList nl = (NodeList) expr.evaluate(xml, XPathConstants.NODESET);
            NodeList nodeList = xml.getElementsByTagName("item");
            String xmlString=null;
            for(int i=0;i<nodeList.getLength();i++) {
                if (nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("ClientId")) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    //Assign Value in a string Dictionary
                    if(xmlString!=null && xmlString!="") {
                        dictObject.put("ClientId", xmlString);
                    }
                    else
                    {
                        throw new Exception("Property ClientID Cannot be null");
                    }
                }
                else if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("DomainURL")) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    if(xmlString!=null && xmlString!="") {
                        dictObject.put("DomainURL", xmlString);
                    }
                    else
                    {
                        throw new Exception("Property DomainURL Cannot be null");
                    }
                }
                else if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("RedirectURL")) {
                    xmlString = nodeList.item(i).getTextContent().trim();
                    if(xmlString!=null && xmlString!="") {
                        dictObject.put("RedirectURL", xmlString);
                    }
                    else
                    {
                        throw new Exception("Property RedirectURL Cannot be null");
                    }
                }
                else
                {
                   webAuthError.propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty","Error"+methodName);
                   result.failure(webAuthError);
                }
            }
            if(dictObject.size()!=0) {
                result.success(dictObject);
            }
            else
            {
                throw new Exception("File is Corrupted,All properties are missing");
            }

        }
        catch (Exception e) {

            if (e instanceof FileNotFoundException)
            {
                webAuthError = webAuthError.fileNotFoundException(methodName);
                LogFile.getShared(context).addFailureLog(webAuthError.getErrorMessage()+webAuthError.getErrorCode());
                result.failure(webAuthError);
            }
            else if(e instanceof XPathExpressionException || e instanceof NullPointerException)
            {
                webAuthError=webAuthError.noContentInFileException(methodName);
                LogFile.getShared(context).addFailureLog(e.getMessage()+WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(webAuthError);
            }
            else
            {
                //webAuthError.ErrorMessage=e.getMessage();
                LogFile.getShared(context).addFailureLog(e.getMessage()+WebAuthErrorCode.READ_PROPERTIES_ERROR);
                result.failure(WebAuthError.getShared(context).methodException("Exception :FileHelper :readProperties()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
            }

             result.failure(WebAuthError.getShared(context).methodException("Exception :FileHelper :readProperties()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
        }

    }

  //Parse XML
    public Document parseXML(byte[] inputStream)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {

            byte[] Docfile=inputStream;
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(Docfile));
        }
        catch (Exception e)
        {
            LogFile.getShared(context).addFailureLog(e.getMessage()+WebAuthErrorCode.PARSE_XML);
            return null;

        }
    }

//Convert parameter into a Dictionary Object
    public void paramsToDictionaryConverter(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL, @NonNull String ClientSecret, Result<Dictionary<String,String>> callback)
    {
        try {
            Dictionary<String, String> loginProperties = new Hashtable<>();
            if (ClientId != null && !ClientId.equals("") && DomainUrl != null && !DomainUrl.equals("") && RedirectURL != null && !RedirectURL.equals("") && ClientSecret != null && !ClientSecret.equals("")) {


                CidaasHelper.baseurl=DomainUrl;

                //Disable PKCE Flow
                DBHelper.getShared().setEnablePKCE(false);
                CidaasHelper.ENABLE_PKCE=false;

                loginProperties.put("ClientId", ClientId);
                loginProperties.put("DomainURL", DomainUrl);
                loginProperties.put("RedirectURL", RedirectURL);
                loginProperties.put("ClientSecret",ClientSecret);

                DBHelper.getShared().addLoginProperties(loginProperties);

                callback.success(loginProperties);
            } else {
                //T handle Error
                callback.failure(webAuthError.CidaaspropertyMissingException("","Error:-FileHelper :paramsToDictionaryConverter()"));
            }
        }
        catch (Exception e)
        {
            callback.failure(webAuthError.methodException("Exception :FileHelper :paramsToDictionaryConverter()",WebAuthErrorCode.PARAMS_TO_DICTIONARY_CONVERTER_ERROR,e.getMessage()));
        }
    }


    //Convert parameter into a Dictionary Object
    public void paramsToDictionaryConverter(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL, Result<Dictionary<String,String>> callback)
    {
        String methodName="FileHelper :paramsToDictionaryConverter()";
        try {
            Dictionary<String, String> loginProperties = new Hashtable<>();
            if (ClientId != null && !ClientId.equals("") && DomainUrl != null && !DomainUrl.equals("") && RedirectURL != null && !RedirectURL.equals("") ) {

                CidaasHelper.baseurl=DomainUrl;

                loginProperties.put("ClientId", ClientId);
                loginProperties.put("DomainURL", DomainUrl);
                loginProperties.put("RedirectURL", RedirectURL);

                DBHelper.getShared().addLoginProperties(loginProperties);
                callback.success(loginProperties);
            } else {
                //T handle Error
                callback.failure(webAuthError.propertyMissingException("ClientId or DomainURL or RedirectURL must not be empty","Error:"+methodName));
            }
        }
        catch (Exception e)
        {
            callback.failure(webAuthError.methodException("Exception :"+methodName,WebAuthErrorCode.PARAMS_TO_DICTIONARY_CONVERTER_ERROR,e.getMessage()));
        }
    }
}
