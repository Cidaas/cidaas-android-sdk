package de.cidaas.sdk.android.cidaasVerification.domain.Controller.ConfigrationFlow.Enroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.Helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasVerification.domain.Helper.BiometricHandler.BiometricHandler;
import de.cidaas.sdk.android.cidaasVerification.domain.Service.Enroll.EnrollService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.AuthenticationType;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EnrollController {
    //Local Variables
    private Context context;


    public static EnrollController shared;

    public EnrollController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static EnrollController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new EnrollController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getInstance(contextFromCidaas).addFailureLog("EnrollController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Enroll--------------------------------------------------------------
    public void enrollVerification(final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        checkEnrollEntity(enrollEntity, enrollResult);
    }


    //-------------------------------------checkEnrollEntity-----------------------------------------------------------
    private void checkEnrollEntity(final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-checkEnrollEntity()";
        try {
            if (enrollEntity.getVerificationType() != null && !enrollEntity.getVerificationType().equals("") &&
                    enrollEntity.getExchange_id() != null && !enrollEntity.getExchange_id().equals("")) {
                handleVerificationTypes(enrollEntity, enrollResult);
            } else {
                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Verification type or ExchangeId must not be empty",
                        "Error:" + methodName));
                return;
            }
        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------handleVerificationTypes---------------------------------------------------------------
    private void handleVerificationTypes(EnrollEntity enrollEntity, EventResult<EnrollResponse> enrollResult) {
        //Handle if Passcode is send for finger
        String methodName = "EnrollController:-handleVerificationTypes()";
        try {

            switch (enrollEntity.getVerificationType()) {

                case AuthenticationType.FINGERPRINT: {
                    //FingerPrint
                    callFingerPrintAuthentication(enrollEntity, enrollResult);
                    break;
                }
                case AuthenticationType.FACE: {
                    Bitmap finalimg = BitmapFactory.decodeFile(enrollEntity.getFileToSend().getAbsolutePath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), enrollEntity.getFileToSend());
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "de.cidaas.png", requestFile);

                    addPropertiesForFaceOrVoice(photo, enrollEntity, enrollResult);
                    break;
                }
                case AuthenticationType.VOICE: {

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), enrollEntity.getFileToSend());
                    MultipartBody.Part voice = MultipartBody.Part.createFormData("voice", "Audio.fav", requestFile);

                    addPropertiesForFaceOrVoice(voice, enrollEntity, enrollResult);
                    break;
                }
                default: {
                    if (enrollEntity.getPass_code() != null && !enrollEntity.getPass_code().equals("")) {

                        addProperties(enrollEntity, enrollResult);
                    } else {
                        enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Passcode must not be empty", "Error:" + methodName));
                        return;
                    }
                }
            }

        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void callFingerPrintAuthentication(final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-callFingerPrintAuthentication()";
        try {
            BiometricHandler biometricHandler = new BiometricHandler(enrollEntity.getFingerPrintEntity().getContext());

            biometricHandler.callFingerPrint(enrollEntity.getFingerPrintEntity(), methodName, new EventResult<String>() {
                @Override
                public void success(String result) {
                    //set pass code as device id and call enroll call
                    // enrollEntity.setPass_code(DBHelper.getShared().getDeviceInfo().getDeviceId());
                    addProperties(enrollEntity, enrollResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResult.failure(error);
                }
            });

        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-addProperties()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    enrollEntity.setDevice_id(deviceInfoEntity.getDeviceId());
                    enrollEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    enrollEntity.setClient_id(clientId);

                    //call enroll call
                    callEnroll(baseurl, enrollEntity, enrollResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResult.failure(error);
                }
            });


        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addPropertiesForFaceOrVoice(final MultipartBody.Part filetosend, final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-addPropertiesForFaceOrVoice()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    //Change To Hashmap and Add Properties
                    HashMap<String, RequestBody> enrollHashmap = new HashMap<>();
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

                    //Optional Sub
                    //  enrollHashmap.put("",StringtoRequestBody(enrollEntity.))
                    enrollHashmap.put("exchange_id", StringtoRequestBody(enrollEntity.getExchange_id()));
                    enrollHashmap.put("device_id", StringtoRequestBody(deviceInfoEntity.getDeviceId()));
                    enrollHashmap.put("client_id", StringtoRequestBody(clientId));
                    enrollHashmap.put("push_id", StringtoRequestBody(deviceInfoEntity.getPushNotificationId()));
                    enrollHashmap.put("face_attempt", StringtoRequestBody("" + enrollEntity.getAttempt() + ""));


                    //call enroll call
                    callEnrollForFaceandVoice(baseurl, filetosend, enrollHashmap, enrollEntity.getVerificationType(), enrollResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResult.failure(error);
                }
            });


        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call enroll Service-----------------------------------------------------------
    private void callEnroll(String baseurl, final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-enroll()";
        try {
            String enrollUrl = VerificationURLHelper.getShared().getEnrollURL(baseurl, enrollEntity.getVerificationType());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Enroll Service call
            EnrollService.getShared(context).callEnrollService(enrollUrl, headers, enrollEntity, enrollResult);

        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call enroll Service-----------------------------------------------------------
    private void callEnrollForFaceandVoice(String baseurl, final MultipartBody.Part file, final HashMap<String, RequestBody> enrollHashmap,
                                           final String verificationType, final EventResult<EnrollResponse> enrollResult) {
        String methodName = "EnrollController:-enroll()";
        try {
            String enrollUrl = VerificationURLHelper.getShared().getEnrollURL(baseurl, verificationType);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

            //Enroll Service call
            EnrollService.getShared(context).callEnrollServiceForFaceOrVoice(file, enrollUrl, headers, enrollHashmap, enrollResult);

        } catch (Exception e) {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //---------------------------------------------------String to requestBodyConversion-------------------------------------
    public RequestBody StringtoRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

}
