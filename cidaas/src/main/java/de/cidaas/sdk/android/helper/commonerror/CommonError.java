package de.cidaas.sdk.android.helper.commonerror;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.cidaas.sdk.android.entities.CommonErrorEntity;
import de.cidaas.sdk.android.helper.enums.HttpStatusCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Response;


public class CommonError {

    public static CommonError shared;
    private Context context;


    private ObjectMapper objectMapper = new ObjectMapper();

    public CommonError(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static CommonError getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new CommonError(contextFromCidaas);
            }
        } catch (Exception e) {
            // Timber.i(e.getMessage());
        }
        return shared;
    }


    //Method to get Error Entity
    public WebAuthError generateCommonErrorEntity(int webAuthErrorCode, Response response, String methodName) {
        try {

            assert response.errorBody() != null;

            response.message().toLowerCase();

            // Handle proper error message
            String errorResponse = response.errorBody().source().readByteString().utf8();


            if (errorResponse.contains("<!DOCTYPE html>")) {
                return WebAuthError.getShared(context).emptyResponseException(webAuthErrorCode, HttpStatusCode.NOT_FOUND, methodName);
            }

            if (errorResponse.contains("invalid_request: given url is not allowed by the application configuration.")) {
                String error_message = "Configured redirect url for your application in cidaas is not allowed by the application configuration. " +
                        "Please update your application configuration in cidaas to include the redirect url";
                return WebAuthError.getShared(context).customException(webAuthErrorCode, error_message, methodName);
            }


            final CommonErrorEntity commonErrorEntity;
            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

            LogFile.getShared(context).addFailureLog("Error:- WebAuthErrorCode: " + webAuthErrorCode + " Response Message:- " + response.message() +
                    " ErrorCode:- " + commonErrorEntity.getError().getCode() + "error message:-" + commonErrorEntity.getError().getError());


            return WebAuthError.getShared(context).serviceCallException(webAuthErrorCode, commonErrorEntity.getError().getError(), commonErrorEntity.getStatus(),
                    commonErrorEntity.getError(), errorResponse, methodName);
        } catch (Exception e) {

            return WebAuthError.getShared(context).methodException("Exception :CommonError :generateCommonErrorEntity()", webAuthErrorCode, e.getMessage());

        }
    }
}
