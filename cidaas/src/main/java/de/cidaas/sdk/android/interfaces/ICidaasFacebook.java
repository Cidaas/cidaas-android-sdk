package de.cidaas.sdk.android.interfaces;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;

public interface ICidaasFacebook {

    public void login(String equestId, EventResult<AccessTokenEntity> accessTokenEntityResult);

    public void logout();
}
