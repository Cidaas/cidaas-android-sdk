package de.cidaas.sdk.android.cidaasnative.domain.Controller.Tenant;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Tenant.TenantService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
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
    public void getTenantInfo(@NonNull String baseurl, final EventResult<TenantInfoEntity> result) {
        String methodName = "TenantController :getTenantInfo()";
        try {

            if (CidaasHelper.baseurl != null && !CidaasHelper.baseurl.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
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