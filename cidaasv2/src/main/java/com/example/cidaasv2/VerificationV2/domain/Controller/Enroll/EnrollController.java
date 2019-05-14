package com.example.cidaasv2.VerificationV2.domain.Controller.Enroll;

import android.content.Context;

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
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponseEntity;
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
    public void enrollVerification(final EnrollEntity enrollEntity, final Result<EnrollResponseEntity> enrollResult)
    {
        checkEnrollEntity(enrollEntity,enrollResult);
    }


    //-------------------------------------checkEnrollEntity-----------------------------------------------------------
    private void checkEnrollEntity(final EnrollEntity enrollEntity, final Result<EnrollResponseEntity> enrollResult)
    {
        String methodName = "EnrollController:-checkEnrollEntity()";
        try {
            if (enrollEntity.getPass_code() != null && !enrollEntity.getPass_code().equals("") && enrollEntity.getVerificationType() != null &&
                    !enrollEntity.getVerificationType().equals("")) {
                if(enrollEntity.getClient_id() != null && !enrollEntity.getClient_id().equals("") &&
                        enrollEntity.getExchange_id() != null && !enrollEntity.getExchange_id().equals(""))
                {
                    addProperties(enrollEntity,enrollResult);
                }
                else
                {
                    enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("ClientId or ExchangeId must not be null",
                            "Error:"+methodName));
                    return;
                }

            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Pass_code or Verification type must not be null",
                        "Error:"+methodName));
                return;
            }

        }
        catch (Exception e) {
        enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final EnrollEntity enrollEntity, final Result<EnrollResponseEntity> enrollResult)
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
        enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call enroll Service-----------------------------------------------------------
    private void callEnroll(final EnrollEntity enrollEntity, final Result<EnrollResponseEntity> enrollResult)
    {
        String methodName = "EnrollController:-enroll()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String enrollUrl= URLHelper.getShared().getEnrollURL(baseurl,enrollEntity.getVerificationType());

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
       enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }
}
