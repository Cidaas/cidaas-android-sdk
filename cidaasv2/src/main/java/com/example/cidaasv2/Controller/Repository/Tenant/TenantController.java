package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Repository.Tenant.TenantService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class TenantController {



    private Context context;

    public static TenantController shared;

    public TenantController(Context contextFromCidaas) {

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

    //Service call To get Tenant Info
    public void getTenantInfo(@NonNull String baseurl, final Result<TenantInfoEntity> result){
        try{

            if(Cidaas.baseurl!=null && !Cidaas.baseurl.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> stringresult) {
                        TenantService.getShared(context).getTenantInfo(stringresult.get("DomainURL"),result);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL Must not be empty"));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.TENANT_INFO_FAILURE));
            LogFile.getShared(context).addRecordToLog("Exception getTenantInfo():"+e.getMessage());
        }
    }

}
