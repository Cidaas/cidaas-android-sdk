package de.cidaas.sdk.android.cidaasnative.domain.service.Tenant;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.R;
import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.data.service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.ICidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.helper.NativeURLHelper;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TenantService {
    CidaasNativeService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables

    private Context context;

    public static TenantService shared;

    public TenantService(Context contextFromCidaas) {

        context = contextFromCidaas;


        if (service == null) {
            service = new CidaasNativeService(context);
        }


    }


    public static TenantService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new TenantService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i("Exception" + e.getMessage());
        }
        return shared;
    }


    //Get Tenant info
    public void getTenantInfo(String baseurl, final EventResult<TenantInfoEntity> callback) {
        //Local Variables
        String methodName = "TenantService :getTenantInfo()";
        try {

            if (baseurl != null && !baseurl.equals("")) {

                //Construct URL For RequestId
                String TenantUrl = baseurl + NativeURLHelper.getShared().getTenantUrl();

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

                //Service Call For get tenantInfo
                serviceForGetTenantInfo(TenantUrl, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.TENANT_INFO_FAILURE, e.getMessage()));
        }
    }

    public void serviceForGetTenantInfo(String tenantUrl, Map<String, String> headers, final EventResult<TenantInfoEntity> callback) {
        final String methodName = "TenantService :getTenantInfo()";
        try {

            //Call Service-getRequestId
            ICidaasNativeService CidaasNativeService = service.getInstance();
            CidaasNativeService.getTenantInfo(tenantUrl, headers).enqueue(new Callback<TenantInfoEntity>() {
                @Override
                public void onResponse(Call<TenantInfoEntity> call, Response<TenantInfoEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.TENANT_INFO_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.TENANT_INFO_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<TenantInfoEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.TENANT_INFO_FAILURE, t.getMessage()
                            , "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.TENANT_INFO_FAILURE, e.getMessage()));
        }
    }

}
