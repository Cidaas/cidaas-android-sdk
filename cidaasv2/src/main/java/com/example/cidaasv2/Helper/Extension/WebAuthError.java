package com.example.cidaasv2.Helper.Extension;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Interface.IOAuthExcepiton;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.R;

import timber.log.Timber;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class  WebAuthError extends Error implements IOAuthExcepiton{

   private int errorCode=WebAuthErrorCode.DEFAULT;
   private int statusCode=400;
   private Context context;
   private String ErrorMessage="";
   private String DetailedErrorMessage="";
   private ErrorEntity errorEntity;
    private String jsonMessage="";

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }


    public String getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getDetailedErrorMessage() {
        return DetailedErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public ErrorEntity getErrorEntity() {
        return errorEntity;
    }

    public void setErrorEntity(ErrorEntity errorEntity) {
        this.errorEntity = errorEntity;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object error;
   private static WebAuthError shared;



    public static WebAuthError getShared(Context contextFromCidaas)
   {
      try {

          if (shared == null) {
              shared = new WebAuthError(contextFromCidaas);
          }
      }
      catch (Exception e)
      {
          Timber.i(e.getMessage());
      }
       return shared;
   }

   public WebAuthError(Context contextFromCidaas)
   {

       context=contextFromCidaas;
   }


   //File NotFound Exception
    @Override
    public WebAuthError fileNotFoundException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.FILE_NOT_FOUND;
        WebAuthError.shared.statusCode= HttpStatusCode.NOT_FOUND;
        WebAuthError.shared.ErrorMessage= context.getString(R.string.FILE_NOT_FOUND);
        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.FILE_NOT_FOUND));

        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }
//NoContentinFileException
    @Override
    public WebAuthError noContentInFileException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.NO_CONTENT_IN_FILE;
        WebAuthError.shared.statusCode=HttpStatusCode.NO_CONTENT;
        WebAuthError.shared.setErrorMessage(context.getString(R.string.NO_CONTENT_IN_FILE));
        return WebAuthError.shared;
    }
//PropertyMissingException
    @Override
    public WebAuthError propertyMissingException(String errorDetails,String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.PROPERTY_MISSING;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.PROPERTY_MISSING);
        WebAuthError.shared.DetailedErrorMessage=errorDetails;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.PROPERTY_MISSING));

        String loggerMessage = "Property Missing Error :-"+methodName+"ErrorCode :- "+ WebAuthError.shared.errorCode+" Error Message:- " +errorDetails;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);

        return WebAuthError.shared;
    }

    //CidaasPropertyMissingException
    public WebAuthError CidaaspropertyMissingException(String errorDetails,String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.CIDAAS_PROPERTY_MISSING;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.CIDAAS_PROPERTY_MISSING);
        WebAuthError.shared.DetailedErrorMessage=errorDetails;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.CIDAAS_PROPERTY_MISSING));

        String loggerMessage = "Property Missing Error :-"+methodName+"ErrorCode :- "+ WebAuthError.shared.errorCode+" Error Message:- " +errorDetails;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);

        return WebAuthError.shared;
    }

 //Access Token Exception
 @Override
 public WebAuthError accessTokenException(String errorDetails,String methodName) {

     WebAuthError.shared.errorCode=WebAuthErrorCode.ACCESS_TOKEN_CONVERSION_FAILURE;
     WebAuthError.shared.statusCode=HttpStatusCode.INTERNAL_SERVER_ERROR;
     WebAuthError.shared.ErrorMessage=context.getString(R.string.ACCESS_TOKEN_SERVICE_FAILURE);
     WebAuthError.shared.DetailedErrorMessage=errorDetails;

     ErrorEntity errorEntity=new ErrorEntity();
     errorEntity.setCode(errorCode);
     errorEntity.setStatus(statusCode);
     errorEntity.setError(context.getString(R.string.ACCESS_TOKEN_SERVICE_FAILURE));

     WebAuthError.shared.setErrorEntity(errorEntity);

     return WebAuthError.shared;
 }
//Service FailureException
    @Override
    public WebAuthError serviceCallFailureException(int errorCode, String errorMessage, String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=400;
        WebAuthError.shared.ErrorMessage=errorMessage;



        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +errorMessage+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;


    }


    //Service FailureException

    public WebAuthError serviceCallException(int errorCode, String errorMessage,int statusCode,ErrorEntity errorEntity,String errorResponse, String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=statusCode;
        WebAuthError.shared.ErrorMessage=errorMessage;
        WebAuthError.shared.jsonMessage=errorResponse;


        WebAuthError.shared.setErrorEntity(errorEntity);

        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +errorMessage+"StatusCode:- "+statusCode+
                "ErrorResponse From Server:"+jsonMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;


    }



    public WebAuthError loginFailureException(int errorCode,String errorMessage,int statusCode,Object error,String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=statusCode;
        WebAuthError.shared.ErrorMessage=errorMessage;
        WebAuthError.shared.error=error;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(errorMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);


        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +errorMessage+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;


    }

    //FCM TOKEN FAILURE
    public WebAuthError FCMTokenFailure(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.UPDATE_FCM_TOKEN;
        WebAuthError.shared.statusCode= HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage= context.getString(R.string.UPDATE_FCM_TOKEN_FAILURE);
        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.UPDATE_FCM_TOKEN_FAILURE));

        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +" Empty response"+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }


    public WebAuthError emptyResponseException(int errorCode, int statusCode, String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=statusCode;
        WebAuthError.shared.ErrorMessage="Empty response";


        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +" Empty response"+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;

    }


    public WebAuthError loginWithBrowserFailureException(int errorCode, String errorMessage, String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.ErrorMessage=errorMessage;


        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +" Empty response"+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;

    }

    public WebAuthError methodException(String methodName, int errorCode, String errorMessage)
    {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.setErrorMessage(context.getString(R.string.SERVICE_EXCEPTION));

        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        Timber.d(loggerMessage);

        return WebAuthError.shared;


    }

 //Location History Failure Exception
 @Override
 public WebAuthError locationHistoryException(String methodName) {

     WebAuthError.shared.errorCode=WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE;
     WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
     WebAuthError.shared.ErrorMessage=context.getString(R.string.LOCATION_HISTORY_FAILURE);
     return WebAuthError.shared;
 }

