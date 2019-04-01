package com.example.cidaasv2.Service.Repository;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class OauthService {



    CidaassdkService service;

    public Context context;
    public static OauthService shared;
    public WebAuthError webAuthError;
    URLHelper urlHelper=new URLHelper();
    public ObjectMapper objectMapper=new ObjectMapper();



    //Todo Create Shared instances
    public static OauthService getShared(Context contextfromcidaas)
    {
        if(shared==null)
        {
            shared=new OauthService(contextfromcidaas);
        }
        return shared;
    }
    //Constructor
    OauthService(Context contextfromcidaas)
    {
        this.context=contextfromcidaas;
        if(service==null) {
             service=new CidaassdkService();
        }
        webAuthError=new WebAuthError(context);
    }


    //Get Login URL
    public void getLoginUrl(String requestId, @NonNull String DomainURL, final Result<String> callback)
    {
       try {
           //Local Variables
           String finalUrl = "";
           String baseUrl = "";

           URLHelper urlComponents;

           //Get Properties From DB
           Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(DomainURL);

           if (loginProperties == null) {
               callback.failure(webAuthError.loginURLMissingException());
           }


           baseUrl = loginProperties.get("DomainURL");

           //Todo Construct URl Checking,Add Parameter(FieldMap) pending
           urlComponents = new URLHelper();
          // finalUrl = urlComponents.constructURL(loginProperties.get("DomainURL"),loginProperties.get("ClientId"));
               callback.success(finalUrl);
       }
       catch (Exception e)
       {
           Timber.d(e.getMessage());
           callback.failure(webAuthError);
       }
    }


    public void getUserinfo(String AccessToken,String DomainURL, final Result<UserinfoEntity> callback) {
        try {
            //Local Variables
            String url = "";
            String baseUrl = "";

            Map<String, String> headers = new Hashtable<>();
            Map<String, String> querymap = new Hashtable<>();

            //get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentType);
            headers.put("access_token", AccessToken);
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());


            //Get Properties From DB

            Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(DomainURL);
            if (loginProperties == null) {
                callback.failure(webAuthError.loginURLMissingException());
            }


         //   querymap.put("UserInfoURL",loginProperties.get("UserInfoURL"));

            //Assign Url
            //TOdo Perform Null Check
            url = loginProperties.get("DomainURL")+URLHelper.getShared().getUserInfoURL();

            //call Service
            ICidaasSDKService cidaassdkService = service.getInstance();
          cidaassdkService.getUserInfo(url,headers).enqueue(new Callback<UserinfoEntity>() {
              @Override
              public void onResponse(Call<UserinfoEntity> call, Response<UserinfoEntity> response) {
                  if (response.isSuccessful()) {

                      //todo save the accesstoken in Storage helper
                      if(response.code()==200) {
                          callback.success(response.body());
                      }
                      else {
                          callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.USER_INFO_SERVICE_FAILURE,
                                  "Service failure but successful response" , 400,null,null));
                      }
                  }
                  else {
                      assert response.errorBody() != null;
                      CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.USER_INFO_SERVICE_FAILURE,response);
                  }
              }

              @Override
              public void onFailure(Call<UserinfoEntity> call, Throwable t) {
                  Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                  callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null,null));

              }
          });
          }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(webAuthError);
        }
    }


}
