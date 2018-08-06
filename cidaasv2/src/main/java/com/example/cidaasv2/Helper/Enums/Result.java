package com.example.cidaasv2.Helper.Enums;

import com.example.cidaasv2.Helper.Extension.WebAuthError;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface Result<T> {
    public void success(T result);
    public void failure(WebAuthError error);

}
