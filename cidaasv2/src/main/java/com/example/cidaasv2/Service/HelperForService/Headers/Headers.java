package com.example.cidaasv2.Service.HelperForService.Headers;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;

import java.util.Hashtable;
import java.util.Map;

import timber.log.Timber;

public class Headers {


    private static Headers shared;



    private Context context;


    public static Headers getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new Headers(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public Headers(Context contextFromCidaas) {

        context = contextFromCidaas;

    }

    public Map<String, String> getHeaders(String accessToken,boolean verification_api_version,String contentType,String... requestId)
    {
        Map<String, String> headers = new Hashtable<>();
        try
        {

            if(accessToken!=null && !accessToken.equals(""))
            {
                headers.put("access_token", accessToken);
            }

            if(verification_api_version)
            {
                headers.put("verification_api_version","2");
            }

            if(contentType!=null && !contentType.equals(""))
            {
                headers.put("Content-Type",contentType);
            }

            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            if(deviceInfoEntity!=null) {
                headers.put("deviceId", deviceInfoEntity.getDeviceId());
                headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
                headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
                headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());
            }


            if(requestId!=null && requestId.length>0)
            {
                headers.put("requestId",requestId[0]);
            }



            return headers;
        }
        catch (Exception e)
        {
            return headers;
        }
    }
}
