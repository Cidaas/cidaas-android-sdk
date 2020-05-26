package de.cidaas.sdk.android.cidaasVerification.domain.Controller.ConfigrationFlow.Scanned;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.DBHelper;
import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;
import de.cidaas.sdk.android.cidaas.Helper.URLHelper.URLHelper;
import de.cidaas.sdk.android.cidaas.Service.HelperForService.Headers.Headers;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.Helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.Service.Scanned.ScannedService;

import java.util.Dictionary;
import java.util.Map;

public class ScannedController {

    //Local Variables
    private Context context;


    public static ScannedController shared;

    public ScannedController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static ScannedController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ScannedController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ScannedController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------Scanned--------------------------------------------------------------
    public void scannedVerification(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult) {
        checkScannedEntity(scannedEntity, scannedResult);
    }


    //-------------------------------------checkScannedEntity-----------------------------------------------------------
    private void checkScannedEntity(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-checkScannedEntity()";
        try {
            if (scannedEntity.getVerificationType() != null && !scannedEntity.getVerificationType().equals("") && scannedEntity.getSub() != null &&
                    !scannedEntity.getSub().equals("") && scannedEntity.getExchange_id() != null && !scannedEntity.getExchange_id().equals("")) {

                addProperties(scannedEntity, scannedResult);
            } else {
                scannedResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub or ExchangeId must not be null",
                        "Error:" + methodName));
                return;
            }
        } catch (Exception e) {
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");


                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    scannedEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    scannedEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    scannedEntity.setClient_id(clientId);

                    //call scanned call
                    callScanned(baseurl, scannedEntity, scannedResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    scannedResult.failure(error);
                }
            });

        } catch (Exception e) {
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call scanned Service-----------------------------------------------------------
    private void callScanned(String baseurl, final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-scanned()";
        try {
            String scannedUrl = VerificationURLHelper.getShared().getScannedURL(baseurl, scannedEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Scanned Service call
            ScannedService.getShared(context).callScannedService(scannedUrl, headers, scannedEntity, scannedResult);
        } catch (Exception e) {
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


}
