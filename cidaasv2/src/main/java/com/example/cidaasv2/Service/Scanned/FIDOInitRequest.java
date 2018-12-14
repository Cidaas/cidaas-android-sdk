package com.example.cidaasv2.Service.Scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FIDOInitRequest implements Serializable{
    private String statusId;
    private String appId;
    private RegisterRequests[] registerRequests;
    private String fidoRequestId;
    private String type;
    private String[] registeredKeys;

    public String getStatusId ()
    {
        return statusId;
    }

    public void setStatusId (String statusId)
    {
        this.statusId = statusId;
    }

    public String getAppId ()
    {
        return appId;
    }

    public void setAppId (String appId)
    {
        this.appId = appId;
    }

    public RegisterRequests[] getRegisterRequests ()
    {
        return registerRequests;
    }

    public void setRegisterRequests (RegisterRequests[] registerRequests)
    {
        this.registerRequests = registerRequests;
    }

    public String getFidoRequestId ()
    {
        return fidoRequestId;
    }

    public void setFidoRequestId (String fidoRequestId)
    {
        this.fidoRequestId = fidoRequestId;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String[] getRegisteredKeys ()
    {
        return registeredKeys;
    }

    public void setRegisteredKeys (String[] registeredKeys)
    {
        this.registeredKeys = registeredKeys;
    }
}
