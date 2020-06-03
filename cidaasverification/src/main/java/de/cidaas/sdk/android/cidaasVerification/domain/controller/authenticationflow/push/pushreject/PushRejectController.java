package de.cidaas.sdk.android.cidaasVerification.domain.controller.authenticationflow.push.pushreject;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushreject.PushRejectEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushreject.PushRejectResponse;
import de.cidaas.sdk.android.cidaasVerification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.service.push.pushreject.PushRejectService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


public class PushRejectController {
    //Local Variables
    private Context context;


    public static PushRejectController shared;

    public PushRejectController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static PushRejectController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushRejectController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushRejectController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------PushReject--------------------------------------------------------------
    public void pushRejectVerification(final PushRejectEntity pushRejectEntity, final EventResult<PushRejectResponse> pushRejectResult) {
        checkPushRejectEntity(pushRejectEntity, pushRejectResult);
    }


    //-------------------------------------checkPushRejectEntity-----------------------------------------------------------
    private void checkPushRejectEntity(final PushRejectEntity pushRejectEntity, final EventResult<PushRejectResponse> pushRejectResult) {
        String methodName = "PushRejectController:-checkPushRejectEntity()";
        try {
            if (pushRejectEntity.getReason() != null && !pushRejectEntity.getReason().equals("") &&
                    pushRejectEntity.getVerificationType() != null && !pushRejectEntity.getVerificationType().equals("") &&
                    pushRejectEntity.getExchange_id() != null && !pushRejectEntity.getExchange_id().equals("")) {

                addProperties(pushRejectEntity, pushRejectResult);
            } else {
                pushRejectResult.failure(WebAuthError.getShared(context).propertyMissingException("Reason or Verification type or ExchangeId must not be null",
                        "Error:" + methodName));
                return;
            }

        } catch (Exception e) {
            pushRejectResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.PUSH_REJECT_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final PushRejectEntity pushRejectEntity, final EventResult<PushRejectResponse> pushRejectResult) {
        String methodName = "PushRejectController:-addProperties()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    pushRejectEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    pushRejectEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    pushRejectEntity.setClient_id(clientId);

                    //call pushReject call
                    callPushReject(baseurl, pushRejectEntity, pushRejectResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    pushRejectResult.failure(error);
                }
            });


        } catch (Exception e) {
            pushRejectResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_REJECT_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call pushReject Service-----------------------------------------------------------
    private void callPushReject(String baseurl, final PushRejectEntity pushRejectEntity, final EventResult<PushRejectResponse> pushRejectResult) {
        String methodName = "PushRejectController:-pushReject()";
        try {
            String pushRejectUrl = VerificationURLHelper.getShared().getPushDenyURL(baseurl, pushRejectEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //PushReject Service call
            PushRejectService.getShared(context).callPushRejectService(pushRejectUrl, headers, pushRejectEntity, pushRejectResult);
        } catch (Exception e) {
            pushRejectResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_REJECT_FAILURE, e.getMessage()));
        }
    }
}
