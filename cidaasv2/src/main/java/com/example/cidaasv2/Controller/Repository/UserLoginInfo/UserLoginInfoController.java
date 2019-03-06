package com.example.cidaasv2.Controller.Repository.UserLoginInfo;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import com.example.cidaasv2.Service.Repository.LocationHistory.UserLoginInfoService;

import timber.log.Timber;

public class UserLoginInfoController {

    private Context context;
    private String ConsentName;
    private String ConsentVersion;
    private String TrackId;

    public static UserLoginInfoController shared;

    public UserLoginInfoController(Context contextFromCidaas) {

        context = contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static UserLoginInfoController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new UserLoginInfoController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void getUserLoginInfo(String baseurl, String accessToken, UserLoginInfoEntity locationHistoryEntity,
                                 Result<UserLoginInfoResponseEntity> locationHistoryResponseEntityResult)
    {
        if(baseurl!=null && baseurl!="" && accessToken!=null && accessToken!="") {
            UserLoginInfoService.getShared(context).getUserLoginInfoService(baseurl, accessToken, locationHistoryEntity,
                    null, locationHistoryResponseEntityResult);
        }
        else {
            locationHistoryResponseEntityResult.failure(WebAuthError.getShared(context).
                    customException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,"Base URL and Access Token must not be empty",
                            HttpStatusCode.EXPECTATION_FAILED));
        }

    }





}
