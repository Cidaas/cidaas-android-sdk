package de.cidaas.sdk.android.interfaces;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;

public interface ICidaasGoogle {
    public void login(EventResult<AccessTokenEntity> accessTokenEntityResult);

    public void logout();
}
