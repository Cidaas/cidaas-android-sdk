package de.cidaas.sdk.android.helper.enums;


import de.cidaas.sdk.android.helper.extension.WebAuthError;

public interface EventResult<T> {
    public void success(T result);

    public void failure(WebAuthError error);

}
