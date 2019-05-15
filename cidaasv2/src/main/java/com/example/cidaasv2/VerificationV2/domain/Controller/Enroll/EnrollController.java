package com.example.cidaasv2.VerificationV2.domain.Controller.Enroll;

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
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.BiometricHandler.BiometricHandler;
import com.example.cidaasv2.VerificationV2.domain.Service.Enroll.EnrollService;

import java.util.Dictionary;
import java.util.Map;

public class EnrollController {
    //Local Variables
    private Context context;


    public static EnrollController shared;

    public EnrollController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static EnrollController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new EnrollController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("EnrollController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Enroll--------------------------------------------------------------
    public void enrollVerification(final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResult)
    {
        checkEnrollEntity(enrollEntity,enrollResult);
    }


    //-------------------------------------checkEnrollEntity-----------------------------------------------------------
    private void checkEnrollEntity(final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResult)
    {
        String methodName = "EnrollController:-checkEnrollEntity()";
        try {
                if(enrollEntity.getVerificationType() != null &&  !enrollEntity.getVerificationType().equals("")&&
                        enrollEntity.getClient_id() != null && !enrollEntity.getClient_id().equals("") &&
                        enrollEntity.getExchange_id() != null && !enrollEntity.getExchange_id().equals(""))
                {
                    handleVerificationTypes(enrollEntity,enrollResult);
                }
                else
                {
               enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Verification type or ClientId or ExchangeId must not be empty",
                            "Error:"+methodName));
                    return;
                }
        }
        catch (Exception e) {
        enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                e.getMessage()));
        }
    }

    //-----------------------------------------------handleVerificationTypes---------------------------------------------------------------
    private void handleVerificationTypes(EnrollEntity enrollEntity,Result<EnrollResponse> enrollResult)
    {
        String methodName = "EnrollController:-handleVerificationTypes()";
        try {
            if (enrollEntity.getPass_code() != null && !enrollEntity.getPass_code().equals("")) {

                addProperties(enrollEntity, enrollResult);
            }
            else {
                if (enrollEntity.getVerificationType().equalsIgnoreCase(AuthenticationType.TOUCHID)) {
                    //FingerPrint
                    callFingerPrintAuthentication(enrollEntity, enrollResult);
                }
                else {
                    enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Passcode must not be empty", "Error:" + methodName));
                    return;
                }
            }

        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void callFingerPrintAuthentication(final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResult)
    {
        String methodName = "EnrollController:-callFingerPrintAuthentication()";
        try {
            BiometricHandler.getShared(context).callFingerPrint(enrollEntity.getFingerPrintEntity(), methodName, new Result<String>() {
                @Override
                public void success(String result) {
                    //call enroll call
                    addProperties(enrollEntity,enrollResult);
                }

                @Override
                public void failure(WebAuthError error) {
                  enrollResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResult)
    {
        String methodName = "EnrollController:-addProperties()";
        try {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            enrollEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            enrollEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //call enroll call
            callEnroll(enrollEntity,enrollResult);
        }
        catch (Exception e) {
        enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                e.getMessage()));
        }
    }

    //-------------------------------------------Call enroll Service-----------------------------------------------------------
    private void callEnroll(final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResult)
    {
        String methodName = "EnrollController:-enroll()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String enrollUrl= VerificationURLHelper.getShared().getEnrollURL(baseurl,enrollEntity.getVerificationType());

                    //headers Generation
                    Map<String,String> headers=Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

                    //Enroll Service call
                    EnrollService.getShared(context).callEnrollService(enrollUrl,headers,enrollEntity,enrollResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    enrollResult.failure(error);
                }
            });
        }
        catch (Exception e) {
       enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
               e.getMessage()));
        }
    }
}
