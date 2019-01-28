package widaas.cidaas.rajanarayanan.cidaasgooglev2.Interface;

import com.example.cidaasv2.Helper.Extension.WebAuthError;

import widaas.cidaas.rajanarayanan.cidaasgooglev2.GoogleAccessTokenEntity;

/**
 * Created by ganesh on 04/01/18.
 */

public interface IGoogleAccessTokenEntity {
    public void onSuccess(GoogleAccessTokenEntity googleAccessTokenEntity);
    public void onError(WebAuthError errorEntity);
}
