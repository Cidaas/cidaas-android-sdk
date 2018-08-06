package com.example.cidaasv2.Helper.Converter;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Models.DBModel.UserInfoModel;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.scottyab.aescrypt.AESCrypt;

import java.util.UUID;

/**
 * Created by widasrnarayanan on 13/2/18.
 */

public class EntityToModelConverter {
//Convert AccessTokenEntity to Model
public static EntityToModelConverter sharedinstance;
    public static EntityToModelConverter getShared()
    {
        if(sharedinstance==null)
        {
            sharedinstance=new EntityToModelConverter();
        }
        return sharedinstance;
    }

    //Entity to Model Conversion

// convert accessTokenEntity To AccessTokenModel
    public void accessTokenEntityToAccessTokenModel(AccessTokenEntity accessTokenEntity, String userId, Result<AccessTokenModel> callback)
    {
        try
        {
            String EncryptedToken="";

            AccessTokenModel.getShared().setExpiresIn(accessTokenEntity.getExpires_in());
            AccessTokenModel.getShared().setIdToken(accessTokenEntity.getId_token());
            AccessTokenModel.getShared().setRefreshToken(accessTokenEntity.getRefresh_token());
            AccessTokenModel.getShared().setScope(accessTokenEntity.getScope());
            AccessTokenModel.getShared().setUserState(accessTokenEntity.getUserstate());
            //Additional Details to store token in Local DB
            AccessTokenModel.getShared().setUserId(userId);
            AccessTokenModel.getShared().setSalt(UUID.randomUUID().toString());
            //AccessTokenModel.getShared().setKey(UUID.randomUUID().toString());
            //Convert Milliseconds int0 seconds
            AccessTokenModel.getShared().setSeconds(System.currentTimeMillis()/1000);

            //Encrypt the AccessToken
                try {
                      EncryptedToken =AESCrypt.encrypt(AccessTokenModel.getShared().getSalt(),accessTokenEntity.getAccess_token());
                }
                catch (Exception e)
                {
                    EncryptedToken="";
                }
            if(EncryptedToken!="") {
                AccessTokenModel.getShared().setAccessToken(EncryptedToken);
                AccessTokenModel.getShared().setEncrypted(true);
            }
            else
            {
                AccessTokenModel.getShared().setAccessToken(accessTokenEntity.getAccess_token());
                AccessTokenModel.getShared().setEncrypted(false);
                AccessTokenModel.getShared().setPlainToken(accessTokenEntity.getAccess_token());
            }
         callback.success(AccessTokenModel.getShared());
        }
        catch (Exception e)
        {
            //TODO Handle Error
            callback.failure(null);
        }
    }


    // Model to Entity  Conversion

    // convert accessTokenModel to AccessTokenEntity
    public void accessTokenModelToAccessTokenEntity(AccessTokenModel accessTokenModel, String userId, Result<AccessTokenEntity> callback)
    {
        try
        {
            AccessTokenEntity accessTokenEntity=new AccessTokenEntity();


           accessTokenEntity.setAccess_token(accessTokenModel.getAccessToken());
            accessTokenEntity.setExpires_in(accessTokenModel.getExpiresIn());
            accessTokenEntity.setId_token(accessTokenModel.getIdToken());
            accessTokenEntity.setRefresh_token(accessTokenModel.getRefreshToken());
            accessTokenEntity.setScope(accessTokenModel.getScope());
            accessTokenEntity.setUserstate(accessTokenModel.getUserState());


            //Decrypt the AccessToken
            if(accessTokenModel.isEncrypted()) {
                accessTokenEntity.setAccess_token(AESCrypt.decrypt(accessTokenModel.getSalt(),accessTokenEntity.getAccess_token()));

            }
            else
            {
                accessTokenEntity.setAccess_token(accessTokenModel.getShared().getPlainToken());
            }
            callback.success(accessTokenEntity);
        }
        catch (Exception e)
        {
            //TODO Handle Error

        }
    }

}
