package com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

/**
 * Created by ganesh on 14/02/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class TotpClock implements SharedPreferences.OnSharedPreferenceChangeListener {

    static final String PREFERENCE_KEY_OFFSET_MINUTES = "timeCorrectionMinutes";

    private SharedPreferences mPreferences;

    private final Object mLock = new Object();

    private Integer mCachedCorrectionMinutes;

    public TotpClock(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public TotpClock(){}

    public long currentTimeMillis() {
        return System.currentTimeMillis() + getTimeCorrectionMinutes() * Utilities.MINUTE_IN_MILLIS;
    }

    public int getTimeCorrectionMinutes() {
        synchronized (mLock) {
            if (mCachedCorrectionMinutes == null) {
                try {
                    mCachedCorrectionMinutes = mPreferences.getInt(PREFERENCE_KEY_OFFSET_MINUTES, 0);
                } catch(ClassCastException e) {
                    mCachedCorrectionMinutes = Integer.valueOf(mPreferences.getString(PREFERENCE_KEY_OFFSET_MINUTES, "0"));
                }
            }
            return mCachedCorrectionMinutes;
        }
    }

    public void setTimeCorrectionMinutes(int minutes) {
        synchronized (mLock) {
            mPreferences.edit().putInt(PREFERENCE_KEY_OFFSET_MINUTES, minutes).commit();
            mCachedCorrectionMinutes = null;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(PREFERENCE_KEY_OFFSET_MINUTES)) {
            mCachedCorrectionMinutes = null;
        }
    }
}
