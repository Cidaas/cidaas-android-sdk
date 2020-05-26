package de.cidaas.sdk.android.cidaas.Interface;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Service.Entity.AccessToken.AccessTokenEntity;

public interface ICidaasFacebook {

    public void login(Result<AccessTokenEntity> accessTokenEntityResult);

    public void logout();
}
