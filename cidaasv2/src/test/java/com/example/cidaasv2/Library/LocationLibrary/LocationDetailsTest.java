package com.example.cidaasv2.Library.LocationLibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LocationDetailsTest {
    @Mock
    Context mContext;
    @Mock
    LocationDetails shared;
    @Mock
    Location location;
    @Mock
    LocationManager locationManager;
    @InjectMocks
    LocationDetails locationDetails;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        when(shared.getLocation()).thenReturn(null);

        mContext = mock(Context.class);

        when(mContext.checkPermission(eq(Manifest.permission.WRITE_EXTERNAL_STORAGE), anyInt(), anyInt())).thenReturn(
                PackageManager.PERMISSION_GRANTED);

        LocationDetails locationDetail1 = LocationDetails.getShared(mContext);
        LocationDetails locationDetail2 = LocationDetails.getShared(mContext);
        Assert.assertEquals(locationDetail2, locationDetail1);
    }

    @Test
    public void testGetLocation() throws Exception {
        Location result = locationDetails.getLocation();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testStopUsingGPS() throws Exception {
        locationDetails.stopUsingGPS();
    }

    @Test
    public void testGetLatitude() throws Exception {
        when(shared.getLocation()).thenReturn(null);

        String result = locationDetails.getLatitude();
        Assert.assertEquals("", result);
    }

    @Test
    public void testGetLongitude() throws Exception {
        when(shared.getLocation()).thenReturn(null);

        String result = locationDetails.getLongitude();
        Assert.assertEquals("", result);
    }

    @Test
    public void testGetBearing() throws Exception {
        float result = locationDetails.getBearing();
        Assert.assertEquals(0.0, result, 0.0);
    }

    @Test
    public void testCanGetLocation() throws Exception {
        boolean result = locationDetails.canGetLocation();
        Assert.assertEquals(false, result);
    }

    @Test
    public void testOnLocationChanged() throws Exception {
        locationDetails.onLocationChanged(null);
    }

    @Test
    public void testOnProviderDisabled() throws Exception {
        locationDetails.onProviderDisabled("provider");
    }

    @Test
    public void testOnProviderEnabled() throws Exception {
        locationDetails.onProviderEnabled("provider");
    }

    @Test
    public void testOnStatusChanged() throws Exception {
        locationDetails.onStatusChanged("provider", 0, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme