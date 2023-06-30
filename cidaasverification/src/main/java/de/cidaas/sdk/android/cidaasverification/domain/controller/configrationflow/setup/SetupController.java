package de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.setup;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasverification.domain.service.setup.SetupService;
import de.cidaas.sdk.android.cidaasverification.util.VerificationConstants;
import de.cidaas.sdk.android.controller.AccessTokenController;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


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

    public void setupVerification(final SetupEntity setupEntity, final EventResult<SetupResponse> setupResult) {
        checkSetupEntity(setupEntity, setupResult);
    }


    //------------------------------------------------------checkSetupEntity-------------------------------------------------------
    private void checkSetupEntity(final SetupEntity setupEntity, final EventResult<SetupResponse> setupResult) {
        String methodName = "SetupController:-checkSetupEntity()";
        try {
            if (setupEntity.getSub() != null && !setupEntity.getSub().equals("") && setupEntity.getVerificationType() != null &&
                    !setupEntity.getVerificationType().equals("")) {
                addProperties(setupEntity, setupResult);
            } else {
                setupResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }

        } catch (Exception e) {
            setupResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //Add Device info and pushnotificationId
    private void addProperties(final SetupEntity setupEntity, final EventResult<SetupResponse> setupResult) {
        String methodName = "SetupController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //get AccessToken
                    AccessTokenController.getShared(context).getAccessToken(setupEntity.getSub(), new EventResult<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accessTokenresult) {
                            //call callSetup call

                            //App properties
                            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                            setupEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                            setupEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                            setupEntity.setClient_id(clientId);


                            callSetup(baseurl, accessTokenresult.getAccess_token(), setupEntity, setupResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            setupResult.failure(error);
                        }
                    });

                }

                @Override
                public void failure(WebAuthError error) {
                    setupResult.failure(error);
                }
            });


        } catch (Exception e) {
            setupResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //Call callSetup Service
    private void callSetup(final String baseurl, String accessToken, final SetupEntity setupEntity, final EventResult<SetupResponse> setupResult) {
        String methodName = "SetupController:-callSetup()";
        try {

            //get url
            String setupUrl = VerificationURLHelper.getShared().getSetupURL(baseurl, setupEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken, false, URLHelper.contentTypeJson);

            //Setup Service call
            SetupService.getShared(context).callSetupService(setupUrl, headers, setupEntity, setupResult);

        } catch (Exception e) {
            setupResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

}
