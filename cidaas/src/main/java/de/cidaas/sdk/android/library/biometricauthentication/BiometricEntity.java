package de.cidaas.sdk.android.library.biometricauthentication;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BiometricEntity implements Serializable {
    private String title;
    private String subtitle;
    private String description;
    private String negativeButtonText;

    private Context context;

    public BiometricEntity(Context context, String title, String subtitle, String description, String negativeButtonText) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.negativeButtonText = negativeButtonText;
        this.context = context;
    }

    public BiometricEntity(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public Context getContext() {
        return context;
    }
}
