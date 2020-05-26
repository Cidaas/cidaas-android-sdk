package de.cidaas.sdk.android.interfaces;

import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;

public interface ICidaasGoogle {
    public void login(Result<AccessTokenEntity> accessTokenEntityResult);

    public void logout();
}
