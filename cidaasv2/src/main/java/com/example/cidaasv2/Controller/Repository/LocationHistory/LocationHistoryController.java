package com.example.cidaasv2.Controller.Repository.LocationHistory;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LocationHistory.LocationHistoryEntity;
import com.example.cidaasv2.Service.Entity.LocationHistory.LocationHistoryRequestEntity;
import com.example.cidaasv2.Service.Entity.LocationHistory.LocationHistoryResponseEntity;
import com.example.cidaasv2.Service.Repository.LocationHistory.LocationHistoryService;

import timber.log.Timber;

public class LocationHistoryController {

    private Context context;
    private String ConsentName;
    private String ConsentVersion;
    private String TrackId;

    public static LocationHistoryController shared;

    public LocationHistoryController(Context contextFromCidaas) {

        context = contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LocationHistoryController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new LocationHistoryController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void getLocationHistoryDetails(String baseurl, String accessToken, LocationHistoryRequestEntity locationHistoryEntity,
                                          Result<LocationHistoryResponseEntity> locationHistoryResponseEntityResult)
    {
        if(baseurl!=null && baseurl!="" && accessToken!=null && accessToken!="") {
            LocationHistoryService.getShared(context).getLocationHistoryService(baseurl, accessToken, locationHistoryEntity,
                    null, locationHistoryResponseEntityResult);
        }
        else {
            locationHistoryResponseEntityResult.failure(WebAuthError.getShared(context).
                    customException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,"Base URL and Access Token must not be empty",
                            HttpStatusCode.EXPECTATION_FAILED));
        }

    }





}
