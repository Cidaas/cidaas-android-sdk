package de.cidaas.sdk.android.cidaas.Service.HelperForService.Chekcing;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import timber.log.Timber;

public class CheckNotNull {

    private static CheckNotNull shared;

    public ObjectMapper objectMapper = new ObjectMapper();

    private Context context;
    Map<String, String> headers = new Hashtable<>();

    public CheckNotNull(Context context) {
        this.context = context;
    }

    public static CheckNotNull getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new CheckNotNull(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

}
