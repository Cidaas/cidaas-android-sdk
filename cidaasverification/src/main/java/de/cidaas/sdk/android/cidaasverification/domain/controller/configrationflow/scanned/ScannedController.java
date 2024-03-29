package de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.scanned;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.SetUpCancelEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.SetUpCancelResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasverification.domain.service.scanned.ScannedService;
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
    public void scannedVerification(final ScannedEntity scannedEntity, final EventResult<ScannedResponse> scannedResult) {
        checkScannedEntity(scannedEntity, scannedResult);
    }


    //-------------------------------------checkScannedEntity-----------------------------------------------------------
    private void checkScannedEntity(final ScannedEntity scannedEntity, final EventResult<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-checkScannedEntity()";
        try {
            if (scannedEntity.getVerificationType() != null && !scannedEntity.getVerificationType().equals("") &&
                   // scannedEntity.getSub() != null && !scannedEntity.getSub().equals("") &&
                    scannedEntity.getExchange_id() != null && !scannedEntity.getExchange_id().equals("")) {

                addProperties(scannedEntity, scannedResult);
            } else {
                scannedResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub or ExchangeId must not be null",
                        VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }
        } catch (Exception e) {
            scannedResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final ScannedEntity scannedEntity, final EventResult<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
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
            scannedResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call scanned Service-----------------------------------------------------------
    private void callScanned(String baseurl, final ScannedEntity scannedEntity, final EventResult<ScannedResponse> scannedResult) {
        String methodName = "ScannedController:-scanned()";
        try {
            String scannedUrl = VerificationURLHelper.getShared().getScannedURL(baseurl, scannedEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Scanned Service call
            ScannedService.getShared(context).callScannedService(scannedUrl, headers, scannedEntity, scannedResult);
        } catch (Exception e) {
            scannedResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    public void setUpCancel(SetUpCancelEntity setUpCancelEntity, EventResult<SetUpCancelResponse> setUpCancelResponseResult) {

        String methodName = "ScannedController:-checkScannedEntity()";
        try {

            addPropertiesSetUpCancel(setUpCancelEntity, setUpCancelResponseResult);

        } catch (Exception e) {
            setUpCancelResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void addPropertiesSetUpCancel(SetUpCancelEntity setUpCancelEntity, EventResult<SetUpCancelResponse> setUpCancelResponseResult) {
        String methodName = "ScannedController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");



                    //call scanned call
                    callScannedSetUpCancel(baseurl, setUpCancelEntity, setUpCancelResponseResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    setUpCancelResponseResult.failure(error);
                }
            });

        } catch (Exception e) {
            setUpCancelResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void callScannedSetUpCancel(String baseurl, SetUpCancelEntity setUpCancelEntity, EventResult<SetUpCancelResponse> setUpCancelResponseResult) {

        String methodName = "ScannedController:-scanned()";
        try {
            String scannedUrl = VerificationURLHelper.getShared().getSetUpCancelURL(baseurl, setUpCancelEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Scanned Service call
            ScannedService.getShared(context).callSetUpCancelService(scannedUrl, headers, setUpCancelEntity, setUpCancelResponseResult);
        } catch (Exception e) {
            setUpCancelResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


}
