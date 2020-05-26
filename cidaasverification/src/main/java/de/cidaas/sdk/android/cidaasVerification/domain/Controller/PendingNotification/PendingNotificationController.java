package de.cidaas.sdk.android.cidaasVerification.domain.Controller.PendingNotification;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Settings.PendingNotification.PendingNotificationEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.Helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.Service.PendingNotification.PendingNotificationService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


public class PendingNotificationController {
    //Local Variables
    private Context context;


    public static PendingNotificationController shared;

    public PendingNotificationController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static PendingNotificationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PendingNotificationController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PendingNotificationController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------PendingNotification--------------------------------------------------------------
    public void getPendingNotification(String sub, final Result<PendingNotificationResponse> pendingNotificationResult) {
        String methodName = "PendingNotificationController:-getPendingNotification()";
        if (sub != null && !sub.equals("")) {
            addProperties(sub, pendingNotificationResult);
        } else {
            pendingNotificationResult.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null", methodName));
        }
    }

    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final String sub, final Result<PendingNotificationResponse> pendingNotificaitonResult) {
        String methodName = "PendingNotificationController:-addProperties()";
        try {
            //App properties
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    PendingNotificationEntity pendingNotificationEntity = new PendingNotificationEntity(deviceInfoEntity.getDeviceId(),
                            deviceInfoEntity.getPushNotificationId(), clientId, sub);

                    //call PendingNotification call
                    callPendingNotification(baseurl, pendingNotificationEntity, pendingNotificaitonResult);

                }

                @Override
                public void failure(WebAuthError error) {
                    pendingNotificaitonResult.failure(error);
                }
            });

        } catch (Exception e) {
            pendingNotificaitonResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call PendingNotification Service-----------------------------------------------------------
    private void callPendingNotification(String baseurl, final PendingNotificationEntity pendingNotificationEntity, final Result<PendingNotificationResponse> pendingNotificationResult) {
        String methodName = "PendingNotificationController:-callPendingNotification()";
        try {
            String configuredListURL = VerificationURLHelper.getShared().getPendingNotificationURL(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //PendingNotification Service call
            PendingNotificationService.getShared(context).callPendingNotificationService(configuredListURL, headers, pendingNotificationEntity, pendingNotificationResult);
        } catch (Exception e) {
            pendingNotificationResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
