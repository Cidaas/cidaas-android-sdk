package com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList;

import java.io.Serializable;

public class MFAListData implements Serializable {

    public String _id;
    public String verification_type;
    public String configured_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String getConfigured_at() {
        return configured_at;
    }

    public void setConfigured_at(String configured_at) {
        this.configured_at = configured_at;
    }
}

