package widas.cidaassdkv2.cidaasVerificationV2.domain.Controller.Settings;

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

import java.util.Dictionary;
import java.util.Map;

import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.ConfiguredMFAList.GetMFAListEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.Others.UpdateFCMTokenEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.UpdateFCMToken.UpdateFCMTokenResponseEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.Helper.VerificationURLHelper;
import widas.cidaassdkv2.cidaasVerificationV2.domain.Service.Settings.SettingsService;

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
    public void getConfiguredMFAList(String sub,final Result<ConfiguredMFAList> settingsResult)
    {
        checkConfiguredMFAList(sub,settingsResult);
    }


    //-------------------------------------checkScannedEntity-----------------------------------------------------------
    private void checkConfiguredMFAList(String sub, final Result<ConfiguredMFAList> settingsResult)
    {
        String methodName = "SettingsController:-checkConfiguredMFAList()";
        try {
            if (sub != null && !sub.equals("")) {

                addProperties(sub,settingsResult);
            }
            else
            {
                settingsResult.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null", "Error:"+methodName));
                return;
            }
        }
        catch (Exception e) {
            settingsResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.MFA_LIST_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final String sub, final Result<ConfiguredMFAList> configuredMFAListResult)
    {
        String methodName = "SettingsController:-addProperties()";
        try {
            //App properties
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity=DBHelper.getShared().getDeviceInfo();
                    GetMFAListEntity getMFAListEntity=new GetMFAListEntity(deviceInfoEntity.getDeviceId(),deviceInfoEntity.getPushNotificationId(),clientId,sub);

                    //call settings call
                    callSettings(baseurl,getMFAListEntity,configuredMFAListResult);

                }
                @Override
                public void failure(WebAuthError error) {
                    configuredMFAListResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call settings Service-----------------------------------------------------------
    private void callSettings(String baseurl,final GetMFAListEntity getMFAListEntity, final Result<ConfiguredMFAList> configuredMFAListResult)
    {
        String methodName = "SettingsController:-callSettings()";
        try
        {
            String configuredListURL= VerificationURLHelper.getShared().getConfiguredListURL(baseurl);

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //Settings Service call
            SettingsService.getShared(context).getConfigurationList(configuredListURL,headers,getMFAListEntity,configuredMFAListResult);
        }
        catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
    //--------------------------------------------Settings--------------------------------------------------------------
    public void updateFCMToken(final String newFCMToken)
    {
        final String methodName = "SettingsController:-updateFCMToken()";
        DeviceInfoEntity deviceInfoEntity= DBHelper.getShared().getDeviceInfo();

        if(newFCMToken!=null && !newFCMToken.equals("")) {
            //Check for DB if it is null or empty save it in DB
            if (deviceInfoEntity.getPushNotificationId() == null || deviceInfoEntity.getPushNotificationId().equals("")) {
                DBHelper.getShared().setFCMToken(newFCMToken);
                //addPropertiesForFCM(newFCMToken);
            } else if (deviceInfoEntity.getPushNotificationId().equals(newFCMToken)) {
                //No problem
            } else {
                addPropertiesForFCM(newFCMToken);
            }
        }
        else
        {
            //Todo handle Error
            WebAuthError.getShared(context).propertyMissingException("FCMToken must not be null",methodName);
        }
    }

    public void addPropertiesForFCM(final String newFCMToken) {
        final String methodName = "SettingsController:-addPropertiesForFCM()";
        if(newFCMToken!=null && !newFCMToken.equals(""))
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Add Properties
                    DeviceInfoEntity deviceInfoEntity= DBHelper.getShared().getDeviceInfo();
                    UpdateFCMTokenEntity updateFCMTokenEntity=new UpdateFCMTokenEntity(deviceInfoEntity.getDeviceId(),newFCMToken,
                            clientId,deviceInfoEntity.getPushNotificationId());

                    callupdateFCMToken(baseurl,updateFCMTokenEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                  WebAuthError.getShared(context).CidaaspropertyMissingException("",methodName);
                }
            });

        }
        else
        {
            WebAuthError.getShared(context).propertyMissingException("FCMToken must not be null",methodName);
        }
    }

    //-------------------------------------------Call settings Service-----------------------------------------------------------
    private void callupdateFCMToken(String baseurl, final UpdateFCMTokenEntity updateFCMTokenEntity)
    {
        final String methodName = "SettingsController:-callupdateFCMToken()";
        try
        {
            String updateFCMTokenURL= VerificationURLHelper.getShared().getUpdateFCMTokenURL(baseurl);

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //Settings Service call
            SettingsService.getShared(context).updateFCMToken(updateFCMTokenURL, headers, updateFCMTokenEntity, new Result<UpdateFCMTokenResponseEntity>() {
                @Override
                public void success(UpdateFCMTokenResponseEntity result) {
                    DBHelper.getShared().setFCMToken(updateFCMTokenEntity.getPush_id());
                }

                @Override
                public void failure(WebAuthError error) {
                    WebAuthError.getShared(context).FCMTokenFailure(methodName);
                }
            });
        }
        catch (Exception e) {
            WebAuthError.getShared(context).methodException(methodName,WebAuthErrorCode.UPDATE_FCM_TOKEN,e.getMessage());
        }
    }




}
