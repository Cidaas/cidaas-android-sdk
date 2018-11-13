package widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Repository;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Helper.GoogleResult;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.CidaasSDKGoogleService;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Entity.GoogleAccessTokenEntity;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.Entity.GoogleSettingsEntity;
import widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service.ICidaasGoogleSDKService;

public class CidaasService {

Context context;

    CidaasSDKGoogleService cidaasGoogleService;
    private ObjectMapper objectMapper=new ObjectMapper();

    String base_url = "https://accounts.google.com/o/oauth2/token";
    String content_type = "application/x-www-form-urlencoded";

    public CidaasService(Context contextFromCidaas) {
        if (cidaasGoogleService == null) {
            cidaasGoogleService = new CidaasSDKGoogleService();
        }

        context=contextFromCidaas;
    }


    public void getGoogleAccessToken(GoogleSettingsEntity googleSettingsEntity, final GoogleResult<GoogleAccessTokenEntity> callback) {

        ICidaasGoogleSDKService iCidaasGoogleService = cidaasGoogleService.getAPIInstance();

        Map<String, String> queryMap = new HashMap<>();

        try {
            queryMap.put("grant_type", URLEncoder.encode(googleSettingsEntity.getGrantType(), "utf-8"));
            queryMap.put("client_id", URLEncoder.encode(googleSettingsEntity.getClientId(), "utf-8"));
            queryMap.put("client_secret", URLEncoder.encode(googleSettingsEntity.getClientSecret(), "utf-8"));
            queryMap.put("redirect_uri", URLEncoder.encode(googleSettingsEntity.getRedirectUrl(), "utf-8"));
            queryMap.put("code", URLEncoder.encode(googleSettingsEntity.getCode(), "utf-8"));
        }
        catch (Exception ex) {

        }


        iCidaasGoogleService.getGoogleAccessToken(base_url,content_type,queryMap).enqueue(new Callback<GoogleAccessTokenEntity>() {
            @Override
            public void onResponse(Call<GoogleAccessTokenEntity> call, Response<GoogleAccessTokenEntity> response) {
                if (response.isSuccessful()) {
                    if(response.code()==200) {
                        callback.success(response.body());
                    }
                    else {
                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                "Service failure but successful response" , 400,null,null));
                    }
                }
                else {
                    assert response.errorBody() != null;
                    try {

                        //Todo Handle proper error message

                        String errorResponse=response.errorBody().source().readByteString().utf8();

                        CommonErrorEntity commonErrorEntity;
                        ErrorEntity errorEntity=new ErrorEntity();
                        commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);
                        String errorMessage="";
                        if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                            errorMessage=commonErrorEntity.getError().toString();
                        }
                        else
                        {
                            errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                            errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                            errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                            errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                            errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                            errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                        }


                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                errorMessage, commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("response"+response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleAccessTokenEntity> call, Throwable t) {
                Timber.e("Faliure in Request id service call"+t.getMessage());
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,t.getMessage(), 400,null,null));

            }
        });

    }




}
