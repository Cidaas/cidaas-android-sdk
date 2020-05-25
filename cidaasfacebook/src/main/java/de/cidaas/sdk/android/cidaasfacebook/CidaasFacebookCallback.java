package de.cidaas.sdk.android.cidaasfacebook;

/**
 * Created by ganesh on 16/06/17.
 */

public interface CidaasFacebookCallback {
    public void onSuccess(String message);

    public void onCancel(String message);

    public void onError(String message);
}
