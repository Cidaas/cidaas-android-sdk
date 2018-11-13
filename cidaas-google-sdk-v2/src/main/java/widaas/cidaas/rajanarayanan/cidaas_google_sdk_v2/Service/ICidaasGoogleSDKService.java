package widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Entity.GoogleAccessTokenEntity;

public interface ICidaasGoogleSDKService {

    @FormUrlEncoded
    @POST
    Call<GoogleAccessTokenEntity> getGoogleAccessToken(@Url String url,
                                                       @Header("Content-Type") String content_type,
                                                       @FieldMap(encoded = true) Map<String, String> params);
}
