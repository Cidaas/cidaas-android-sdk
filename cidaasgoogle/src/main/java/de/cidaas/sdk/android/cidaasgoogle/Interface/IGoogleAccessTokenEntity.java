package de.cidaas.sdk.android.cidaasgoogle.Interface;


import de.cidaas.sdk.android.cidaasgoogle.GoogleAccessTokenEntity;
import de.cidaas.sdk.android.helper.extension.WebAuthError;

/**
 * Created by ganesh on 04/01/18.
 */

public interface IGoogleAccessTokenEntity {
    public void onSuccess(GoogleAccessTokenEntity googleAccessTokenEntity);

    public void onError(WebAuthError errorEntity);
}
