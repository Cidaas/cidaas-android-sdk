package com.example.cidaasv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.cidaasv2.test", appContext.getPackageName());
    }

    private SharedPreferences sharedPreferences;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getTargetContext();
        sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }


    @Test
    public void put_and_get_preference() throws Exception {
        String string1 = "test";
        sharedPreferences.edit().putString("test", string1).apply();
        String string2 = sharedPreferences.getString("test", "");

        // Verify that the received data is correct.
        assertEquals(string1, string2);
    }



}
