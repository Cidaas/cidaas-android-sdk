package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FaceMetadataEntity implements Serializable {

    String comment;
    String number_images_needed;
    String number_images_uploaded;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNumber_images_needed() {
        return number_images_needed;
    }

    public void setNumber_images_needed(String number_images_needed) {
        this.number_images_needed = number_images_needed;
    }

    public String getNumber_images_uploaded() {
        return number_images_uploaded;
    }

    public void setNumber_images_uploaded(String number_images_uploaded) {
        this.number_images_uploaded = number_images_uploaded;
    }
}

