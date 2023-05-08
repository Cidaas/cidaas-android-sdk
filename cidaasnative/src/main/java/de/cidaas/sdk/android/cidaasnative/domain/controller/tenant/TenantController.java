package de.cidaas.sdk.android.cidaasnative.domain.controller.tenant;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.Tenant.TenantService;
import de.cidaas.sdk.android.cidaasnative.util.NativeConstants;
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
                        TenantService.getShared(context).getTenantInfo(stringresult.get(NativeConstants.DOMAIN_URL), result);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL Must not be empty", NativeConstants.ERROR_LOGGING_PREFIX + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.TENANT_INFO_FAILURE, e.getMessage()));
        }
    }

}
