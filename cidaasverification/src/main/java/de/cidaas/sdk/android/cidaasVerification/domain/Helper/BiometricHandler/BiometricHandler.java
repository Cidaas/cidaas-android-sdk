package de.cidaas.sdk.android.cidaasVerification.domain.Helper.BiometricHandler;

import android.content.Context;
import android.os.Build;

import de.cidaas.sdk.android.entities.FingerPrintEntity;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.library.biometricauthentication.BiometricCallback;
import de.cidaas.sdk.android.library.biometricauthentication.BiometricManager;


public class BiometricHandler {

    //Local Variables
    private Context context;


    public static BiometricHandler shared;

    public BiometricHandler(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public void callFingerPrint(FingerPrintEntity fingerPrintEntity, final String methodName, final Result<String> result) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {

                FingerPrintEntity fingerPrintEntityForPassing = new FingerPrintEntity(context);
                if (fingerPrintEntity == null) {
                    fingerPrintEntity = fingerPrintEntityForPassing;
                }

                new BiometricManager.BiometricBuilder(context)
                        .setTitle(fingerPrintEntity.getTitle())
                        .setSubtitle(fingerPrintEntity.getSubtitle())
                        .setDescription(fingerPrintEntity.getDescription())
                        .setNegativeButtonText(fingerPrintEntity.getNegativeButtonString())
                        .build()
                        .authenticate(new BiometricCallback() {
                            @Override
                            public void onSdkVersionNotSupported() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_SDK_VERSION_NOT_SUPPORTED,
                                        "SDK Version Not Supported", methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotSupported() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMETRIC_AUTHENTICATION_NOT_SUPPORTED,
                                        "Biometric Authentication  Not Supported", methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotAvailable() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_NOT_AVAILABLE,
                                        "Biometric Authentication  Not Available", methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationPermissionNotGranted() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_PERMISSION_NOT_GRANTED,
                                        "Biometric Authentication  Permission Not Granted", methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationInternalError(String error) {

                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_INTERNAL_ERROR,
                                        "Biometric Authentication  Internal Error", methodName));
                                return;
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                // result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,"Biometric Authentication  Failed"));
                            }

                            @Override
                            public void onAuthenticationCancelled() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_CANCELLED,
                                        "Biometric Authentication  Cancelled", methodName));
                                return;
                            }

                            @Override
                            public void onAuthenticationSuccessful() {
                                result.success("Success");
                                return;
                            }

                            @Override
                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

                                String errorMessage = helpString.toString();
                                result.failure(WebAuthError.getShared(context).fingerPrintError(helpCode, errorMessage, methodName));
                                return;
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                if (errorCode == 5) {
                                    onAuthenticationCancelled();
                                } else {
                                    String errorMessage = errString.toString();
                                    result.failure(WebAuthError.getShared(context).fingerPrintError(errorCode, errorMessage, methodName));
                                    return;
                                }
                            }
                        });
            } else {
                String errorMessage = "Fingerprint doesnot Support in your mobile";
                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED, errorMessage,
                        methodName));
                return;
            }
        } catch (Exception e) {


            if (e.getMessage().contains("Unable to add window") || e.getMessage().contains("is your activity running?")) {
                result.failure(WebAuthError.getShared(context).methodException("Exception :BiometricHandler :callFingerPrint()",
                        WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED, "Please ensure you pass the current running activity context"));
                return;
            }
            result.failure(WebAuthError.getShared(context).methodException("Exception :BiometricHandler :callFingerPrint()",
                    WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED, e.getMessage()));
            return;

        }

    }
}
