package de.cidaas.cidaasv2.Service.Entity;

import org.junit.Assert;
import org.junit.Test;


public class UserinfoEntityTest {


    UserinfoEntity userinfoEntity = new UserinfoEntity();

    @Test
    public void testIsUser_status() throws Exception {
        userinfoEntity.setUser_status("Test");
        Assert.assertEquals("Test", userinfoEntity.isUser_status());
    }

    @Test
    public void setName() throws Exception {
        userinfoEntity.setName("Test");
        Assert.assertEquals("Test", userinfoEntity.getName());
    }

    @Test
    public void setFamily_name() throws Exception {
        userinfoEntity.setFamily_name("Test");
        Assert.assertEquals("Test", userinfoEntity.getFamily_name());
    }

    @Test
    public void setSub() throws Exception {
        userinfoEntity.setSub("Test");
        Assert.assertEquals("Test", userinfoEntity.getSub());
    }

    @Test
    public void setGiven_name() throws Exception {
        userinfoEntity.setGiven_name("Test");
        Assert.assertEquals("Test", userinfoEntity.getGiven_name());
    }

    @Test
    public void setEmail() throws Exception {
        userinfoEntity.setEmail("Test");
        Assert.assertEquals("Test", userinfoEntity.getEmail());
    }

    @Test
    public void setMobile() throws Exception {
        userinfoEntity.setMobile_number("Test");
        Assert.assertEquals("Test", userinfoEntity.getMobile_number());
    }

    @Test
    public void setNickname() throws Exception {
        userinfoEntity.setNickname("Test");
        Assert.assertEquals("Test", userinfoEntity.getNickname());
    }

    @Test
    public void setPreferred_username() throws Exception {
        userinfoEntity.setPreferred_username("Test");
        Assert.assertEquals("Test", userinfoEntity.getPreferred_username());
    }

    @Test
    public void setWebsite() throws Exception {
        userinfoEntity.setWebsite("Test");
        Assert.assertEquals("Test", userinfoEntity.getWebsite());
    }


    @Test
    public void setLocale() throws Exception {
        userinfoEntity.setLocale("Test");
        Assert.assertEquals("Test", userinfoEntity.getLocale());
    }

    @Test
    public void setLast_used_identity_id() throws Exception {
        userinfoEntity.setLast_used_identity_id("Test");
        Assert.assertEquals("Test", userinfoEntity.getLast_used_identity_id());
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
        userinfoEntity.setEmail_verified(true);
        Assert.assertTrue(userinfoEntity.isEmail_verified());
    }

    @Test
    public void setUpdated_at() throws Exception {
        userinfoEntity.setUpdated_at(17);
        Assert.assertEquals(17, userinfoEntity.getUpdated_at());
    }

    @Test
    public void setLast_accessed_at() throws Exception {
        userinfoEntity.setLast_accessed_at(27);
        Assert.assertEquals(27, userinfoEntity.getLast_accessed_at());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme