package de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentSettingsPIIControllersEntity implements Serializable {


    String piiController;
    boolean onBehalf;
    String contact;
    ConsentSettingsPIIControllersAddressEntity address;
    String email;
    String phone;

    public String getPiiController() {
        return piiController;
    }

    public void setPiiController(String piiController) {
        this.piiController = piiController;
    }

    public boolean isOnBehalf() {
        return onBehalf;
    }

    public void setOnBehalf(boolean onBehalf) {
        this.onBehalf = onBehalf;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ConsentSettingsPIIControllersAddressEntity getAddress() {
        return address;
    }

    public void setAddress(ConsentSettingsPIIControllersAddressEntity address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
