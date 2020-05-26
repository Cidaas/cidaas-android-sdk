/*
package widas.cidaassdkv2.cidaasnativev2.domain.Controller.ResumeLogin;

import android.content.Context;

import LoginController;
import PasswordlessEntity;
importEventResult;
import UsageType;
import WebAuthErrorCode;
import WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResumeLogin.ResumeLoginRequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResumeLogin {

    public static ResumeLogin shared;
    private Context context;

    private ObjectMapper objectMapper=new ObjectMapper();

    public  ResumeLogin(Context contextFromCidaas) {
        context=contextFromCidaas;
    }


    public static ResumeLogin getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ResumeLogin(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            // Timber.i(e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------------------resumeLoginAfterSuccessfullAuthentication--------------------------------------------------

    public void resumeLoginAfterSuccessfullAuthentication(String sub, String trackingCode, String verificationType, String usageType,
                                                          String clientId,String requestId,String trackId, String baseURL,
                                                         EventResult<LoginCredentialsResponseEntity> loginresult)
    {String methodName="ResumeLogin Controller :resumeLoginAfterSuccessfullAuthentication()";
        try {

            ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

             resumeLoginRequestEntity.setSub(sub);
            resumeLoginRequestEntity.setTrackingCode(trackingCode);
            resumeLoginRequestEntity.setVerificationType(verificationType);
            resumeLoginRequestEntity.setUsageType(usageType);
            resumeLoginRequestEntity.setClient_id(clientId);
            resumeLoginRequestEntity.setRequestId(requestId);

            if (usageType.equals(UsageType.MFA)) {

                resumeLoginRequestEntity.setTrack_id(trackId);
                LoginController.getShared(context).continueMFA(baseURL, resumeLoginRequestEntity, loginresult);

            } else if (usageType.equals(UsageType.PASSWORDLESS)) {

                resumeLoginRequestEntity.setTrack_id("");
                LoginController.getShared(context).continuePasswordless(baseURL, resumeLoginRequestEntity, loginresult);

            }
        }
        catch (Exception e)
        {
        loginresult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }

    }

    //--------------------------------------------------------resumeLoginAfterSuccessfullAuthentication--------------------------------------------------
    public void resumeLoginAfterSuccessfullAuthentication(String sub, String trackingCode, String verificationType, PasswordlessEntity passwordlessEntity,
                                                          String clientId, String baseURL,
                                                         EventResult<LoginCredentialsResponseEntity> loginresult) {
        resumeLoginAfterSuccessfullAuthentication(sub,trackingCode,verificationType,passwordlessEntity.getUsageType(),clientId,
                passwordlessEntity.getRequestId(),passwordlessEntity.getTrackId(),baseURL,loginresult);

    }

    //-----------------------------------------------------------resumeLoginAfterConsent------------------------------------------------------------------

}
*/
