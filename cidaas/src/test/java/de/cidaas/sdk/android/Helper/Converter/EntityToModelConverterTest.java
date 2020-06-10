package de.cidaas.sdk.android.Helper.Converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.helper.converter.EntityToModelConverter;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.models.dbmodel.AccessTokenModel;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;


@RunWith(RobolectricTestRunner.class)
@Ignore
public class EntityToModelConverterTest {

    private EntityToModelConverter entityToModelConverter;

    @Before
    public void setUp() {

        entityToModelConverter = new EntityToModelConverter(RuntimeEnvironment.application);

    }

    @Test
    public void testGetShared() throws Exception {
        EntityToModelConverter result = EntityToModelConverter.getShared(RuntimeEnvironment.application);
        Assert.assertTrue(result instanceof EntityToModelConverter);
    }

    @Test
    public void testAccessTokenEntityToAccessTokenModel() throws Exception {
        entityToModelConverter.accessTokenEntityToAccessTokenModel(new AccessTokenEntity(), "userId", new EventResult<AccessTokenModel>() {
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
        entityToModelConverter.accessTokenModelToAccessTokenEntity(new AccessTokenModel(), "userId", new EventResult<AccessTokenEntity>() {
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