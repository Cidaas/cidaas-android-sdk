package de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.Push.PushAcknowledge;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Push.PushAcknowledge.PushAcknowledgeEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.Helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.Service.Push.PushAcknowledge.PushAcknowledgeService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


public class PushAcknowledgeController {
    //Local Variables
    private Context context;


    public static PushAcknowledgeController shared;

    public PushAcknowledgeController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static PushAcknowledgeController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushAcknowledgeController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushAcknowledgeController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------PushAcknowledge--------------------------------------------------------------
    public void pushAcknowledgeVerification(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult) {
        checkPushAcknowledgeEntity(pushAcknowledgeEntity, pushAcknowledgeResult);
    }


    //-------------------------------------checkPushAcknowledgeEntity-----------------------------------------------------------
    private void checkPushAcknowledgeEntity(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult) {
        String methodName = "PushAcknowledgeController:-checkPushAcknowledgeEntity()";
        try {
            if (pushAcknowledgeEntity.getVerificationType() != null && !pushAcknowledgeEntity.getVerificationType().equals("") &&
                    pushAcknowledgeEntity.getExchange_id() != null && !pushAcknowledgeEntity.getExchange_id().equals("")) {
                addProperties(pushAcknowledgeEntity, pushAcknowledgeResult);

            } else {
                pushAcknowledgeResult.failure(WebAuthError.getShared(context).propertyMissingException("Verification type or ExchangeId must not be null",
                        "Error:" + methodName));
                return;
            }

        } catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult) {
        String methodName = "PushAcknowledgeController:-addProperties()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    pushAcknowledgeEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    pushAcknowledgeEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    pushAcknowledgeEntity.setClient_id(clientId);

                    //call pushAcknowledge call
                    callPushAcknowledge(baseurl, pushAcknowledgeEntity, pushAcknowledgeResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    pushAcknowledgeResult.failure(error);
                }
            });

        } catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call pushAcknowledge Service-----------------------------------------------------------
    private void callPushAcknowledge(String baseurl, final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult) {
        String methodName = "PushAcknowledgeController:-pushAcknowledge()";
        try {

            String pushAcknowledgeUrl = VerificationURLHelper.getShared().getPushAcknowledgeURL(baseurl, pushAcknowledgeEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //PushAcknowledge Service call
            PushAcknowledgeService.getShared(context).callPushAcknowledgeService(pushAcknowledgeUrl, headers, pushAcknowledgeEntity, pushAcknowledgeResult);

        } catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }
}
