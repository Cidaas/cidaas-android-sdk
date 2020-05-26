package de.cidaas.sdk.android.cidaas.Helper.Enums;

import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface Result<T> {
    public void success(T result);

    public void failure(WebAuthError error);

}
