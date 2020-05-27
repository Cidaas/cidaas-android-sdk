package de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.Login;

import android.content.Context;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Authenticate.AuthenticateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Authenticate.AuthenticateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.EndUser.LoginRequest.LoginRequest;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate.InitiateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate.InitiateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.VerificationContinue.VerificationContinue;
import de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.Authenticate.AuthenticateController;
import de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.Initiate.InitiateController;
import de.cidaas.sdk.android.cidaasVerification.domain.Controller.AuthenticationFlow.VerificationContinue.VerificationContinueController;
import de.cidaas.sdk.android.cidaasVerification.domain.Helper.TOTPGenerator.GoogleAuthenticator;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.AuthenticationType;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.UsageType;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;


public class PasswordlessLoginController {
    //Local Variables
    private Context context;


    public static PasswordlessLoginController shared;

    public PasswordlessLoginController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static PasswordlessLoginController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PasswordlessLoginController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PasswordlessLoginController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //--------------------------------------------Login--------------------------------------------------------------
    public void loginVerification(final LoginRequest loginRequest, final String verificationType,
                                  final EventResult<LoginCredentialsResponseEntity> loginCredentialsResult) {
        initiateLogin(loginRequest, verificationType, loginCredentialsResult);
    }

    /**
     * @param loginRequest
     * @param verificationType
     * @param loginCredentialsResult 1.Initiate the authentication Flow
     *                               2.Pass the value to Handle authentication entity based on Type
     *                               3.Handle Error and Exception
     */

    //--------------------------------------------Initiate Login--------------------------------------------------------------
    private void initiateLogin(final LoginRequest loginRequest, final String verificationType, final EventResult<LoginCredentialsResponseEntity>
            loginCredentialsResult) {
        String methodName = "PasswordlessLoginController:-initiateLogin()";
        try {

            if (loginRequest.getRequestId() != null && !loginRequest.getRequestId().equals("")) {
                final String requestId = loginRequest.getRequestId();
                InitiateEntity initiateEntity = new InitiateEntity(loginRequest.getSub(), requestId, loginRequest.getUsageType(),
                        verificationType);

                initiate(initiateEntity, new EventResult<InitiateResponse>() {
                    @Override
                    public void success(InitiateResponse initiateResult) {
                        // handle Authenticate entity and call authenticate
                        handleTypesForAuthentication(initiateResult, loginRequest, requestId, verificationType, loginCredentialsResult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        loginCredentialsResult.failure(error);
                    }
                });
            } else {
                loginCredentialsResult.failure(WebAuthError.getShared(context).propertyMissingException("requestId must not be null", methodName));
            }

        } catch (Exception e) {
            loginCredentialsResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.PASSWORDLESS_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }

    /**
     * @param initiateResult
     * @param loginRequest
     * @param requestId
     * @param verificationType
     * @param loginCredentialsResult 1.Create authenticationEntity base on Verification Type
     *                               2.Pass the value to trigger authentication call authenticateVerification
     *                               3.Handle Error and Exception
     */


    public void handleTypesForAuthentication(InitiateResponse initiateResult, final LoginRequest loginRequest, String requestId,
                                             final String verificationType, final EventResult<LoginCredentialsResponseEntity> loginCredentialsResult) {
        String methodName = "PasswordlessLoginController:handleTypesForAuthentication()";
        try {
            AuthenticateEntity authenticateEntity;

            switch (verificationType) {
                case AuthenticationType.FACE:
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(), verificationType,
                            loginRequest.getFileToSend(), loginRequest.getAttempt());
                    break;

                case AuthenticationType.VOICE:
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(), verificationType,
                            loginRequest.getFileToSend(), loginRequest.getAttempt());
                    break;

                case AuthenticationType.FINGERPRINT:
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(), verificationType,
                            loginRequest.getFingerPrintEntity());
                    break;

                case AuthenticationType.SMARTPUSH:
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(), loginRequest.getPass_code(),
                            verificationType);
                    break;

                case AuthenticationType.PATTERN:
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),
                            loginRequest.getPass_code(), verificationType);
                    break;

                case AuthenticationType.TOTP:
                    String secret = DBHelper.getShared().getSecret(loginRequest.getSub());
                    if (secret == null || secret.equals("")) {
                        loginCredentialsResult.failure(WebAuthError.getShared(context).invalidPropertiesException("TOTP is not configured for this user: invalid or empty secret", methodName));
                        return;
                    }
                    authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),
                            GoogleAuthenticator.getTOTPCode(secret), verificationType);
                    break;

                default:
                    loginCredentialsResult.failure(WebAuthError.getShared(context).invalidPropertiesException("Invalid Verification Type:- " +
                            verificationType, methodName));
                    return;
            }

            authenticateVerification(authenticateEntity, verificationType, requestId, loginRequest, loginCredentialsResult);
        } catch (Exception e) {
            loginCredentialsResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.PASSWORDLESS_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }

    /**
     * @param authenticateEntity
     * @param verificationType
     * @param requestId
     * @param loginRequest
     * @param loginCredentialsResult 1.Call authentication
     *                               2.After Successful authentication call resume login
     *                               3.Handle Error and Exception
     */

    public void authenticateVerification(final AuthenticateEntity authenticateEntity, final String verificationType, final String requestId,
                                         final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> loginCredentialsResult) {
        final String methodName = "PasswordlessLoginController:authenticateVerification()";
        try {
            AuthenticateController.getShared(context).authenticateVerification(authenticateEntity, new EventResult<AuthenticateResponse>() {
                @Override
                public void success(AuthenticateResponse result) {
                    //Sdk continue Call
                    VerificationContinue verificationContinueEntity;
                    if (loginRequest.getUsageType().equals(UsageType.MFA)) {
                        verificationContinueEntity = VerificationContinue.getVerificationContinueEntity(requestId, result.getData().getSub(), loginRequest.getTrackId(),
                                result.getData().getStatus_id(), verificationType);
                    } else if (loginRequest.getUsageType().equals(UsageType.PASSWORDLESS)) {
                        verificationContinueEntity = VerificationContinue.getVerificationContinuePasswordlessEntity(requestId,
                                result.getData().getSub(), result.getData().getStatus_id(), verificationType);
                    } else {
                        loginCredentialsResult.failure(WebAuthError.getShared(context).invalidPropertiesException("Invalid UsageType:- " +
                                loginRequest.getUsageType(), methodName));
                        return;
                    }

                    verificationContinue(verificationContinueEntity, loginCredentialsResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginCredentialsResult.failure(error);
                }
            });
        } catch (Exception e) {
            loginCredentialsResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.PASSWORDLESS_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }

    /**
     * @param verificationContinueEntity
     * @param loginCredentialsResponseEntityResult 1.Call Resume call to get Access Token
     */
    private void verificationContinue(VerificationContinue verificationContinueEntity, EventResult<LoginCredentialsResponseEntity> loginCredentialsResponseEntityResult) {
        VerificationContinueController.getShared(context).verificationContinue(verificationContinueEntity, loginCredentialsResponseEntityResult);
    }

    /**
     * @param initiateEntity-Entity  to Initiate call
     * @param initiateResponseResult 1. Call Initiate to start the authentication Flow
     */
    //-------------------------------------------------------INITIATE CALL--------------------------------------------------------------
    private void initiate(InitiateEntity initiateEntity, EventResult<InitiateResponse> initiateResponseResult) {
        InitiateController.getShared(context).initiateVerification(initiateEntity, initiateResponseResult);
    }

}

