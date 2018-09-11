package com.example.cidaasv2.Service.Entity.ConsentManagement;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ConsentSettingsPIIControllersEntityTest {

    ConsentSettingsPIIControllersAddressEntity address;

    ConsentSettingsPIIControllersEntity consentSettingsPIIControllersEntity;

    @Before
    public void setUp() {
        
        consentSettingsPIIControllersEntity=new ConsentSettingsPIIControllersEntity();
    }





    @Test
    public void setOnBehalf()
    {
        consentSettingsPIIControllersEntity.setOnBehalf(true);
        Assert.assertEquals(true,consentSettingsPIIControllersEntity.isOnBehalf());
    }

    @Test
    public void setAddress()
    {
        address=new ConsentSettingsPIIControllersAddressEntity();
        address.setStreetAddress("Test");
        consentSettingsPIIControllersEntity.setAddress(address);
        Assert.assertEquals("Test",consentSettingsPIIControllersEntity.getAddress().getStreetAddress());
    }





    @Test
    public void setpII()
    {
        consentSettingsPIIControllersEntity.setPiiController("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersEntity.getPiiController());
    }

    @Test
    public void setContact()
    {
        consentSettingsPIIControllersEntity.setContact("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersEntity.getContact());
    }

    @Test
    public void setEMail()
    {
        consentSettingsPIIControllersEntity.setEmail("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersEntity.getEmail());
    }

    @Test
    public void setPhone()
    {
        consentSettingsPIIControllersEntity.setPhone("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersEntity.getPhone());
    }
    
    


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme