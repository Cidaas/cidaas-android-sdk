package com.example.cidaasv2.VerificationV2.domain.Controller.Scanned;

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
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.domain.Service.Scanned.ScannedService;

import java.util.Dictionary;
import java.util.Map;

import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;

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
    public void scannedVerification(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult)
    {
        checkScannedEntity(scannedEntity,scannedResult);
    }


    //-------------------------------------checkScannedEntity-----------------------------------------------------------
    private void checkScannedEntity(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult)
    {
        String methodName = "ScannedController:-checkScannedEntity()";
        try {
            if (scannedEntity.getVerificationType() != null && !scannedEntity.getVerificationType().equals("") && scannedEntity.getSub() != null &&
                    !scannedEntity.getSub().equals("")) {
                scannedResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:"+methodName));
                return;
            }
            if(scannedEntity.getClient_id() != null && !scannedEntity.getClient_id().equals("") &&
                    scannedEntity.getExchange_id() != null && !scannedEntity.getExchange_id().equals(""))
            {
                scannedResult.failure(WebAuthError.getShared(context).propertyMissingException("ClientId or ExchangeId must not be null",
                        "Error:"+methodName));
                return;
            }

            addProperties(scannedEntity,scannedResult);

        }
        catch (Exception e) {
       scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult)
    {
        String methodName = "ScannedController:-addProperties()";
        try {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            scannedEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            scannedEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //call scanned call
            callScanned(scannedEntity,scannedResult);
        }
        catch (Exception e) {
      scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call scanned Service-----------------------------------------------------------
    private void callScanned(final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult)
    {
        String methodName = "ScannedController:-scanned()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    String scannedUrl= URLHelper.getShared().getScannedURL(baseurl,scannedEntity.getVerificationType());

                    //headers Generation
                    Map<String,String> headers=Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

                    //Scanned Service call
                    ScannedService.getShared(context).callScannedService(scannedUrl,headers,scannedEntity,scannedResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    scannedResult.failure(error);
                }
            });
        }
        catch (Exception e) {
      scannedResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }


}
