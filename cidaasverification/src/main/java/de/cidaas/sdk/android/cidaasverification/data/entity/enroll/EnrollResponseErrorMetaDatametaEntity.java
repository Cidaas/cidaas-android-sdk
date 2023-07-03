package de.cidaas.sdk.android.cidaasverification.data.entity.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponseErrorMetaDatametaEntity implements Serializable {

    public int getNumber_images_uploaded() {
        return number_images_uploaded;
    }

    public void setNumber_images_uploaded(int number_images_uploaded) {
        this.number_images_uploaded = number_images_uploaded;
    }

    int number_images_uploaded;

}
