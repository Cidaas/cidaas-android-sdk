package com.example.cidaasv2.Helper.CustomTab.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cidaasv2.BuildConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CustomTabPropertiesEntityTest {

    Parcelable.Creator<CustomTabPropertiesEntity> CREATOR;

    CustomTabPropertiesEntity customTabPropertiesEntity;

    Parcel sam=Parcel.obtain();

    @Before
    public void setUp() {
        customTabPropertiesEntity=new CustomTabPropertiesEntity("SD","sd");
    }

    @Test
    public void testDescribeContents() throws Exception {
        int result = customTabPropertiesEntity.describeContents();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testWriteToParcel() throws Exception {
        customTabPropertiesEntity.writeToParcel(sam, 0);

    }

    @Test
    public void testGetPrimaryColor() throws Exception {
        customTabPropertiesEntity.writeToParcel(sam, 0);

    }
    @Test
    public void testGetSecondaryColorl() throws Exception {
        customTabPropertiesEntity.writeToParcel(sam, 0);

    }
    @Test
    public void testSetPrimaryColor() throws Exception {
        customTabPropertiesEntity.setPrimaryColor("PrimaryColor");
        Assert.assertEquals("PrimaryColor",customTabPropertiesEntity.getPrimaryColor());
    }
    @Test
    public void testSetSecondaryColor() throws Exception {
        customTabPropertiesEntity.setSecondaryColor("SecondaryColor");
        Assert.assertEquals("SecondaryColor",customTabPropertiesEntity.getSecondaryColor());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme