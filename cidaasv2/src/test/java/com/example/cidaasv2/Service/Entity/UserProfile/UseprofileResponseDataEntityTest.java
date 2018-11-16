package com.example.cidaasv2.Service.Entity.UserProfile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UseprofileResponseDataEntityTest {


    UseprofileResponseDataEntity useprofileResponseDataEntity;




    @Before
    public void setUp() {
        useprofileResponseDataEntity=new UseprofileResponseDataEntity();
    }

    @Test
    public void testGetRoles() throws Exception {
        String[] result={"Test"};

        useprofileResponseDataEntity.setRoles(result);

        Assert.assertEquals("Test",  useprofileResponseDataEntity.getRoles()[0]);
}

    @Test
    public void testGetGroups() throws Exception {
        String[] result={"Test"};
        useprofileResponseDataEntity.setGroups(result);
        Assert.assertEquals("Test",  useprofileResponseDataEntity.getGroups()[0]);

    }

    @Test
    public void setIdentity(){

        IdentityEntity identityEntity=new IdentityEntity();
        identityEntity.setMobile_number("Test");
        useprofileResponseDataEntity.setIdentity(identityEntity);
        Assert.assertEquals("Test",useprofileResponseDataEntity.getIdentity()
                .getMobile_number());

    }

    @Test
    public void setUserAccount(){

        UserAccountEntity userAccountEntity=new UserAccountEntity();
        userAccountEntity.setUserStatus("Test");
        useprofileResponseDataEntity.setUserAccount(userAccountEntity);
        junit.framework.Assert.assertEquals("Test",useprofileResponseDataEntity.getUserAccount().getUserStatus());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme