package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Tenant.TenantService;

import java.util.Dictionary;
import java.util.logging.Logger;

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
                String message="base url must not be empty";
                result.failure(WebAuthError.getShared(context).customException(417,message, HttpStatusCode.EXPECTATION_FAILED));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

}
