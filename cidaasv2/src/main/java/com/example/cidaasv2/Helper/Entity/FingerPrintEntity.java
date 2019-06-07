package com.example.cidaasv2.Helper.Entity;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FingerPrintEntity implements Serializable {
    String Title="Authenticate";
    String Subtitle="Please Authenticate to continue";
    String Description="";
    String NegativeButtonString="Cancel";

    @JsonIgnore
    Context context;

    public FingerPrintEntity(Context context) {
        this.context = context;
    }

    //All Parameter
    public FingerPrintEntity(Context context,String title, String subtitle, String description, String negativeButtonString ) {
        Title = title;
        Subtitle = subtitle;
        Description = description;
        NegativeButtonString = negativeButtonString;
        this.context = context;
    }

    //


    public FingerPrintEntity( Context context,String title, String description) {
        Title = title;
        Description = description;
        this.context = context;
    }

    public FingerPrintEntity(Context context,String title, String subtitle, String description) {
        Title = title;
        Subtitle = subtitle;
        Description = description;
        this.context = context;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNegativeButtonString() {
        return NegativeButtonString;
    }

    public void setNegativeButtonString(String negativeButtonString) {
        NegativeButtonString = negativeButtonString;
    }

    @JsonIgnore
    public Context getContext() {
        return context;
    }

    @JsonIgnore
    public void setContext(Context context) {
        this.context = context;
    }
}
