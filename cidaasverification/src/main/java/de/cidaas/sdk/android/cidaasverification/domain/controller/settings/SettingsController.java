package de.cidaas.sdk.android.cidaasverification.domain.controller.settings;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.settings.configuredmfalist.ConfiguredMFAList;
import de.cidaas.sdk.android.cidaasverification.data.entity.settings.configuredmfalist.GetMFAListEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.updatefcmtoken.UpdateFCMTokenEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.updatefcmtoken.UpdateFCMTokenResponseEntity;
import de.cidaas.sdk.android.cidaasverification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasverification.domain.service.settings.SettingsService;
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


public class SettingsController {
    //Local Variables
    private Context context;


    public static SettingsController shared;

    public SettingsController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static SettingsController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SettingsController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SettingsController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------Settings--------------------------------------------------------------
    public void getConfiguredMFAList(String sub, final EventResult<ConfiguredMFAList> settingsResult) {
        checkConfiguredMFAList(sub, settingsResult);
    }


    //-------------------------------------checkEntity-----------------------------------------------------------
    private void checkConfiguredMFAList(String sub, final EventResult<ConfiguredMFAList> settingsResult) {
        String methodName = "SettingsController:-checkConfiguredMFAList()";
        try {
            if (sub != null && !sub.equals("")) {

                addProperties(sub, settingsResult);
            } else {
                settingsResult.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null", VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }
        } catch (Exception e) {
            settingsResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.MFA_LIST_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final String sub, final EventResult<ConfiguredMFAList> configuredMFAListResult) {
        String methodName = "SettingsController:-addProperties()";
        try {
            //App properties
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    GetMFAListEntity getMFAListEntity = new GetMFAListEntity(deviceInfoEntity.getDeviceId(), deviceInfoEntity.getPushNotificationId(), clientId, sub,"");

                    //call settings call
                    callSettings(baseurl, getMFAListEntity, configuredMFAListResult);

                }

                @Override
                public void failure(WebAuthError error) {
                    configuredMFAListResult.failure(error);
                }
            });

        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call Configured Verification Service-----------------------------------------------------------
    private void callSettings(String baseurl, final GetMFAListEntity getMFAListEntity, final EventResult<ConfiguredMFAList> configuredMFAListResult) {
        String methodName = "SettingsController:-callSettings()";
        try {
            String configuredListURL = VerificationURLHelper.getShared().getConfiguredListURL(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Settings Service call
            SettingsService.getShared(context).getConfigurationList(configuredListURL, headers, getMFAListEntity, configuredMFAListResult);
        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException(VerificationConstants.EXCEPTION_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //--------------------------------------------Settings----------------------------------------------------------------------------------------------------------------
    public void updateFCMToken(final String newFCMToken) {
        final String methodName = "SettingsController:-updateFCMToken()";
        DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

        if (newFCMToken != null && !newFCMToken.equals("")) {
            //Check for DB if it is null or empty save it in DB
            if (deviceInfoEntity.getPushNotificationId() == null || deviceInfoEntity.getPushNotificationId().equals("")) {
                DBHelper.getShared().setFCMToken(newFCMToken);
            } else if (deviceInfoEntity.getPushNotificationId().equals(newFCMToken)) {
                //No problem  New and Old FCM id is same
            } else {
                addPropertiesForFCM(newFCMToken);
            }
        } else {
            WebAuthError.getShared(context).propertyMissingException("FCMToken must not be null", methodName);
        }
    }

    public void addPropertiesForFCM(final String newFCMToken) {
        final String methodName = "SettingsController:-addPropertiesForFCM()";
        if (newFCMToken != null && !newFCMToken.equals("")) {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    UpdateFCMTokenEntity updateFCMTokenEntity = new UpdateFCMTokenEntity(deviceInfoEntity.getDeviceId(), newFCMToken,
                            clientId, deviceInfoEntity.getPushNotificationId());

                    callupdateFCMToken(baseurl, updateFCMTokenEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                    WebAuthError.getShared(context).cidaasPropertyMissingException("", methodName);
                }
            });

        } else {
            WebAuthError.getShared(context).propertyMissingException("FCMToken must not be null", methodName);
        }
    }

    //-------------------------------------------Call settings Service-----------------------------------------------------------
    private void callupdateFCMToken(String baseurl, final UpdateFCMTokenEntity updateFCMTokenEntity) {
        final String methodName = "SettingsController:-callupdateFCMToken()";
        try {
            String updateFCMTokenURL = VerificationURLHelper.getShared().getUpdateFCMTokenURL(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Settings Service call
            SettingsService.getShared(context).updateFCMToken(updateFCMTokenURL, headers, updateFCMTokenEntity, new EventResult<UpdateFCMTokenResponseEntity>() {
                @Override
                public void success(UpdateFCMTokenResponseEntity result) {
                    DBHelper.getShared().setFCMToken(updateFCMTokenEntity.getPush_id());
                }

                @Override
                public void failure(WebAuthError error) {
                    WebAuthError.getShared(context).FCMTokenFailure(methodName);
                }
            });
        } catch (Exception e) {
            WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.UPDATE_FCM_TOKEN, e.getMessage());
        }
    }

    public void getConfiguredMFAListThirdParty(String baseurl, String sub, String linkeddeviceid, String clientid, final EventResult<ConfiguredMFAList> settingsResult) {
        checkConfiguredMFAListThirdParty(baseurl, sub,linkeddeviceid,clientid, settingsResult);
    }
    private void checkConfiguredMFAListThirdParty(String baseurl, String sub, String linkeddeviceid, String clientid, final EventResult<ConfiguredMFAList> settingsResult) {
        String methodName = "SettingsController:-checkConfiguredMFAList()";
        try {
            if (sub != null && !sub.equals("")) {

                addPropertiesThirdParty(baseurl,sub,linkeddeviceid,clientid, settingsResult);
            } else {
                settingsResult.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null", "Error:" + methodName));
                return;
            }
        } catch (Exception e) {
            settingsResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.MFA_LIST_FAILURE,
                    e.getMessage()));
        }
    }
    private void callSettingsThirdParty(String baseurl, final GetMFAListEntity getMFAListEntity, final EventResult<ConfiguredMFAList> configuredMFAListResult) {
        String methodName = "SettingsController:-callSettings()";
        try {
            String configuredListURL = VerificationURLHelper.getShared().getConfiguredListURL(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Settings Service call
            SettingsService.getShared(context).getConfigurationListThirdParty(configuredListURL, headers, getMFAListEntity, configuredMFAListResult);

        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
    private void addPropertiesThirdParty(String baseurl, final String sub, String linkeddeviceid, String clientid, final EventResult<ConfiguredMFAList> configuredMFAListResult) {
        String methodName = "SettingsController:-addProperties()";
        try {
            //App properties
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    // final String baseurl = loginPropertiesResult.get("DomainURL");
                    //final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    GetMFAListEntity getMFAListEntity = new GetMFAListEntity(deviceInfoEntity.getDeviceId(), deviceInfoEntity.getPushNotificationId(), clientid, sub,linkeddeviceid);
                    getMFAListEntity.setLinked_device_id(linkeddeviceid);
                    //call settings call
                    //callSettings(baseurl, getMFAListEntity, configuredMFAListResult);
                    callSettingsThirdParty(baseurl, getMFAListEntity, configuredMFAListResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    configuredMFAListResult.failure(error);
                }
            });

        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


}
