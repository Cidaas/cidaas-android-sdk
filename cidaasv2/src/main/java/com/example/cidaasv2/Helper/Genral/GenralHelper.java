package com.example.cidaasv2.Helper.Genral;

import com.example.cidaasv2.Helper.Entity.SocialAccessTokenEntity;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;

import java.util.Dictionary;

public class GenralHelper {
    //Shared Instances
    public static GenralHelper shared;


    public static String responseType = "code";
    public static String viewType = "login";
    public static String grantType = "authorization_code";
    public static String codeChallengeMethod = "S256";
    public static String contentType = "application/x-www-form-urlencoded";


    public static GenralHelper getShared()
    {
        if(shared==null)
        {
            shared=new GenralHelper();
        }
        return shared;
    }


    public  String constructSocialServiceURl(SocialAccessTokenEntity socialAccessTokenEntity, String givenType, Dictionary<String,String> loginProperties) {

        return loginProperties.get("DomainURL")+ URLHelper.getShared().getSocialTokenURL() + "?codeOrToken=" + socialAccessTokenEntity.getToken() +
                "&provider=" + socialAccessTokenEntity.getProvider() + "&clientId=" +loginProperties.get("ClientId") + "&givenType=" + givenType +
                "&responseType=" + GenralHelper.responseType + "&redirectUrl=" + loginProperties.get("RedirectURL") + "&viewtype=" + viewType;
    }
}
