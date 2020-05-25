package de.cidaas.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class ConsentResponseDataEntityTest {

    ConsentResponseDataEntity consentResponseDataEntity = new ConsentResponseDataEntity();

    @Test
    public void getId() {
        consentResponseDataEntity.set_id("test");
        assertTrue(consentResponseDataEntity.get_id() == "test");
    }

    @Test
    public void geDescription() {
        consentResponseDataEntity.setDescription("test");
        assertTrue(consentResponseDataEntity.getDescription() == "test");

    }

    @Test
    public void getTitle() {
        consentResponseDataEntity.setTitle("title");
        assertTrue(consentResponseDataEntity.getTitle() == "title");

    }

    @Test
    public void getUserAgreeText() {
        consentResponseDataEntity.setUserAgreeText("title");
        assertTrue(consentResponseDataEntity.getUserAgreeText() == "title");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme