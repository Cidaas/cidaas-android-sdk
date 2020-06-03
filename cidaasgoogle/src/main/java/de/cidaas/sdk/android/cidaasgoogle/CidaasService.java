package de.cidaas.sdk.android.cidaasgoogle;

import android.content.Context;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.cidaas.sdk.android.cidaasgoogle.entity.GoogleSettingsEntity;
import de.cidaas.sdk.android.cidaasgoogle.interfaces.ICidaasGoogleService;
import de.cidaas.sdk.android.cidaasgoogle.interfaces.IGoogleAccessTokenEntity;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ganesh on 03/01/18.
 */

public class CidaasService {
    CidaasGoogleService cidaasGoogleService;
    Context globalContext;
    String base_url = "https://accounts.google.com/o/oauth2/token";
    String content_type = "application/x-www-form-urlencoded";

    public CidaasService(Context context) {
        if (cidaasGoogleService == null) {
            cidaasGoogleService = new CidaasGoogleService();

        }
        globalContext = context;
    }

    public void getGoogleAccessToken(GoogleSettingsEntity googleSettingsEntity, final IGoogleAccessTokenEntity iGoogleAccessTokenEntity) {
        final String methodName = "CidaasService:getGoogleAccessToken";
        ICidaasGoogleService iCidaasGoogleService = cidaasGoogleService.getAPIInstance();
        Map<String, String> queryMap = new HashMap<>();

        try {
            queryMap.put("grant_type", URLEncoder.encode(googleSettingsEntity.getGrantType(), "utf-8"));
            queryMap.put("client_id", URLEncoder.encode(googleSettingsEntity.getClientId(), "utf-8"));
            queryMap.put("client_secret", URLEncoder.encode(googleSettingsEntity.getClientSecret(), "utf-8"));
            queryMap.put("redirect_uri", URLEncoder.encode(googleSettingsEntity.getRedirectUrl(), "utf-8"));
            queryMap.put("code", URLEncoder.encode(googleSettingsEntity.getCode(), "utf-8"));
        } catch (Exception ex) {

        }

        iCidaasGoogleService.getGoogleAccessToken(base_url, content_type, queryMap).enqueue(new Callback<GoogleAccessTokenEntity>() {
            @Override
            public void onResponse(Call<GoogleAccessTokenEntity> call, Response<GoogleAccessTokenEntity> response) {
                if (response.isSuccessful()) {
                    iGoogleAccessTokenEntity.onSuccess(response.body());
                } else {
                    WebAuthError.getShared(globalContext).customException(WebAuthErrorCode.GOOGLE_ERROR, "Service failed:Bad response", methodName);
                }
            }

            @Override
            public void onFailure(Call<GoogleAccessTokenEntity> call, Throwable t) {
                WebAuthError.getShared(globalContext).customException(WebAuthErrorCode.GOOGLE_ERROR, t.getMessage(), methodName);

            }
        });

    }
}
