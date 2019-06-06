package com.example.cidaasv2.VerificationV2.domain.Controller.Setup;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.Service.Setup.SetupService;

import java.util.Dictionary;
import java.util.Map;

public class SetupController {

    //Local Variables
    private Context context;


    public static SetupController shared;

    public SetupController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static SetupController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SetupController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SetupController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    public void setupVerification(final SetupEntity setupEntity, final Result<SetupResponse> setupResult)
    {
        checkSetupEntity(setupEntity,setupResult);
    }


    //------------------------------------------------------checkSetupEntity-------------------------------------------------------
    private void checkSetupEntity(final SetupEntity setupEntity, final Result<SetupResponse> setupResult)
    {
        String methodName = "SetupController:-checkSetupEntity()";
        try
        {
            if (setupEntity.getSub() != null && !setupEntity.getSub().equals("") && setupEntity.getVerificationType() != null &&
                    !setupEntity.getVerificationType().equals("")) {
                addProperties(setupEntity, setupResult);
            }
            else
            {
                setupResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:"+methodName));
                return;
            }

        }
        catch (Exception e) {
         setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                 e.getMessage()));
        }
    }


    //Add Device info and pushnotificationId
    private void addProperties(final SetupEntity setupEntity, final Result<SetupResponse> setupResult)
    {
        String methodName = "SetupController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    setupEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    setupEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    setupEntity.setClient_id(clientId);

                    //call setup call
                    callSetup(baseurl,setupEntity,setupResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    setupResult.failure(error);
                }
            });


        }
        catch (Exception e)
        {
          setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                  e.getMessage()));
        }
    }

    //Call callSetup Service
    private void callSetup(final String accessToken, final SetupEntity setupEntity, final Result<SetupResponse> setupResult)
    {
        String methodName = "SetupController:-callSetup()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                    //get url
                    String setupUrl= VerificationURLHelper.getShared().getSetupURL(baseurl,setupEntity.getVerificationType());

                    //headers Generation
                    Map<String,String> headers= Headers.getShared(context).getHeaders(accessToken,false,URLHelper.contentTypeJson);

                    //Setup Service call
                    SetupService.getShared(context).callSetupService(setupUrl,headers,setupEntity,setupResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    setupResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
         setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                 e.getMessage()));
        }
    }

}
