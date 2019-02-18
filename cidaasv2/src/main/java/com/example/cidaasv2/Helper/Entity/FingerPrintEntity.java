package com.example.cidaasv2.Helper.Entity;

public class FingerPrintEntity {
    String Title="Authenticate";
    String Subtitle="Please Authenticate to continue";
    String Description="";
    String NegativeButtonString="Cancel";


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
}
