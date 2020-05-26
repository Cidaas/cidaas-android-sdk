package de.cidaas.sdk.android.cidaas.Interface;

import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface IOAuthExcepiton {
    public WebAuthError fileNotFoundException(String methodName);

    public WebAuthError noContentInFileException(String methodName);

    public WebAuthError propertyMissingException(String errorDetails, String methodName);

    public WebAuthError serviceCallFailureException(int errorCode, String errorMessage, String methodName);

    public WebAuthError loginURLMissingException(String methodName);

    public WebAuthError redirectURLMissingException(String methodName);

    public WebAuthError userCancelledException(String methodName);

    public WebAuthError codeNotFoundException(String methodName);

    public WebAuthError emptyCallbackException(String methodName);

    public WebAuthError noUserFoundException(String methodName);

    public WebAuthError locationHistoryException(String methodName);

    public WebAuthError accessTokenException(String errorDetails, String methodName);
}
