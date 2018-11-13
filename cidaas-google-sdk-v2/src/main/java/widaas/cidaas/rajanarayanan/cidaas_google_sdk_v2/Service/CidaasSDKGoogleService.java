package widaas.cidaas.rajanarayanan.cidaas_google_sdk_v2.Service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CidaasSDKGoogleService {
    public ICidaasGoogleSDKService getAPIInstance() {
        ICidaasGoogleSDKService iCidaasGoogleService = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://accounts.google.com/o/oauth2/token/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        iCidaasGoogleService = retrofit.create(ICidaasGoogleSDKService.class);
        return iCidaasGoogleService;
    }
}
