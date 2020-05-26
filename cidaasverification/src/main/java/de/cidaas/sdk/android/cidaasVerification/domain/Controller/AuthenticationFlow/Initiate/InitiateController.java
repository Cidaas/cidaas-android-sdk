package de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.Initiate;

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

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate.InitiateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate.InitiateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.Helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.Service.Initiate.InitiateService;

public class InitiateController {
    //Local Variables
    private Context context;


    public static InitiateController shared;

    public InitiateController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static InitiateController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new InitiateController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("InitiateController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Initiate--------------------------------------------------------------
    public void initiateVerification(final InitiateEntity initiateEntity, final Result<InitiateResponse> initiateResult) {
        checkInitiateEntity(initiateEntity, initiateResult);
    }


    //-------------------------------------checkInitiateEntity-----------------------------------------------------------
    private void checkInitiateEntity(final InitiateEntity initiateEntity, final Result<InitiateResponse> initiateResult) {
        String methodName = "InitiateController:-checkInitiateEntity()";
        try {
            if (initiateEntity.getVerificationType() != null && !initiateEntity.getVerificationType().equals("")) {
                if (initiateEntity.getRequest_id() != null && !initiateEntity.getRequest_id().equals("") &&
                        initiateEntity.getSub() != null && !initiateEntity.getSub().equals("") &&
                        initiateEntity.getUsage_type() != null && !initiateEntity.getUsage_type().equals("")) {
                    addProperties(initiateEntity, initiateResult);
                } else {
                    initiateResult.failure(WebAuthError.getShared(context).propertyMissingException("requestId or sub or UsageType must not be null",
                            "Error:" + methodName));
                    return;
                }

            } else {
                initiateResult.failure(WebAuthError.getShared(context).propertyMissingException("MediumId or Verification type must not be null",
                        "Error:" + methodName));
                return;
            }

        } catch (Exception e) {
            initiateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final InitiateEntity initiateEntity, final Result<InitiateResponse> initiateResult) {
        String methodName = "InitiateController:-addProperties()";
        try {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            initiateEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            initiateEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //call initiate call
            callInitiate(initiateEntity, initiateResult);
        } catch (Exception e) {
            initiateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call initiate Service-----------------------------------------------------------
    private void callInitiate(final InitiateEntity initiateEntity, final Result<InitiateResponse> initiateResult) {
        String methodName = "InitiateController:-initiate()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String initiateUrl = VerificationURLHelper.getShared().getInitiateURL(baseurl, initiateEntity.getVerificationType());

                    //headers Generation
                    Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

                    //Initiate Service call
                    InitiateService.getShared(context).callInitiateService(initiateUrl, headers, initiateEntity, initiateResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    initiateResult.failure(error);
                }
            });
        } catch (Exception e) {
            initiateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
