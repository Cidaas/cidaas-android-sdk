package com.example.cidaasv2.Interface;

import com.example.cidaasv2.Helper.Extension.WebAuthError;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface IOAuthExcepiton {
    public WebAuthError fileNotFoundException();
    public WebAuthError noContentInFileException();
    public WebAuthError propertyMissingException();
    public WebAuthError serviceFailureException(int errorCode, String errorMessage,int StatusCode,Object error);
    public WebAuthError loginURLMissingException();
    public WebAuthError redirectURLMissingException();
    public WebAuthError userCancelledException();
    public WebAuthError codeNotFoundException();
    public WebAuthError emptyCallbackException();
    public  WebAuthError noUserFoundException();
}
