package com.example.cidaasv2.Helper.Extension;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Interface.IOAuthExcepiton;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.R;

import timber.log.Timber;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class  WebAuthError extends Error implements IOAuthExcepiton{
   public int errorCode=WebAuthErrorCode.DEFAULT;
   public int statusCode=400;
   private Context context;
   public String ErrorMessage="";
    public String DetailedErrorMessage="";
   public ErrorEntity errorEntity;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
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
   public static WebAuthError shared;



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
    public WebAuthError fileNotFoundException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.FILE_NOT_FOUND;
        WebAuthError.shared.statusCode= HttpStatusCode.NOT_FOUND;
        WebAuthError.shared.ErrorMessage= context.getString(R.string.FILE_NOT_FOUND);
        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode+"");
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.FILE_NOT_FOUND));

        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }
//NoContentinFileException
    @Override
    public WebAuthError noContentInFileException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.NO_CONTENT_IN_FILE;
        WebAuthError.shared.statusCode=HttpStatusCode.NO_CONTENT;
        WebAuthError.shared.setErrorMessage(context.getString(R.string.NO_CONTENT_IN_FILE));
        return WebAuthError.shared;
    }
//PropertyMissingException
    @Override
    public WebAuthError propertyMissingException(String errorDetails) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.PROPERTY_MISSING;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.PROPERTY_MISSING);
        WebAuthError.shared.DetailedErrorMessage=errorDetails;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode+"");
        errorEntity.setStatus(statusCode);
        errorEntity.setError(context.getString(R.string.PROPERTY_MISSING));

        WebAuthError.shared.setErrorEntity(errorEntity);

        return WebAuthError.shared;
    }

 //Access Token Exception
 //PropertyMissingException
 @Override
 public WebAuthError accessTokenException(String errorDetails) {

     WebAuthError.shared.errorCode=WebAuthErrorCode.ACCESS_TOKEN_CONVERSION_FAILURE;
     WebAuthError.shared.statusCode=HttpStatusCode.INTERNAL_SERVER_ERROR;
     WebAuthError.shared.ErrorMessage=context.getString(R.string.ACCESS_TOKEN_SERVICE_FAILURE);
     WebAuthError.shared.DetailedErrorMessage=errorDetails;

     ErrorEntity errorEntity=new ErrorEntity();
     errorEntity.setCode(errorCode+"");
     errorEntity.setStatus(statusCode);
     errorEntity.setError(context.getString(R.string.ACCESS_TOKEN_SERVICE_FAILURE));

     WebAuthError.shared.setErrorEntity(errorEntity);

     return WebAuthError.shared;
 }
//Service FailureException
    @Override
    public WebAuthError serviceFailureException(int errorCode, String errorMessage, int StatusCode,Object error,ErrorEntity errorEntity) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=StatusCode;
        WebAuthError.shared.ErrorMessage=errorMessage;
        WebAuthError.shared.error=error;
        WebAuthError.shared.errorEntity=errorEntity;
        return WebAuthError.shared;
    }


    public WebAuthError serviceException(int errorCode)
    {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.setErrorMessage(context.getString(R.string.SERVICE_EXCEPTION));
        return WebAuthError.shared;
    }

 //Location History Failure Exception
 @Override
 public WebAuthError locationHistoryException() {

     WebAuthError.shared.errorCode=WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE;
     WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
     WebAuthError.shared.ErrorMessage=context.getString(R.string.LOCATION_HISTORY_FAILURE);
     return WebAuthError.shared;
 }

//LoginUrl MissingException
    @Override
    public WebAuthError loginURLMissingException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_LOGIN_URL;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_LOGIN_URL);
        return WebAuthError.shared;
    }
//RedirectUrl Missing Exception
    @Override
    public WebAuthError redirectURLMissingException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_REDIRECT_URL;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_REDIRECT_URL);
        return WebAuthError.shared;
    }
//Usercancelled Exception
    @Override
    public WebAuthError userCancelledException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.USER_CANCELLED_LOGIN;
        WebAuthError.shared.statusCode=HttpStatusCode.CANCEL_REQUEST;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.USER_CANCELLED_LOGIN);
        return WebAuthError.shared;
    }
//CodenotFoundException
    @Override
    public WebAuthError codeNotFoundException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.CODE_NOT_FOUND;
        WebAuthError.shared.statusCode=HttpStatusCode.NO_CONTENT;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.CODE_NOT_FOUND);
        return WebAuthError.shared;
    }
//EmptyCallbackException
    @Override
    public WebAuthError emptyCallbackException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.EMPTY_CALLBACK;
        WebAuthError.shared.statusCode=HttpStatusCode.BAD_REQUEST;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.EMPTY_CALLBACK);
        return WebAuthError.shared;
    }
//NouserFoundException
    @Override
    public WebAuthError noUserFoundException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.NO_USER_FOUND;
        WebAuthError.shared.statusCode=HttpStatusCode.NOT_FOUND;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.NO_USER_FOUND);
        return WebAuthError.shared;
    }

    //Device Failed to Verify
    public WebAuthError deviceVerificationFailureException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.DEVICE_VERIFICATION_FAILURE);

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE+"");
        errorEntity.setStatus(HttpStatusCode.EXPECTATION_FAILED);
        errorEntity.setError(context.getString(R.string.DEVICE_VERIFICATION_FAILURE));

        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }

    //Custom Exception
    public WebAuthError customException(int errorCode, String errorMessage, int StatusCode) {

        WebAuthError.shared.errorCode=errorCode;
        WebAuthError.shared.statusCode=StatusCode;
        WebAuthError.shared.ErrorMessage=errorMessage;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode+"");
        errorEntity.setStatus(statusCode);
        errorEntity.setError(errorMessage);


        WebAuthError.shared.setErrorEntity(errorEntity);
        return WebAuthError.shared;
    }



    //Custom Exception
    public WebAuthError fingerPrintException(String errorMessage) {

        WebAuthError.shared.errorCode=WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED;
        WebAuthError.shared.statusCode=417;
        WebAuthError.shared.ErrorMessage=errorMessage;
        return WebAuthError.shared;
    }


    //Custom Exception
    public WebAuthError fingerPrintError(int ErrorCode,String errorMessage) {

        WebAuthError.shared.errorCode=ErrorCode;
        WebAuthError.shared.statusCode=417;
        WebAuthError.shared.ErrorMessage=errorMessage;

        ErrorEntity errorEntity=new ErrorEntity();
        errorEntity.setCode(errorCode+"");
        errorEntity.setStatus(statusCode);
        errorEntity.setError(errorMessage);

        WebAuthError.shared.setErrorEntity(errorEntity);

        return WebAuthError.shared;
    }

    //Facebook oncancel Exception
    public WebAuthError facebookOnCancelException() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.ON_CANCEL_FACEBOOK;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.USER_CANCELLED_LOGIN);
        return WebAuthError.shared;
    }


    //Google Error Exception
    public WebAuthError googleError() {

        WebAuthError.shared.errorCode=WebAuthErrorCode.GOOGLE_ERROR;
        WebAuthError.shared.statusCode=HttpStatusCode.EXPECTATION_FAILED;
        WebAuthError.shared.ErrorMessage=context.getString(R.string.GOOGLE_SIGNIN_ERROR);
        return WebAuthError.shared;
    }
}
