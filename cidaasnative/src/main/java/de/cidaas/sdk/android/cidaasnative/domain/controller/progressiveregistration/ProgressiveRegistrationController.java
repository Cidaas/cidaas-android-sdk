package de.cidaas.sdk.android.cidaasnative.domain.controller.progressiveregistration;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveRegistrationResponseDataEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveRegistrationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveUpdateRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveUpdateResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.ProgressiveReegistration.ProgressiveRegistrationService;
import de.cidaas.sdk.android.cidaasnative.util.NativeConstants;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.properties.CidaasProperties;
import timber.log.Timber;

public class ProgressiveRegistrationController {
    private Context context;

    public static ProgressiveRegistrationController shared;

    public ProgressiveRegistrationController(Context contextFromCidaas) {

        //Set Callback Null;
        //logincallback = null;
        context = contextFromCidaas;
        //Make Call back null
    }

    public static ProgressiveRegistrationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ProgressiveRegistrationController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void preLoginCheck(@NonNull String trackId, @NonNull EventResult<ProgressiveRegistrationResponseEntity> result){
        String methodName = "ProgressiveRegistrationController :preLoginCheck()";
        try{
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> preLoginresult) {
                    ProgressiveRegistrationService.getShared(context).preLoginCheck(preLoginresult.get(NativeConstants.DOMAIN_URL), trackId,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PRE_LOGIN_ERROR, e.getMessage()));
        }
    }

    public void progressiveUpdate(@NonNull String trackId,@NonNull String requestId, @NonNull ProgressiveUpdateRequestEntity progressiveUpdateRequest, @NonNull EventResult<ProgressiveUpdateResponseEntity> result){
        String methodName = "ProgressiveRegistrationController :progressiveUpdate()";
        try{
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> urlResult) {
                    ProgressiveRegistrationService.getShared(context).progressiveUpdate(urlResult.get(NativeConstants.DOMAIN_URL),trackId,requestId,progressiveUpdateRequest,result);
                }

                @Override
                public void failure(WebAuthError error) {
                 result.failure(error);
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PROGRESSIVE_UPDATE_USER, e.getMessage()));
        }
    }
}
