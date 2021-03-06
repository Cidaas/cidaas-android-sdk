package de.cidaas.sdk.android.cidaasgoogle;


import de.cidaas.sdk.android.cidaasgoogle.interfaces.ICidaasGoogleService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ganesh on 03/01/18.
 */

public class CidaasGoogleService {
    public ICidaasGoogleService getAPIInstance() {
        ICidaasGoogleService iCidaasGoogleService = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://accounts.google.com/o/oauth2/token/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        iCidaasGoogleService = retrofit.create(ICidaasGoogleService.class);
        return iCidaasGoogleService;
    }
}