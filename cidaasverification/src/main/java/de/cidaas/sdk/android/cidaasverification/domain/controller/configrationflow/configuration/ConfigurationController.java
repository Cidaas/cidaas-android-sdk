package de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.configuration;

import android.content.Context;

import de.cidaas.sdk.android.cidaasverification.data.entity.enduser.configurerequest.ConfigurationRequest;
import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupResponse;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.enroll.EnrollController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.scanned.ScannedController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.setup.SetupController;
import de.cidaas.sdk.android.cidaasverification.domain.helper.totpgenerator.GoogleAuthenticator;
import de.cidaas.sdk.android.helper.AuthenticationType;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;


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
    public void configureVerification(final ConfigurationRequest configurationRequest, final String verificationType, final EventResult<EnrollResponse> enrollResponseResult) {
        callSetupCall(configurationRequest, verificationType, enrollResponseResult);
    }

    //--------------------------------------------Configuration Setup--------------------------------------------------------------

    private void callSetupCall(final ConfigurationRequest configurationRequest, final String verificationType, final EventResult<EnrollResponse> enrollResponseResult) {
        String methodName = "ConfigurationController:configureVerification()";
        try {
            SetupEntity setupEntity = new SetupEntity(configurationRequest.getSub(), verificationType);
            setup(setupEntity, new EventResult<SetupResponse>() {
                @Override
                public void success(SetupResponse setupResult) {
                    String exchange_id = setupResult.getData().getExchange_id().getExchange_id();
                    if (verificationType.contains(AuthenticationType.SMARTPUSH)) {
                        configurationRequest.setPass_code(setupResult.getData().getPush_selected_number());
                    }
                    if (verificationType.contains(AuthenticationType.TOTP)) {
                        DBHelper.getShared().addSecret(setupResult.getData().getTotp_secret(), configurationRequest.getSub());
                        configurationRequest.setPass_code(GoogleAuthenticator.getTOTPCode(setupResult.getData().getTotp_secret()));
                        EnrollEntity enrollEntity = new EnrollEntity(exchange_id, configurationRequest.getPass_code(), verificationType);
                        EnrollController.getShared(context).enrollVerification(enrollEntity, enrollResponseResult);

                    } else {
                        callScannedCall(exchange_id, configurationRequest, verificationType, enrollResponseResult);
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResponseResult.failure(error);
                }
            });
        } catch (Exception e) {
            enrollResponseResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONFIGURE_MFA_FAILURE, e.getMessage()));
        }
    }

    //--------------------------------------------Configuration Scanned--------------------------------------------------------------


    private void callScannedCall(String exchange_id, final ConfigurationRequest configurationRequest, final String verificationType,
                                 final EventResult<EnrollResponse> enrollResponseResult) {
        String methodName = "ConfigurationController:callScannedCall()";
        try {
            ScannedEntity scannedEntity = new ScannedEntity(configurationRequest.getSub(), exchange_id, verificationType);

            ScannedController.getShared(context).scannedVerification(scannedEntity, new EventResult<ScannedResponse>() {
                @Override
                public void success(ScannedResponse scannedResult) {
                    generateEnrollEntity(scannedResult, verificationType, configurationRequest, enrollResponseResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResponseResult.failure(error);
                }
            });
        } catch (Exception e) {
            enrollResponseResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONFIGURE_MFA_FAILURE, e.getMessage()));
        }
    }

    //--------------------------------------------Configuration enrollEntity--------------------------------------------------------------


    private void generateEnrollEntity(ScannedResponse scannedResult, String verificationType, ConfigurationRequest configurationRequest,
                                      EventResult<EnrollResponse> enrollResponseResult) {
        String methodName = "ConfigurationController:generateEnrollEntity()";
        try {
            // handle Enroll entity and call enroll
            EnrollEntity enrollEntity;

            String exchange_id = scannedResult.getData().getExchange_id().getExchange_id();

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


                default:
                    enrollResponseResult.failure(WebAuthError.getShared(context).invalidPropertiesException("Invalid Verification Type:- " +
                            verificationType, methodName));
                    return;

            }
            EnrollController.getShared(context).enrollVerification(enrollEntity, enrollResponseResult);
        } catch (Exception e) {
            enrollResponseResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONFIGURE_MFA_FAILURE, e.getMessage()));
        }
    }

    public void setup(SetupEntity setupEntity, EventResult<SetupResponse> setupResponseResult) {
        SetupController.getShared(context).setupVerification(setupEntity, setupResponseResult);
    }

}
