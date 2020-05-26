package de.cidaas.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)

public class TotpClockTest {
    SharedPreferences mPreferences;
    Object mLock;
    TotpClock totpClock;

    @Before
    public void setUp() {
        totpClock = new TotpClock();
    }

    /*  @Test
      public void testCurrentTimeMillis() throws Exception {
          long result = totpClock.currentTimeMillis();
          Assert.assertEquals(0L, result);
      }

      @Test
      public void testGetTimeCorrectionMinutes() throws Exception {
          int result = totpClock.getTimeCorrectionMinutes();
          Assert.assertEquals(0, result);
      }

      @Test
      public void testSetTimeCorrectionMinutes() throws Exception {
          totpClock.setTimeCorrectionMinutes(0);
      }
  */
    @Test
    public void testOnSharedPreferenceChanged() throws Exception {
        totpClock.onSharedPreferenceChanged(null, "key");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme