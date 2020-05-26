package de.cidaas.sdk.android.cidaasnative.Client;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ClientInfo.ClientInfoDataEntity;

import static junit.framework.TestCase.assertTrue;

public class ClientInfoDataEntityTest {
    ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();

    @Test
    public void testGetLogin_providers() throws Exception {
        String string1 = "String1";
        String string2 = "String2";
        String[] strarray = new String[2];

        strarray[0] = string1;
        strarray[1] = string2;

        clientInfoDataEntity.setLogin_providers(strarray);

        String[] result = clientInfoDataEntity.getLogin_providers();
        Assert.assertArrayEquals(new String[]{"String1", "String2"}, result);
    }

    private boolean passwordless_enabled;
    private String logo_uri;
    private String[] login_providers;
    private String policy_uri;
    private String tos_uri;
    private String client_name;


    @Test
    public void getPasswordless_enabled() {
        ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();
        clientInfoDataEntity.setPasswordless_enabled(true);
        assertTrue(clientInfoDataEntity.isPasswordless_enabled());
    }

    @Test
    public void getLogo_uri() {
        ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();
        clientInfoDataEntity.setLogo_uri("Logo URI");
        assertTrue(clientInfoDataEntity.getLogo_uri() == "Logo URI");
    }

    @Test
    public void getPolicy_uri() {
        ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();
        clientInfoDataEntity.setPolicy_uri("Policy URI");
        assertTrue(clientInfoDataEntity.getPolicy_uri() == "Policy URI");
    }

    @Test
    public void getTos_uri() {
        ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();
        clientInfoDataEntity.setTos_uri("TOS URI");
        assertTrue(clientInfoDataEntity.getTos_uri() == "TOS URI");
    }

    @Test
    public void getClient_name() {
        ClientInfoDataEntity clientInfoDataEntity = new ClientInfoDataEntity();
        clientInfoDataEntity.setClient_name("clientName");
        assertTrue(clientInfoDataEntity.getClient_name() == "clientName");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme