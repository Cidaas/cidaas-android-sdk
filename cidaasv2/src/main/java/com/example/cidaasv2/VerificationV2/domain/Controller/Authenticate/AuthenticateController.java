package com.example.cidaasv2.VerificationV2.domain.Controller.Authenticate;

import android.content.Context;

import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.BiometricHandler.BiometricHandler;
import com.example.cidaasv2.VerificationV2.domain.Service.Authenticate.AuthenticateService;

import java.util.Dictionary;
import java.util.Map;

public class AuthenticateController {
    //Local Variables
    private Context context;


    public static AuthenticateController shared;

    public AuthenticateController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static AuthenticateController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AuthenticateController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("AuthenticateController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Authenticate--------------------------------------------------------------
    public void authenticateVerification(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        checkAuthenticateEntity(authenticateEntity,authenticateResult);
    }


    //-------------------------------------checkAuthenticateEntity-----------------------------------------------------------
    private void checkAuthenticateEntity(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-checkAuthenticateEntity()";
        try {

                if( authenticateEntity.getVerificationType() != null && !authenticateEntity.getVerificationType().equals("")&&
                        authenticateEntity.getClient_id() != null && !authenticateEntity.getClient_id().equals("") &&
                        authenticateEntity.getExchange_id() != null && !authenticateEntity.getExchange_id().equals(""))
                {
                    // Todo Check For Face and Voice
                    handleVerificationTypes(authenticateEntity,authenticateResult);
                }
                else
                {
                    authenticateResult.failure(WebAuthError.getShared(context).propertyMissingException(
                            "ClientId or ExchangeId or Verification Type must not be null", "Error:"+methodName));
                    return;
                }

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //-----------------------------------------------handleVerificationTypes---------------------------------------------------------------
    private void handleVerificationTypes(AuthenticateEntity authenticateEntity, Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-handleVerificationTypes()";
        try {
            if (authenticateEntity.getPass_code() != null && !authenticateEntity.getPass_code().equals("")) {

                addProperties(authenticateEntity, authenticateResult);
            }
            else {
                if (authenticateEntity.getVerificationType().equalsIgnoreCase(AuthenticationType.TOUCHID)) {
                    //FingerPrint
                    callFingerPrintAuthentication(authenticateEntity, authenticateResult);
                }
                else {
                    authenticateResult.failure(WebAuthError.getShared(context).propertyMissingException("Passcode must not be empty", "Error:" + methodName));
                    return;
                }
            }

        }
        catch (Exception e)
        {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void callFingerPrintAuthentication(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-callFingerPrintAuthentication()";
        try {
            BiometricHandler.getShared(context).callFingerPrint(authenticateEntity.getFingerPrintEntity(), methodName, new Result<String>() {
                @Override
                public void success(String result) {
                    //call authenticate call
                    authenticateEntity.setPass_code(DBHelper.getShared().getDeviceInfo().getDeviceId());
                    addProperties(authenticateEntity,authenticateResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    authenticateResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }



    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-addProperties()";
        try {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            authenticateEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            authenticateEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //call authenticate call
            callAuthenticate(authenticateEntity,authenticateResult);
        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call authenticate Service-----------------------------------------------------------
    private void callAuthenticate(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-authenticate()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String authenticateUrl= VerificationURLHelper.getShared().getAuthenticateURL(baseurl,authenticateEntity.getVerificationType());

                    //headers Generation
                    Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                    //Authenticate Service call
                    AuthenticateService.getShared(context).callAuthenticateService(authenticateUrl,headers,authenticateEntity,authenticateResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    authenticateResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
