package de.cidaas.cidaasv2.Helper.Converter;

import de.cidaas.sdk.android.cidaas.Helper.Converter.EntityToModelConverter;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Models.DBModel.AccessTokenModel;
import de.cidaas.sdk.android.cidaas.Service.Entity.AccessToken.AccessTokenEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class EntityToModelConverterTest {

    EntityToModelConverter entityToModelConverter = new EntityToModelConverter();

    @Before
    public void setUp() {


    }

    @Test
    public void testGetShared() throws Exception {
        EntityToModelConverter result = EntityToModelConverter.getShared();
        Assert.assertTrue(result instanceof EntityToModelConverter);
    }

    @Test
    public void testAccessTokenEntityToAccessTokenModel() throws Exception {
        entityToModelConverter.accessTokenEntityToAccessTokenModel(new AccessTokenEntity(), "userId", new Result<AccessTokenModel>() {
            @Override
            public void success(AccessTokenModel result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAccessTokenModelToAccessTokenEntity() throws Exception {
        entityToModelConverter.accessTokenModelToAccessTokenEntity(new AccessTokenModel(), "userId", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme