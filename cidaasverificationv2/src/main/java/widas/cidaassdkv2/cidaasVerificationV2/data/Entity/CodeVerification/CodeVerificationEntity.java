package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.CodeVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeVerificationEntity implements Serializable {
    String verificationCode="";
    String exchangeId="";
    String requestId="";
    String trackId="";
    String usageType="";


}
