package de.cidaas.sdk.android.cidaasgoogle.Interface;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import de.cidaas.sdk.android.cidaasgoogle.GoogleAccessTokenEntity;

/**
 * Created by ganesh on 15/06/17.
 */

public interface ICidaasGoogleService {
    @FormUrlEncoded
    @POST
    Call<GoogleAccessTokenEntity> getGoogleAccessToken(@Url String url,
                                                       @Header("Content-Type") String content_type,
                                                       @FieldMap(encoded = true) Map<String, String> params);
}
