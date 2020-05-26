package de.cidaas.sdk.android.cidaas.Controller.Repository.UserLoginInfo;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Controller.Repository.AccessToken.AccessTokenController;
import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Service.Entity.AccessToken.AccessTokenEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Repository.LocationHistory.UserLoginInfoService;

import java.util.Dictionary;

import timber.log.Timber;

public class UserLoginInfoController {

    private Context context;
    private String ConsentName;
    private String ConsentVersion;
    private String TrackId;

    public static UserLoginInfoController shared;

    public UserLoginInfoController(Context contextFromCidaas) {

        context = contextFromCidaas;

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

    public void getUserLoginInfo(final UserLoginInfoEntity userLoginInfoEntity, final Result<UserLoginInfoResponseEntity> result) {
        final String methodName = "UserLoginInfoController :getUserLoginInfo()";
        try {
            if (userLoginInfoEntity.getSub() != null && !userLoginInfoEntity.getSub().equals("")) {
                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(final Dictionary<String, String> lpresult) {
                        final String baseurl = lpresult.get("DomainURL");

                        //Get AccessToken From Sub
                        AccessTokenController.getShared(context).getAccessToken(userLoginInfoEntity.getSub(), new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {
                                getUserLoginInfo(baseurl, accessTokenresult.getAccess_token(), userLoginInfoEntity, result);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException(error.getErrorMessage(), "Error:" + methodName));
                    }
                });
            } else {
                // handle Faliure
                result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be empty", "Error:" + methodName));
            }
        } catch (Exception e) {
            // handle Faliure Exception
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE, e.getMessage()));
        }
    }

    public void getUserLoginInfo(String baseurl, String accessToken, UserLoginInfoEntity locationHistoryEntity,
                                 Result<UserLoginInfoResponseEntity> locationHistoryResponseEntityResult) {
        String methodName = "UserLoginInfoController :getUserLoginInfo()";
        if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("")) {
            UserLoginInfoService.getShared(context).getUserLoginInfoService(baseurl, accessToken, locationHistoryEntity, locationHistoryResponseEntityResult);
        } else {
            locationHistoryResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("Base URL and Access Token must not be empty",
                    "Error" + methodName));
        }

    }
}
