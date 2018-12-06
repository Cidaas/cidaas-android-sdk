package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.RegisteredKeys;

import java.io.Serializable;

public class NFC_sign_object implements Serializable {
    private String statusId;

    private String challenge;

    private String appId;

    private String fidoRequestId;

    private String type;

    private RegisteredKeys[] registeredKeys;

    public String getStatusId ()
    {
        return statusId;
    }

    public void setStatusId (String statusId)
    {
        this.statusId = statusId;
    }

    public String getChallenge ()
    {
        return challenge;
    }

    public void setChallenge (String challenge)
    {
        this.challenge = challenge;
    }

    public String getAppId ()
    {
        return appId;
    }

    public void setAppId (String appId)
    {
        this.appId = appId;
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

    public RegisteredKeys[] getRegisteredKeys ()
    {
        return registeredKeys;
    }

    public void setRegisteredKeys (RegisteredKeys[] registeredKeys)
    {
        this.registeredKeys = registeredKeys;
    }


}
