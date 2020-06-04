package de.cidaas.sdk.android.cidaasverification.domain.interactor;

import android.content.Context;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.logger.LogFile;
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

    public void handleResponse(Response response, EventResult<T> result) {

    }
}