//LoginUrl MissingException
    @Override
    public WebAuthError loginURLMissingException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_LOGIN_URL;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_LOGIN_URL);
        return WebAuthError.shared;
    }
//RedirectUrl Missing Exception
    @Override
    public WebAuthError redirectURLMissingException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_REDIRECT_URL;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_REDIRECT_URL);
        return WebAuthError.shared;
    }
//Usercancelled Exception
    @Override
    public WebAuthError userCancelledException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.USER_CANCELLED_LOGIN;
        WebAuthError.shared.statusCode=HttpStatusCode.CANCEL_REQUEST;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.USER_CANCELLED_LOGIN);
        return WebAuthError.shared;
    }
//CodenotFoundException
    @Override
    public WebAuthError codeNotFoundException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.CODE_NOT_FOUND;
        WebAuthError.shared.statusCode=HttpStatusCode.NO_CONTENT;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.CODE_NOT_FOUND);
        return WebAuthError.shared;
    }
//EmptyCallbackException
    @Override
    public WebAuthError emptyCallbackException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_CALLBACK;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_CALLBACK);
        return WebAuthError.shared;
    }
//NouserFoundException
    @Override
    public WebAuthError noUserFoundException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.NO_USER_FOUND;
        WebAuthError.shared.statusCode=HttpStatusCode.NOT_FOUND;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.NO_USER_FOUND);
        return WebAuthError.shared;
    }

    //Device Failed to Verify
    public WebAuthError deviceVerificationFailureException(String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.DEVICE_VERIFICATION_FAILURE);

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE);
        errorEntity.setStatus(HttpStatusCode.EXPECTATION_FAILED);
        errorEntity.setError(context.getString(R.string.DEVICE_VERIFICATION_FAILURE));

        String loggerMessage = "Device Verification Failure Exception:-"+methodName+" ErrorCode :- "+ WebAuthError.shared.errorCode
                +" Error Message:- " +errorEntity.getError();
        LogFile.getShared(context).addFailureLog(loggerMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }

    //Custom Exception
    public WebAuthError customException(int errorCode, String errorMessage, String methodName) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.ErrorMessage=errorMessage;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(errorMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);

        String loggerMessage = "CustomException:-"+methodName+" ErrorCode :- "+errorCode+" "+ "Error Message:- " +errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        return WebAuthError.shared;
    }

    //Custom Exception
    public WebAuthError fingerPrintException(String errorMessage,String methodName) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED;
        WebAuthError.shared.statusCode=417;
        WebAuthError.shared.ErrorMessage=errorMessage;

        String loggerMessage = "Finger print Exception:-"+methodName+" ErrorCode :- "+WebAuthError.shared.errorCode+" Error Message:- " +errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        return WebAuthError.shared;
    }


    //Custom Exception
    public WebAuthError fingerPrintError(int ErrorCode,String errorMessage,String methodName) {

        WebAuthError.shared.errorCode=ErrorCode;
        WebAuthError.shared.statusCode=417;
        WebAuthError.shared.ErrorMessage=errorMessage;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode);
        errorEntity.setStatus(statusCode);
        errorEntity.setError(errorMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);

        String loggerMessage = "Finger print Error:-"+methodName+" ErrorCode :- "+ErrorCode+" "+ "Error Message:- " +errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        return WebAuthError.shared;
    }

    //Facebook oncancel Exception
    public WebAuthError facebookOnCancelException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.ON_CANCEL_FACEBOOK;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.USER_CANCELLED_LOGIN);

        String loggerMessage = "Facebook OnCancel Exception :- ErrorCode :- "+errorCode+" Error Message:- " + WebAuthError.shared.ErrorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        return WebAuthError.shared;
    }


    //Google Error Exception
    public WebAuthError googleError() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.GOOGLE_ERROR;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.GOOGLE_SIGNIN_ERROR);

        String loggerMessage = "Google Signin error:- ErrorCode :- "+errorCode+" Error Message:- " + WebAuthError.shared.ErrorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);

        return WebAuthError.shared;
    }


    //unauthorized Access
    public WebAuthError unAuthorizedAccess(int errorCode,String errorMessage,String methodName)
    {
        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=401;
        WebAuthError.shared.ErrorMessage=errorMessage;


        String loggerMessage = methodName+" :- "+"ErrorCode : "+errorCode+" "+ "Error Message - " +" Empty response"+"StatusCode:- "+statusCode;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        Timber.d(loggerMessage);


        return WebAuthError.shared;
    }

}
