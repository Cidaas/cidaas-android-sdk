package de.cidaas.sdk.android.cidaasgoogle.Interface;

import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import de.cidaas.sdk.android.cidaasgoogle.GoogleAccessTokenEntity;

/**
 * Created by ganesh on 04/01/18.
 */

public interface IGoogleAccessTokenEntity {
    public void onSuccess(GoogleAccessTokenEntity googleAccessTokenEntity);

    public void onError(WebAuthError errorEntity);
}
