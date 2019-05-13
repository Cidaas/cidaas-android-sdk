package com.example.cidaasv2.VerificationV2.domain.Controller.Setup;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
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
                setupResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:"+methodName));
                return;
            }
            else
            {
                addProperties(setupEntity, setupResult);
            }

        }
        catch (Exception e) {
         setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }


    //Add Device info and pushnotificationId
    private void addProperties(final SetupEntity setupEntity, final Result<SetupResponse> setupResult)
    {
        String methodName = "SetupController:-addProperties()";
        try {

            //get AccessToken
            AccessTokenController.getShared(context).getAccessToken(setupEntity.getSub(), new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity accessTokenresult) {
                    //call callSetup call
                    callSetup(accessTokenresult.getAccess_token(),setupEntity,setupResult);
                }

                @Override
                public void failure(WebAuthError error) { setupResult.failure(error);
                }
            });

        }
        catch (Exception e)
        {
          setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
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
                    String setupUrl= URLHelper.getShared().getSetupURL(baseurl,setupEntity.getVerificationType());

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
         setupResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.SCANNED_FAILURE, e.getMessage()));
        }
    }

}
