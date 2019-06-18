package com.example.cidaasv2.VerificationV2.domain.Controller.Authenticate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.Helper.BiometricHandler.BiometricHandler;
import com.example.cidaasv2.VerificationV2.domain.Service.Authenticate.AuthenticateService;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AuthenticateController {
    //Local Variables
    private Context context;


    public static AuthenticateController shared;

    public AuthenticateController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static AuthenticateController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AuthenticateController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("AuthenticateController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Authenticate--------------------------------------------------------------
    public void authenticateVerification(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        checkAuthenticateEntity(authenticateEntity,authenticateResult);
    }


    //-------------------------------------checkAuthenticateEntity-----------------------------------------------------------
    private void checkAuthenticateEntity(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-checkAuthenticateEntity()";
        try {

                if( authenticateEntity.getVerificationType() != null && !authenticateEntity.getVerificationType().equals("")&&
                        authenticateEntity.getExchange_id() != null && !authenticateEntity.getExchange_id().equals(""))
                {
                    LogFile.getShared(context).addInfoLog(methodName,"Verification Type:"+authenticateEntity.getVerificationType()+
                            "ExchangeId:"+authenticateEntity.getExchange_id());
                    // Todo Check For Face and Voice
                    handleVerificationTypes(authenticateEntity,authenticateResult);
                }
                else
                {
                    authenticateResult.failure(WebAuthError.getShared(context).propertyMissingException(
                            "ExchangeId or Verification Type must not be null", "Error:"+methodName));
                    return;
                }

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //-----------------------------------------------handleVerificationTypes---------------------------------------------------------------
    private void handleVerificationTypes(AuthenticateEntity authenticateEntity, Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-handleVerificationTypes()";
        try {
            switch (authenticateEntity.getVerificationType()) {

                case AuthenticationType.FINGERPRINT:
                {
                    //FingerPrint
                    callFingerPrintAuthentication(authenticateEntity, authenticateResult);
                    break;
                }
                case AuthenticationType.FACE:
                {
                    Bitmap finalimg = BitmapFactory.decodeFile(authenticateEntity.getFileToSend().getAbsolutePath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), authenticateEntity.getFileToSend());
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "cidaas.png", requestFile);

                    addPropertiesForFaceOrVoice(photo,authenticateEntity,authenticateResult);
                    break;
                }
                case AuthenticationType.VOICE:
                {

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), authenticateEntity.getFileToSend());
                    MultipartBody.Part voice = MultipartBody.Part.createFormData("voice", "Audio.fav", requestFile);

                    addPropertiesForFaceOrVoice(voice,authenticateEntity,authenticateResult);
                    break;
                }
                default:
                {
                    if (authenticateEntity.getPass_code() != null && !authenticateEntity.getPass_code().equals("")) {

                        addProperties(authenticateEntity, authenticateResult);
                    }
                    else {
                        authenticateResult.failure(WebAuthError.getShared(context).propertyMissingException("Passcode must not be empty", "Error:" + methodName));
                        return;
                    }
                }
            }

        }
        catch (Exception e)
        {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void callFingerPrintAuthentication(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-callFingerPrintAuthentication()";
        try {
            BiometricHandler biometricHandler=new BiometricHandler(authenticateEntity.getFingerPrintEntity().getContext());
           biometricHandler.callFingerPrint(authenticateEntity.getFingerPrintEntity(), methodName, new Result<String>() {
                @Override
                public void success(String result) {
                    //call authenticate call
                    //authenticateEntity.setPass_code(DBHelper.getShared().getDeviceInfo().getDeviceId());
                    addProperties(authenticateEntity,authenticateResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    authenticateResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }



    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    authenticateEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    authenticateEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    authenticateEntity.setClient_id(clientId);

                    //call authenticate call
                    callAuthenticate(baseurl,authenticateEntity,authenticateResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    authenticateResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call authenticate Service-----------------------------------------------------------
    private void callAuthenticate(String baseurl,final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-authenticate()";
        try
        {
            String authenticateUrl= VerificationURLHelper.getShared().getAuthenticateURL(baseurl,authenticateEntity.getVerificationType());

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //Authenticate Service call
            AuthenticateService.getShared(context).callAuthenticateService(authenticateUrl,headers,authenticateEntity,authenticateResult);

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addPropertiesForFaceOrVoice(final MultipartBody.Part  filetosend, final AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-addPropertiesForFaceOrVoice()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId = loginPropertiesResult.get("ClientId");

                    //Change To Hashmap and Add Properties
                    HashMap<String, RequestBody> authenticateHashmap = new HashMap<>();
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

                    //Optional Sub
                    //  authenticateHashmap.put("",StringtoRequestBody(authenticateEntity.))
                    authenticateHashmap.put("exchange_id",StringtoRequestBody(authenticateEntity.getExchange_id()));
                    authenticateHashmap.put("device_id", StringtoRequestBody(deviceInfoEntity.getDeviceId()));
                    authenticateHashmap.put("client_id",StringtoRequestBody(clientId));
                    authenticateHashmap.put("push_id", StringtoRequestBody(deviceInfoEntity.getPushNotificationId()));
                    authenticateHashmap.put("face_attempt",StringtoRequestBody(""+authenticateEntity.getFace_attempt()+""));


                    //call authenticate call
                    callAuthenticateForFaceandVoice(baseurl,filetosend,authenticateHashmap,authenticateEntity.getVerificationType(),authenticateResult);

                  }
                @Override
                public void failure(WebAuthError error) {
                    authenticateResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call authenticate Service-----------------------------------------------------------
    private void callAuthenticateForFaceandVoice(String baseurl,final MultipartBody.Part file, final HashMap<String, RequestBody> authenticateHashmap,
                                                 final String verificationType, final Result<AuthenticateResponse> authenticateResult)
    {
        String methodName = "AuthenticateController:-authenticate()";
        try
        {
            String authenticateUrl= VerificationURLHelper.getShared().getAuthenticateURL(baseurl,verificationType);

            //headers Generation
            Map<String,String> headers=Headers.getShared(context).getHeaders(null,false,null);

            //Authenticate Service call
            AuthenticateService.getShared(context).callAuthenticateServiceForFaceOrVoice(file,authenticateUrl,headers,authenticateHashmap,authenticateResult);
        }
        catch (Exception e) {
            authenticateResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //---------------------------------------------------String to requestBodyConversion-------------------------------------
    public RequestBody StringtoRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
}
