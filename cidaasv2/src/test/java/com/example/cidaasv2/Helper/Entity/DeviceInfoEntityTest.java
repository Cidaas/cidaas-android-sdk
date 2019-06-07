package com.example.cidaasv2.Helper.Entity;

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
public class DeviceInfoEntityTest {

    Parcelable.Creator<DeviceInfoEntity> CREATOR=new Parcelable.ClassLoaderCreator<DeviceInfoEntity>() {
        @Override
        public DeviceInfoEntity createFromParcel(Parcel source, ClassLoader loader) {
            return null;
        }

        @Override
        public DeviceInfoEntity createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public DeviceInfoEntity[] newArray(int size) {
            return new DeviceInfoEntity[0];
        }
    };

    DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

    Parcel in=Parcel.obtain();


    @Before
    public void setUp() {

    }

    @Test
    public void testDescribeContents() throws Exception {
        int result = deviceInfoEntity.describeContents();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testWriteToParcel() throws Exception {
        deviceInfoEntity.writeToParcel(in, 0);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme