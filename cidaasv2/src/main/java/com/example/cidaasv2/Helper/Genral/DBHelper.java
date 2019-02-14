package com.example.cidaasv2.Helper.Genral;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Models.DBModel.UserInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class DBHelper {


    //TOdo Set Enable Log
    public static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String LoginProperties="Login_Properties";
    private static final String ChallengeProperties=" Challenge_Properties";
    private static final String DeviceInfo="Device_info";
    private static final String FCMTokenInfo="FCMTOKEN_info";
    private static final String SecretInfo="Secret_info";
    private static final String UserAgent="UserAgent";
    private static final String userDeviceInfo="User_device_Info";
    private static final String pkceEnableStatus = "OAuthEnablePkce";
    private static final String logEnableStatus = "OAuthEnableLog";
    private static String user_storage_key = "cidaas_user_details_";
    private static String user_storage_info = "cidaas_user_info_";
    private static ObjectMapper shared_objectMapper=new ObjectMapper();
    private static Activity context;

    public static DBHelper dbHelper=null;


    public static DBHelper getShared()
    {
        if(dbHelper==null)
        {
            dbHelper=new DBHelper();
        }
        return dbHelper;
    }


    //setEnablePKCE

    public void setEnablePKCE(boolean enablePKCE)
    {
        try {
            editor.putBoolean(pkceEnableStatus,enablePKCE);
            editor.commit();
        }
        catch (Exception e)
        {

        }
    }



//Get EnablePKCE
    public boolean getEnablePKCE()
    {
        boolean result=false;
        try
        {
            result=preferences.getBoolean(logEnableStatus,true);
        }
        catch (Exception e)
        {
            result=false;
        }

        return result;
    }

//Set Enable Log
public void setEnableLog(boolean enableLog)
{
    try {
        editor.putBoolean(logEnableStatus,enableLog);
        editor.commit();
    }
    catch (Exception e)
    {

    }
}

    //Get EnableLog
    public boolean getEnableLog()
    {
        boolean result=false;
        try
        {
            result=preferences.getBoolean(pkceEnableStatus,true);
        }
        catch (Exception e)
        {
            result=false;
        }

        return result;
    }



    public static void setConfig(Context context)
    {
        if (preferences == null)
        {
            preferences = context.getSharedPreferences("cidaas_preference", 0);
            editor = preferences.edit();
        }
    }



    public void addChallengeProperties(Dictionary<String,String> challengepropObj)
    {
        boolean result=false;
        try {
            String jsonString =shared_objectMapper.writeValueAsString(challengepropObj);
            editor.putString(ChallengeProperties, jsonString);
            result=editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }

    }

    //Add Secret Based on Sub
    public void addSecret(String secret,String sub){
        boolean result=false;
        try {
            editor.putString(SecretInfo+sub,secret);
            editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }

    }

    //get Device info
    public String getSecret(String sub)
    {
        String secret;
        try {
            secret = preferences.getString(SecretInfo+sub, "");
        }
        catch (Exception e)
        {
            secret="";
        }
        return secret;
    }



    //Add Secret Based on Sub
    public void setUserAgent(String userAgent){
        boolean result=false;
        try {
            editor.putString(UserAgent,userAgent);
            editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }

    }

    //get Device info
    public String getUserAgent()
    {
        String userAgent;
        try {
            userAgent = preferences.getString(UserAgent, "");
        }
        catch (Exception e)
        {
            userAgent="";
        }
        return userAgent;
    }


    //Get Login Object
    public Dictionary<String,String> getChallengeProperties()
    {

        Dictionary<String,String> challengeProperties=new Hashtable<>();
        try {
            String jsonString =preferences.getString(ChallengeProperties,(new JSONObject().toString()));
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = (String) jsonObject.get(key);
                challengeProperties.put(key, value);
            }
        }
        catch (Exception e)
        {
            challengeProperties=null;
        }
        return challengeProperties;
    }


    //Add Login object in LocalDB(SharedPreferences) for Future purposes
    public void addLoginProperties(Dictionary<String,String> loginpropObj) {
        boolean result = false;
        try {
            String jsonString = shared_objectMapper.writeValueAsString(loginpropObj);
            editor.putString(LoginProperties+loginpropObj.get("DomainURL"), jsonString);
            result = editor.commit();
        } catch (Exception e) {
            result = false;
        }
    }


    //Get Login Object
    public Dictionary<String,String> getLoginProperties(String DomainURL)
    {

        Dictionary<String,String> loginProperties=new Hashtable<>();
        try {
            String jsonString =preferences.getString(LoginProperties+DomainURL,(new JSONObject().toString()));
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = (String) jsonObject.get(key);
                loginProperties.put(key, value);
            }
        }
        catch (Exception e)
        {
            loginProperties=null;
        }
        return loginProperties;
    }


    //Add Device
    public void addDeviceInfo(DeviceInfoEntity deviceInfoEntity)
    {
        boolean result=false;
        try {
            String jsonString =shared_objectMapper.writeValueAsString(deviceInfoEntity);
            editor.putString(DeviceInfo,jsonString);
            result=editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }
    }

    //get Device info
    public DeviceInfoEntity getDeviceInfo()
    {
        String jsonString =preferences.getString(DeviceInfo,"");
        DeviceInfoEntity deviceInfoEntity;
        try {
            deviceInfoEntity=shared_objectMapper.readValue(jsonString,DeviceInfoEntity.class);
        }
        catch (Exception e)
        {
            deviceInfoEntity=null;
        }
        return deviceInfoEntity;
    }



    //Add Device
    public void setFCMToken(String fcmToken)
    {
        boolean result=false;
        try {
            editor.putString(FCMTokenInfo,fcmToken);
            editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }
    }

    //get Device info
    public String getFCMToken()
    {
        String FCMToken;
        try {
            FCMToken = preferences.getString(FCMTokenInfo, "");
        }
        catch (Exception e)
        {
            FCMToken="";
        }
        return FCMToken;
    }



    //Add User Device id with Domain URL
    public void setUserDeviceId(String userDeviceId,String domainURL)
    {
        boolean result=true;
        try {
            editor.putString(userDeviceInfo+domainURL,userDeviceId);
            editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }
    }

    //get Device info
    public String getUserDeviceId(String DomainURL)
    {
        String userDeviceId;
        try {
            userDeviceId = preferences.getString(userDeviceInfo+DomainURL, "");
        }
        catch (Exception e)
        {
            userDeviceId="";
        }
        return userDeviceId;
    }

    //Done Set Access Token
    public void setAccessToken(AccessTokenModel accessTokenModel)
    {
        boolean result=false;
        try {
            String key=accessTokenModel.getUserId();
            String jsonString =shared_objectMapper.writeValueAsString(accessTokenModel);
            editor.putString(user_storage_key+key, jsonString);
            result=editor.commit();
        }
        catch (Exception e)
        {
            result=false;
        }

    }
    //Done Get Access Token
    public AccessTokenModel getAccessToken(String userId)
    {
        String jsonString =preferences.getString(user_storage_key+userId,"");
        AccessTokenModel accessTokenModel;
        try {
            accessTokenModel=shared_objectMapper.readValue(jsonString,AccessTokenModel.class);
        }
        catch (Exception e)
        {
            accessTokenModel=null;
        }
        return accessTokenModel;
    }
    //done Set UserInfo
    public void setUserInfo(UserInfoModel userInfoModel)
    {
        try {
            String key=userInfoModel.getUserid();
            String jsonString =shared_objectMapper.writeValueAsString(userInfoModel);
            editor.putString(user_storage_info+key, jsonString);
            editor.commit();
        }
        catch (Exception e)
        {
            //result=false;
        }

    }
    //Done Get User info
    public UserInfoModel getUserInfo(String userId)
    {
        String jsonString =preferences.getString(user_storage_info+userId,"DefaultUserinfo");
        UserInfoModel userInfoModel;
        try {
            userInfoModel=shared_objectMapper.readValue(jsonString,UserInfoModel.class);
        }
        catch (Exception e)
        {
            userInfoModel=null;
        }
        return userInfoModel;
    }


    //DeleteUserInfo
    public boolean removeUserInfo(String userId)
    {
        boolean result = false;
        //Remove Access Token
        String savedKey = user_storage_key + userId;
        //Todo Remove User info


        //Todo Remove Userdevice Id
        editor.remove(savedKey);
        result = editor.commit();
        return result;
    }

}
