package widas.cidaassdkv2.cidaasVerificationV2.domain.Controller.AuthenticationFlow.VerificationContinue;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;

import java.util.Dictionary;
import java.util.Map;

import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinue;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinueResponseEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.Helper.VerificationURLHelper;
import widas.cidaassdkv2.cidaasVerificationV2.domain.Service.VerificationContinue.VerificationContinueService;

public class VerificationContinueController {
    //Local Variables
    private Context context;


    public static VerificationContinueController shared;

    public VerificationContinueController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static VerificationContinueController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new VerificationContinueController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("VerificationContinueController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------VerificationContinue--------------------------------------------------------------
    public void verificationContinue(final VerificationContinue verificationContinueEntity, final Result<LoginCredentialsResponseEntity> verificationContinueResponseResult)
    {
        checkVerificationContinueEntity(verificationContinueEntity,verificationContinueResponseResult);
    }


    //-------------------------------------checkVerificationContinueEntity-----------------------------------------------------------
    private void checkVerificationContinueEntity(final VerificationContinue verificationContinueEntity, final Result<LoginCredentialsResponseEntity> VerificationContinueResult)
    {
        String methodName = "VerificationContinueController:-checkVerificationContinueEntity()";
        try {
            if (verificationContinueEntity.getVerificationType() != null && !verificationContinueEntity.getVerificationType().equals("") &&
                    verificationContinueEntity.getSub() != null && !verificationContinueEntity.getSub().equals("")) {

                if(verificationContinueEntity.getStatus_id() == null || verificationContinueEntity.getStatus_id().equals("")
                        && verificationContinueEntity.getRequestId()==null || verificationContinueEntity.getRequestId().equals(""))
                {
                    VerificationContinueResult.failure(WebAuthError.getShared(context).propertyMissingException("Status_id or RequestId must not be null",
                            "Error:"+methodName));
                    return;
                }

                if(verificationContinueEntity.getUsageType().equals(UsageType.MFA))
                {
                    if(verificationContinueEntity.getTrackId()==null ||verificationContinueEntity.getTrackId().equals(""))
                    {
                        VerificationContinueResult.failure(WebAuthError.getShared(context).propertyMissingException("TrackId must not be null",
                                "Error:"+methodName));
                        return;
                    }
                }

                addProperties(verificationContinueEntity,VerificationContinueResult);
            }
            else
            {
                VerificationContinueResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:"+methodName));
                return;
            }
        }
        catch (Exception e) {
            VerificationContinueResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final VerificationContinue verificationContinueEntity, final Result<LoginCredentialsResponseEntity> verificationContinueResponseResult)
    {
        String methodName = "VerificationContinueController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");

                  if(verificationContinueEntity.getUsageType().equals(UsageType.MFA)) {
                      //call VerificationContinue call
                      String verificationContinueUrl= VerificationURLHelper.getShared().getMfaContinueCallUrl(baseurl,verificationContinueEntity.getTrackId());
                      callVerificationContinue(verificationContinueUrl, verificationContinueEntity, verificationContinueResponseResult);
                  }
                  else if(verificationContinueEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                  {
                      String verificationContinueUrl= VerificationURLHelper.getShared().getPasswordlessContinueUrl(baseurl);
                      callVerificationContinue(verificationContinueUrl, verificationContinueEntity, verificationContinueResponseResult);
                  }
                }
                @Override
                public void failure(WebAuthError error) {
                    verificationContinueResponseResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            verificationContinueResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.RESUME_LOGIN_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call VerificationContinue Service-----------------------------------------------------------
    private void callVerificationContinue(String resumeURL,final VerificationContinue verificationContinueEntity, final Result<LoginCredentialsResponseEntity> verificationContinueResult)
    {
        String methodName = "VerificationContinueController:-callPasswordlessVerificationContinue()";
        try
        {
            //App properties
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            verificationContinueEntity.setDevice_id(deviceInfoEntity.getDeviceId());
            verificationContinueEntity.setPush_id(deviceInfoEntity.getPushNotificationId());

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //VerificationContinue Service call
            VerificationContinueService.getShared(context).callVerificationContinueService(resumeURL, headers, verificationContinueEntity, new Result<VerificationContinueResponseEntity>() {
                @Override
                public void success(VerificationContinueResponseEntity result) {
                    getAccessTokenFromCode(result,verificationContinueResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    verificationContinueResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            verificationContinueResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }

    public void getAccessTokenFromCode(VerificationContinueResponseEntity result,final Result<LoginCredentialsResponseEntity> verificationContinueResult) {
        String methodName = "VerificationContinueController:-getAccessTokenFromCode()";
        try {
            AccessTokenController.getShared(context).getAccessTokenByCode(result.getData().getCode(), new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity accessTokenresult) {
                    LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
                    loginCredentialsResponseEntity.setData(accessTokenresult);
                    loginCredentialsResponseEntity.setStatus(200);
                    loginCredentialsResponseEntity.setSuccess(true);
                    verificationContinueResult.success(loginCredentialsResponseEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                    verificationContinueResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            verificationContinueResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                e.getMessage()));
        }
    }

}
