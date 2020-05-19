package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Initiate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateEntity implements Serializable {

    private String sub="";
    private String request_id="";
    private String usage_type="";
    private String device_id="";
    private String push_id="";
    private String verificationType="";

    //For Pattern

    public InitiateEntity(String sub, String request_id,  String usage_type,String verificationType) {
        this.sub = sub;
        this.request_id = request_id;
       // this.medium_id = medium_id;
        this.usage_type = usage_type;
        this.verificationType=verificationType;
    }


    public InitiateEntity(String sub, String request_id, String usage_type,String verificationType, String device_id, String push_id) {
        this.sub = sub;
        this.request_id = request_id;
        this.verificationType=verificationType;
        this.usage_type = usage_type;
        this.device_id = device_id;
        this.push_id = push_id;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }
}
