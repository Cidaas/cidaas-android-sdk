package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Tenant.TenantService;

import java.util.Dictionary;

import timber.log.Timber;

public class TenantController {


    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static TenantController shared;

    public TenantController(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        authenticationType="";
        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static TenantController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new TenantController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }
    /*//Get Tenant Info
    public void getTenantInfo(final Result<TenantInfoEntity> result) {
        try {

            //Todo Check notnull in db
            String baseurl = "";
            if(savedProperties==null){

                savedProperties= DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "TenantInfo readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");
                getTenantInfoService(baseurl,result);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/
    //Service call To get Tenant Info
    public void getTenantInfo(@NonNull String baseurl, final Result<TenantInfoEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("")) {
                // Change service call to private
                TenantService.getShared(context).getTenantInfo(baseurl, new Result<TenantInfoEntity>() {

                    @Override
                    public void success(TenantInfoEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

}
