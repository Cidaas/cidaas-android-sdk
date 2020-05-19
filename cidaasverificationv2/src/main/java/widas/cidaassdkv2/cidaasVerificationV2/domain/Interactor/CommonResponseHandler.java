package widas.cidaassdkv2.cidaasVerificationV2.domain.Interactor;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Logger.LogFile;

import okhttp3.Response;

public class CommonResponseHandler<T> {
    //Local Variables
    private Context context;


    public static CommonResponseHandler shared;

    public CommonResponseHandler(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static CommonResponseHandler getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new CommonResponseHandler(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("CommonResponseHandler instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    public void handleResponse(Response response, Result<T> result)
    {

    }
}
