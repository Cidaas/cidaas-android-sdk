package com.example.cidaasv2.Controller.Repository.ResumeLogin;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import timber.log.Timber;

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


    public void resumeLoginAfterSuccessfullAuthentication(String sub, String trackingCode, String verificationType, String usageType, String clientId,
                                                          String requestId, String trackId, String baseURL,
                                                          Result<LoginCredentialsResponseEntity> loginresult) {
        try {

            ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

            //Todo Check not Null values
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
            LogFile.getShared(context).addRecordToLog("Failure in Resume Login Service exception" + e.getMessage());
            loginresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESUME_LOGIN_FAILURE));
            Timber.e("Failure in Resume Login Service exception" + e.getMessage());
        }
    }
}
