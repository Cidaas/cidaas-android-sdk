package de.cidaas.sdk.android.cidaasnative.domain.Controller.Tenant;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.CidaasHelper;
import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Tenant.TenantService;

import java.util.Dictionary;

import androidx.annotation.NonNull;

import timber.log.Timber;

public class TenantController {

    private Context context;

    public static TenantController shared;

    public TenantController(Context contextFromCidaas) {

        context = contextFromCidaas;

    }

    public static TenantController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new TenantController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Service call To get Tenant Info
    public void getTenantInfo(@NonNull String baseurl, final Result<TenantInfoEntity> result) {
        String methodName = "TenantController :getTenantInfo()";
        try {

            if (CidaasHelper.baseurl != null && !CidaasHelper.baseurl.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> stringresult) {
                        TenantService.getShared(context).getTenantInfo(stringresult.get("DomainURL"), result);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL Must not be empty", "Error:" + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.TENANT_INFO_FAILURE, e.getMessage()));
        }
    }

}
