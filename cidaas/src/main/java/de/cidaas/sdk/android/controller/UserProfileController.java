package de.cidaas.sdk.android.controller;

import android.content.Context;

import java.util.Dictionary;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasConstants;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.entity.UserInfo.UserInfoEntity;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.repository.OauthService;
import timber.log.Timber;

public class UserProfileController {


    private Context context;

    public static UserProfileController shared;

    public UserProfileController(Context contextFromCidaas) {

        context = contextFromCidaas;

    }

    public static UserProfileController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new UserProfileController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void getUserProfile(String sub, final EventResult<UserInfoEntity> callback) {
        String methodName = "UserProfileController :getUserProfile()";
        try {
            if (sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new EventResult<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> result) {
                                OauthService.getShared(context).getUserinfo(accessTokenresult.getAccess_token(), result.get("DomainURL"), callback);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                callback.failure(error);
                            }
                        });

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callback.failure(error);
                    }
                });

            } else {
                String errorMessage = "Sub must not be null";
                callback.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, CidaasConstants.ERROR_LOGGING_PREFIX + methodName));
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(CidaasConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.USER_INFO_SERVICE_FAILURE, e.getMessage()));
        }
    }


    public void getUserConfigurationList() {
        try {

        } catch (Exception e) {

        }
    }

}
