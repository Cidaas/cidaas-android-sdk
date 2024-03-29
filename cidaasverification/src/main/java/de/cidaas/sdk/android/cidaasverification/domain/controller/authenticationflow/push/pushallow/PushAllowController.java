package de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.push.pushallow;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasverification.domain.service.push.pushallow.PushAllowService;
import de.cidaas.sdk.android.cidaasverification.util.VerificationConstants;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


public class PushAllowController {
    //Local Variables
    private Context context;


    public static PushAllowController shared;

    public PushAllowController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static PushAllowController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushAllowController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushAllowController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------PushAllow--------------------------------------------------------------
    public void pushAllowVerification(final PushAllowEntity pushAllowEntity, final EventResult<PushAllowResponse> pushAllowResult) {
        checkPushAllowEntity(pushAllowEntity, pushAllowResult);
    }


    //-------------------------------------checkPushAllowEntity-----------------------------------------------------------
    private void checkPushAllowEntity(final PushAllowEntity pushAllowEntity, final EventResult<PushAllowResponse> pushAllowResult) {
        String methodName = "PushAllowController:-checkPushAllowEntity()";
        try {
            if (pushAllowEntity.getVerificationType() != null && !pushAllowEntity.getVerificationType().equals("") &&
                    pushAllowEntity.getExchange_id() != null && !pushAllowEntity.getExchange_id().equals("")) {
                addProperties(pushAllowEntity, pushAllowResult);
            } else {
                pushAllowResult.failure(WebAuthError.getShared(context).propertyMissingException("Verification type or ExchangeId must not be null",
                        VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }

        } catch (Exception e) {
            pushAllowResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.PUSH_ALLOW_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final PushAllowEntity pushAllowEntity, final EventResult<PushAllowResponse> pushAllowResult) {
        String methodName = "PushAllowController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get(VerificationConstants.DOMAIN_URL);
                    String clientId = loginPropertiesResult.get(VerificationConstants.CLIENT_ID);

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    pushAllowEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    pushAllowEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    pushAllowEntity.setClient_id(clientId);

                    //call pushAllow call
                    callPushAllow(baseurl, pushAllowEntity, pushAllowResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    pushAllowResult.failure(error);
                }
            });

        } catch (Exception e) {
            pushAllowResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.PUSH_ALLOW_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call pushAllow Service-----------------------------------------------------------
    private void callPushAllow(String baseurl, final PushAllowEntity pushAllowEntity, final EventResult<PushAllowResponse> pushAllowResult) {
        String methodName = "PushAllowController:-pushAllow()";
        try {
            String pushAllowUrl = VerificationURLHelper.getShared().getPushAllowURL(baseurl, pushAllowEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //PushAllow Service call
            PushAllowService.getShared(context).callPushAllowService(pushAllowUrl, headers, pushAllowEntity, pushAllowResult);
        } catch (Exception e) {
            pushAllowResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.PUSH_ALLOW_FAILURE, e.getMessage()));
        }
    }
}
