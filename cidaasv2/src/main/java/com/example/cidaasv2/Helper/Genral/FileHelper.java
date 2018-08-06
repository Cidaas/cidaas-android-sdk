package com.example.cidaasv2.Helper.Genral;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;

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

import timber.log.Timber;

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
        InputStream inputStream;
        AssetManager asstManager= assetManager;
        String fileName=fileNameFromBase;
        Dictionary<String, String> dictObject=new Hashtable<>();

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
                e.printStackTrace();
            }
            Document xml=parseXML(outputStream.toByteArray());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//resources/item[@string]");
            NodeList nl = (NodeList) expr.evaluate(xml, XPathConstants.NODESET);
            NodeList nodeList = xml.getElementsByTagName("item");
            String sam=null;
            for(int i=0;i<nodeList.getLength();i++) {
                if (nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("ClientId")) {
                    sam = nodeList.item(i).getTextContent().trim();
                    //Assign Value in a string Dictionary
                    if(sam!=null && sam!="") {
                        dictObject.put("ClientId", sam);
                    }
                    else
                    {
                        throw new Exception("Property Cannot be null");
                    }
                }
                else if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("DomainURL")) {
                    sam = nodeList.item(i).getTextContent().trim();
                    if(sam!=null && sam!="") {
                        dictObject.put("DomainURL", sam);
                    }
                    else
                    {
                        throw new Exception("Property Cannot be null");
                    }
                }
                else if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("RedirectURL")) {
                    sam = nodeList.item(i).getTextContent().trim();
                    if(sam!=null && sam!="") {
                        dictObject.put("RedirectURL", sam);
                    }
                    else
                    {
                        throw new Exception("Property Cannot be null");
                    }
                }
                else if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("UserInfoURL")) {
                    sam = nodeList.item(i).getTextContent().trim();
                    if(sam!=null && sam!="") {
                        dictObject.put("UserInfoURL", sam);
                    }
                    else
                    {
                        throw new Exception("Property Cannot be null");
                    }
                }
                else
                {
                   webAuthError.propertyMissingException();
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

            if(e.getMessage().equalsIgnoreCase("Property Cannot be null"))
            {
                webAuthError = webAuthError.propertyMissingException();
            }
            else if (e instanceof FileNotFoundException)
            {
                webAuthError = webAuthError.fileNotFoundException();
            }
            else if(e instanceof XPathExpressionException || e instanceof NullPointerException)
            {
                webAuthError=webAuthError.noContentInFileException();
            }
            else
            {
                webAuthError.ErrorMessage=e.getMessage();
            }

             result.failure(webAuthError);
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
            return null;
        }
    }

//Convert parameter into a Dictionary Object
    public void paramsToDictionaryConverter(@NonNull String DomainUrl,@NonNull String ClientId,@NonNull String RedirectURL,@Nullable String ClientSecret, Result<Dictionary<String,String>> callback)
    {
        try {
            Dictionary<String, String> loginProperties = new Hashtable<>();
            if (ClientId != null && !ClientId.equals("") && DomainUrl != null && !DomainUrl.equals("") && RedirectURL != null && !RedirectURL.equals("") && ClientSecret != null && !ClientSecret.equals("")) {

                loginProperties.put("ClientId", ClientId);
                loginProperties.put("DomainURL", DomainUrl);
                loginProperties.put("RedirectURL", RedirectURL);
                loginProperties.put("ClientSecret",ClientSecret);

                callback.success(loginProperties);
            } else {
                //T handle Error
                callback.failure(webAuthError.propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            //Todo handle Exception
        }
    }
}
