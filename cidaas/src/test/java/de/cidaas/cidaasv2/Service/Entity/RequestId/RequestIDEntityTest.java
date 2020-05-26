package de.cidaas.cidaasv2.Service.Entity.RequestId;

import junit.framework.Assert;

import org.junit.Test;

public class RequestIDEntityTest {

    RequestIDEntity requestIDEntity = new RequestIDEntity();

    @Test
    public void setGroupname() {
        requestIDEntity.setGroupname("GroupName");
        Assert.assertEquals("GroupName", requestIDEntity.getGroupname());
    }

    @Test
    public void setLang() {
        requestIDEntity.setLang("Lang");
        Assert.assertEquals("Lang", requestIDEntity.getLang());
    }

    @Test
    public void setView_type() {
        requestIDEntity.setView_type("ViewType");
        Assert.assertEquals("ViewType", requestIDEntity.getView_type());
    }

    @Test
    public void setRequestId() {
        requestIDEntity.setRequestId("RequestId");
        Assert.assertEquals("RequestId", requestIDEntity.getRequestId());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme