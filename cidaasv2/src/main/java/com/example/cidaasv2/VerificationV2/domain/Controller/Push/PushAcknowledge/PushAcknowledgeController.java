package com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushAcknowledge;

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
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.Service.Push.PushAcknowledge.PushAcknowledgeService;

import java.util.Dictionary;
import java.util.Map;

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
    public void pushAcknowledgeVerification(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        checkPushAcknowledgeEntity(pushAcknowledgeEntity,pushAcknowledgeResult);
    }


    //-------------------------------------checkPushAcknowledgeEntity-----------------------------------------------------------
    private void checkPushAcknowledgeEntity(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        String methodName = "PushAcknowledgeController:-checkPushAcknowledgeEntity()";
        try {
            if (pushAcknowledgeEntity.getVerificationType() != null && !pushAcknowledgeEntity.getVerificationType().equals(""))
            {
                if(pushAcknowledgeEntity.getClient_id() != null && !pushAcknowledgeEntity.getClient_id().equals("") &&
                        pushAcknowledgeEntity.getExchange_id() != null && !pushAcknowledgeEntity.getExchange_id().equals(""))
                {
                    // Todo Check For Face and Voice
                    addProperties(pushAcknowledgeEntity,pushAcknowledgeResult);
                }
                else
                {
                    pushAcknowledgeResult.failure(WebAuthError.getShared(context).propertyMissingException("ClientId or ExchangeId must not be null",
                            "Error:"+methodName));
                    return;
                }

            }
            else
            {
           pushAcknowledgeResult.failure(WebAuthError.getShared(context).propertyMissingException("Verification type must not be null", "Error:"+methodName));
                return;
            }

        }
        catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        String methodName = "PushAcknowledgeController:-addProperties()";
        try {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            pushAcknowledgeEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            pushAcknowledgeEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //call pushAcknowledge call
            callPushAcknowledge(pushAcknowledgeEntity,pushAcknowledgeResult);
        }
        catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call pushAcknowledge Service-----------------------------------------------------------
    private void callPushAcknowledge(final PushAcknowledgeEntity pushAcknowledgeEntity, final Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        String methodName = "PushAcknowledgeController:-pushAcknowledge()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String pushAcknowledgeUrl= VerificationURLHelper.getShared().getPushAcknowledgeURL(baseurl,pushAcknowledgeEntity.getVerificationType());

                    //headers Generation
                    Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                    //PushAcknowledge Service call
                  PushAcknowledgeService.getShared(context).callPushAcknowledgeService(pushAcknowledgeUrl,headers,pushAcknowledgeEntity,pushAcknowledgeResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    pushAcknowledgeResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            pushAcknowledgeResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }
}
