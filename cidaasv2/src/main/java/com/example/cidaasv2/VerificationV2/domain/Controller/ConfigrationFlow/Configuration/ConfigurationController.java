package com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Configuration;

import android.content.Context;

import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.ConfigureRequest.ConfigurationRequest;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Enroll.EnrollController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Scanned.ScannedController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Setup.SetupController;

public class ConfigurationController {

    //Local Variables
    private Context context;


    public static ConfigurationController shared;

    private ConfigurationController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static ConfigurationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ConfigurationController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ConfigurationController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Configuration--------------------------------------------------------------
    public void configurationVerification(final ConfigurationRequest configurationRequest, final String verificationType, final Result<EnrollResponse> enrollResponseResult)
    {
        String methodName="ConfigurationController:configurationVerification()";
        try {
            SetupEntity setupEntity = new SetupEntity(configurationRequest.getSub(), verificationType);
            setup(setupEntity, new Result<SetupResponse>() {
                @Override
                public void success(SetupResponse setupResult) {
                    String exchange_id=setupResult.getData().getExchange_id().getExchange_id();

                    ScannedEntity scannedEntity = new ScannedEntity(configurationRequest.getSub(),exchange_id ,verificationType);

                    ScannedController.getShared(context).scannedVerification(scannedEntity, new Result<ScannedResponse>() {
                        @Override
                        public void success(ScannedResponse scannedResult) {
                            String exchange_id=scannedResult.getData().getExchange_id().getExchange_id();
                            generateEnrollEntity(exchange_id, verificationType, configurationRequest, enrollResponseResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            enrollResponseResult.failure(error);
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResponseResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
           enrollResponseResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONFIGURE_MFA_FAILURE,e.getMessage()));
        }
    }

    private void generateEnrollEntity(String exchange_id, String verificationType, ConfigurationRequest configurationRequest,
                                      Result<EnrollResponse> enrollResponseResult)
    {
        String methodName="ConfigurationController:generateEnrollEntity()";
        try {
            // handle Enroll entity and call enroll
            EnrollEntity enrollEntity;

            switch (verificationType) {
                case AuthenticationType.FACE:
                    enrollEntity = new EnrollEntity(exchange_id, verificationType, configurationRequest.getFileToSend(), configurationRequest.getAttempt());
                    break;

                case AuthenticationType.VOICE:
                    enrollEntity = new EnrollEntity(exchange_id, verificationType, configurationRequest.getFileToSend(), configurationRequest.getAttempt());
                    break;

                case AuthenticationType.FINGERPRINT:
                    enrollEntity = new EnrollEntity(exchange_id, verificationType, configurationRequest.getFingerPrintEntity());
                    break;

                case AuthenticationType.SMARTPUSH:
                    enrollEntity = new EnrollEntity(exchange_id, configurationRequest.getPass_code(), verificationType);
                    break;

                case AuthenticationType.PATTERN:
                    enrollEntity = new EnrollEntity(exchange_id, configurationRequest.getPass_code(), verificationType);
                    break;

                case AuthenticationType.TOTP:
                    enrollEntity = new EnrollEntity(exchange_id, configurationRequest.getPass_code(), verificationType);
                    break;

                default:
                    enrollResponseResult.failure(WebAuthError.getShared(context).invalidPropertiesException("Invalid Verification Type:- " +
                            verificationType, methodName));
                    return;

            }
            EnrollController.getShared(context).enrollVerification(enrollEntity, enrollResponseResult);
        }
        catch (Exception e)
        {
            enrollResponseResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONFIGURE_MFA_FAILURE,e.getMessage()));
        }
    }

    public void setup(SetupEntity setupEntity, Result<SetupResponse> setupResponseResult)
    {
        SetupController.getShared(context).setupVerification(setupEntity,setupResponseResult);
    }



}
