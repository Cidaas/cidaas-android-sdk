package de.cidaas.sdk.android.Service.Entity;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.UserInfo.UserInfoEntity;


public class UserinfoEntityTest {


    UserInfoEntity userinfoEntity = new UserInfoEntity();


    @Test
    public void testIsUser_status() throws Exception {
        userinfoEntity.setUserStatus("Test");

        Assert.assertEquals("Test", userinfoEntity.getUserStatus());
    }

    @Test
    public void setName() throws Exception {
        userinfoEntity.setName("Test");
        Assert.assertEquals("Test", userinfoEntity.getName());
    }

    @Test
    public void setFamily_name() throws Exception {
        userinfoEntity.setFamilyName("Test");
        Assert.assertEquals("Test", userinfoEntity.getFamilyName());
    }

    @Test
    public void setGender() throws Exception {
        userinfoEntity.setGender("Test");
        Assert.assertEquals("Test", userinfoEntity.getGender());
    }

    @Test
    public void setSub() throws Exception {
        userinfoEntity.setSub("Test");
        Assert.assertEquals("Test", userinfoEntity.getSub());
    }

    @Test
    public void setGiven_name() throws Exception {
        userinfoEntity.setGivenName("Test");
        Assert.assertEquals("Test", userinfoEntity.getGivenName());
    }

    @Test
    public void setEmail() throws Exception {
        userinfoEntity.setEmail("Test");
        Assert.assertEquals("Test", userinfoEntity.getEmail());
    }

    @Test
    public void setMobile() throws Exception {
        userinfoEntity.setMobileNumber("Test");
        Assert.assertEquals("Test", userinfoEntity.getMobileNumber());
    }

    @Test
    public void setNickname() throws Exception {
        userinfoEntity.setNickname("Test");
        Assert.assertEquals("Test", userinfoEntity.getNickname());
    }

    @Test
    public void setPreferred_username() throws Exception {
        userinfoEntity.setPreferredUsername("Test");
        Assert.assertEquals("Test", userinfoEntity.getPreferredUsername());
    }

    @Test
    public void setWebsite() throws Exception {
      //  userinfoEntity.setWebsite("Test");
      //  Assert.assertEquals("Test", userinfoEntity.get());
    }


    @Test
    public void setLocale() throws Exception {
        userinfoEntity.setLocale("Test");
        Assert.assertEquals("Test", userinfoEntity.getLocale());
    }

    @Test
    public void setLast_used_identity_id() throws Exception {
        userinfoEntity.setLastUsedIdentityId("Test");
        Assert.assertEquals("Test", userinfoEntity.getLastUsedIdentityId());
    }

    @Test
    public void setProvider() throws Exception {
        userinfoEntity.setProvider("Test");
        Assert.assertEquals("Test", userinfoEntity.getProvider());
    }

    @Test
    public void setUsername() throws Exception {
        userinfoEntity.setUsername("Test");
        Assert.assertEquals("Test", userinfoEntity.getUsername());
    }

    @Test
    public void setEmail_verified() throws Exception {
        userinfoEntity.setEmailVerified(true);
        Assert.assertTrue(userinfoEntity.getEmailVerified());
    }

    @Test
    public void setUpdated_at() throws Exception {
        userinfoEntity.setUpdatedAt(17);
        Assert.assertEquals(17, (long) userinfoEntity.getUpdatedAt());
    }

    @Test
    public void setLast_accessed_at() throws Exception {
        int values = 27;
        userinfoEntity.setLastAccessedAt(values);
        Assert.assertEquals((values), (long) userinfoEntity.getLastAccessedAt());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme