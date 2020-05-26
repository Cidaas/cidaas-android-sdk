package de.cidaas.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Test;


import static junit.framework.TestCase.assertTrue;

public class ConsentManagementResponseDataEntityTest {

    ConsentManagementResponseDataEntity consentManagementResponseDataEntity = new ConsentManagementResponseDataEntity();

    @Test
    public void getAccepted() {
        consentManagementResponseDataEntity.setAccepted(true);
        assertTrue(consentManagementResponseDataEntity.isAccepted());
    }

    @Test
    public void getSub() {
        consentManagementResponseDataEntity.setSub("test");
        assertTrue(consentManagementResponseDataEntity.getSub() == "test");
    }

    @Test
    public void getVersion() {
        consentManagementResponseDataEntity.setVersion("test");
        assertTrue(consentManagementResponseDataEntity.getVersion() == "test");
    }

    @Test
    public void getName() {
        consentManagementResponseDataEntity.setName("Name");
        assertTrue(consentManagementResponseDataEntity.getName() == "Name");
    }

    @Test
    public void getClientId() {
        consentManagementResponseDataEntity.setClient_id("test");
        assertTrue(consentManagementResponseDataEntity.getClient_id() == "test");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme