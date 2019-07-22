package widas.raja.cidaasconsentv2.Domain.Service;

import android.content.Context;

import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.ICidaasSDKService;

import okhttp3.OkHttpClient;
import widas.raja.cidaasconsentv2.data.Interface.ICidaasConsentSDKService;

public class CidaasConsentSDKService {


    private static Context mcontext;

    public CidaasConsentSDKService(Context context) {
        setContext(context);
    }

    private void setContext(Context context)
    {
        mcontext=context;
    }

    //For Cidaas Core Service
    public ICidaasConsentSDKService getInstance()
    {

        String baseurl= CidaasHelper.baseurl;

        if(baseurl==null || baseurl.equals(""))
        {
            baseurl="https://www.google.com";
        }

        CidaassdkService cidaassdkService=new CidaassdkService(mcontext);
        OkHttpClient okHttpClient= cidaassdkService.getOKHttpClient();

        ICidaasConsentSDKService iCidaasConsentSDKService=cidaassdkService.getRetrofit(baseurl, okHttpClient).create(ICidaasConsentSDKService.class);
        return iCidaasConsentSDKService;
    }


}
