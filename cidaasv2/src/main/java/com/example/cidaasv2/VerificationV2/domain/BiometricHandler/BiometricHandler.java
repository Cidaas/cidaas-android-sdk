package com.example.cidaasv2.VerificationV2.domain.BiometricHandler;

import android.content.Context;
import android.os.Build;

import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricCallback;
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricManager;

public class BiometricHandler {

    //Local Variables
    private Context context;


    public static BiometricHandler shared;

    public BiometricHandler(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static BiometricHandler getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new BiometricHandler(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("BiometricHandler instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }



    public void callFingerPrint(FingerPrintEntity fingerPrintEntity, final String methodName, final Result<String> result)
    {
        try
        {
            if (Build.VERSION.SDK_INT >= 23) {

                FingerPrintEntity fingerPrintEntityForPassing = new FingerPrintEntity();
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
                                        "SDK Version Not Supported",methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotSupported() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMETRIC_AUTHENTICATION_NOT_SUPPORTED,
                                        "Biometric Authentication  Not Supported",methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotAvailable() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_NOT_AVAILABLE,
                                        "Biometric Authentication  Not Available",methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationPermissionNotGranted() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_PERMISSION_NOT_GRANTED,
                                        "Biometric Authentication  Permission Not Granted",methodName));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationInternalError(String error) {

                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_INTERNAL_ERROR,
                                        "Biometric Authentication  Internal Error",methodName));
                                return;
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                // result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,"Biometric Authentication  Failed"));
                            }

                            @Override
                            public void onAuthenticationCancelled() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_CANCELLED,
                                        "Biometric Authentication  Cancelled",methodName));
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
                                result.failure(WebAuthError.getShared(context).fingerPrintError(helpCode, errorMessage,methodName));
                                return;
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {

                                String errorMessage = errString.toString();
                                result.failure(WebAuthError.getShared(context).fingerPrintError(errorCode, errorMessage,methodName));
                                return;
                            }
                        });
            }
            else
            {
                String errorMessage="Fingerprint doesnot Support in your mobile";
                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,errorMessage,
                        methodName));
                return;
            }
        }
        catch (Exception e)
        {
            result.failure( WebAuthError.getShared(context).methodException("Exception :BiometricHandler :callFingerPrint()",
                    WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,e.getMessage()));

        }

    }
}
