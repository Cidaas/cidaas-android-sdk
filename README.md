# Cidaas Android SDK V2

[![Build Status](https://travis-ci.org/Cidaas/de.cidaas-sdk-android-v2.svg?branch=development)](https://travis-ci.org/Cidaas/de.cidaas-sdk-android-v2)
[![codecov.io](https://codecov.io/gh/Cidaas/de.cidaas-sdk-android-v2/branch/development/graph/badge.svg)](https://codecov.io/gh/Cidaas/de.cidaas-sdk-android-v2/branch/development)
[![jitpack](https://jitpack.io/v/Cidaas/de.cidaas-sdk-android-v2.svg)](https://jitpack.io/#Cidaas/de.cidaas-sdk-android-v2)
[![Platform](https://img.shields.io/badge/Platforms-android-4E4E4E.svg?colorA=28a745)](#installation)


The steps here will guide you through setting up and managing authentication and authorization in your apps using cidaas SDK.

## Table of Contents

<!--ts-->
* [Requirements](#requirements)
* [Installation](#installation)
* [Getting started](#getting-started)
* [Getting Client Id and urls](#getting-client-id-and-urls)
* [Initialisation](#initialisation)
* [Usage](#usage)
    <!--ts-->
    * [Native Browser Login](#native-browser-login)
        <!--ts-->
        * [Classic Login](#classic-login)
        * [Social Login](#social-login)
        <!--te-->
    *  [Embedded Browser Login](#embedded-browser-login)
    * [Native UI Integration](/PureNative.md)
    * [Verification v2](/Verification-v2.md)
    <!--te-->

## Requirements

    minSdkVersion 21

### Steps for integrate native Android SDKs:
> ##### Note:- If you Already integrate the Cidaas 1.0.0 , Please check the migration guide here(/migration.md)
### Installation


Cidaas SDK is available through jitpack.io.  Please ensure that you are using the latest versions by [checking here](https://jitpack.io/#Cidaas/cidaas-v2-sdk-android)

Add the following Gradle configuration to your Android project:

```java        
allprojects {
    repositories {
        ...
		maven { url 'https:jitpack.io' }
	}
}
```		
 Add the dependency to app module
 ```java
dependencies {
 implementation 'com.github.Cidaas:cidaas-v2-sdk-android:1.0.4'
}
 ```
This will add all the subproject of cidaas SDK, if you need specific subproject just add it before the version . For example


 ```java
dependencies {
    implementation 'com.github.Cidaas:cidaas-v2-sdk-android:cidaasnative:1.0.4'
}
 ```

This will add only the Cidaas native features

For now we need to implement only Cidaas Core Functions 

So please Add

 ```java
dependencies {
    implementation 'com.github.Cidaas:cidaas-v2-sdk-android:cidaas:1.0.4'
}
 ```

> ##### Note:- This Library is developed in AndroidX so AndroidX migration is necessary

To migrate your project into Androidx in android studio goto

Refacter-> Migrate to AndroidX


 ## Getting started
 
 Create an asset folder by right click the app module goto new->folder->Assets folder and click the finish button

Create an xml file named as <b>cidaas.xml</b> inside the asset folder and fill all the inputs in key value pair. The inputs are below mentioned.

> ##### Note:- The File name must be cidaas.xml 

A sample XML file would look like this :

``` 
<?xml version="1.0" encoding="utf-8"?>
<resources>
	<item name="DomainURL" type="string">DomainURL</item>
	<item name="ClientId" type="string">ClientId</item>
	<item name="RedirectURL">RedirectURL</item>
</resources> 

```

Following sections will help you to generate some of the information that is needed for XML.


### Getting client Id and urls

You can get this by creating your App in App settings section of cidaas Admin portal. Once you select the right scope and application type, and fill in all mandatory fields, you can use the generated Client ID and re-direct URLs.


#### Initialisation

The first step of integrating cidaas sdk is the initialisation process.
```java

Cidaas cidaas = Cidaas.getInstance(yourActivityContext);

    or

Cidaas cidaas =new Cidaas(yourActivityContext);

```

### Usage

> ##### Note:- If you are going to use Native browser login, you must use getInstance() method to create cidaas instance


#### Native Browser Login 
#### Classic Login
You can login using your native browser and redirects to the App once successfully logged in. To login with your native browser call ****loginWithBrowser()****.

```java
cidaas.loginWithBrowser(yourActivityContext, "NullableColorParameterInColorCode", new EventResult<AccessTokenEntity>() {
     @Override
     public void success(AccessTokenEntity result) {
    	//Your Success Code
     }

     @Override
     public void failure(WebAuthError error) {
		//Your Failure Code
     }
});
```
Add Deep link or app link (#add-custom-scheme)

You can get the url for the login and you can load in your favorite browser for this you can call 

```java
Cidaas.getInstance(yourContext).getLoginURL(new EventResult<String>() {
    @Override
    public void success(String result) {
        //Your Success code
    }

    @Override
    public void failure(WebAuthError error) {
        //Your Failure code
    }
});
```

#### Social Login
You can also perform social login using your native browser and redirects to the App once successfully logged in. To perform social login call ****loginWithSocial()****.

```java
cidaas.loginWithSocial(yourActivityContext, "requestID",your_Social_Provider, "Nullable_Color_Parameter_In_Color_Code", new EventResult<AccessTokenEntity>() {
    @Override
    public void success(AccessTokenEntity result) {
        //Your Success Code
    }

    @Override
    public void failure(WebAuthError error) {
		//Your Failure Code
    }
 });
```
where social provider may be either facebook, google, linkedin or any other providers

You can get the url for the social login and you can load in your favorite browser for this you can call 

```java
Cidaas.getInstance(yourContext).getSocialLoginURL(yourRequestid,your_social_provider,new EventResult<String>() {
    @Override
    public void success(String result) {
       //Your Success Code
    }

    @Override
    public void failure(WebAuthError error) {
       	//Your Failure Code
    }
});

```
where social provider may be either "facebook", "google", "linkedin" or any other providers

#### Register in Browser

To Register in Browser , you can call the following method

```java
Cidaas.getInstance(yourContext).RegisterWithBrowser(youraAtivityContext, "Nullable_Color_Parameter_In_Color_Code", new EventResult<AccessTokenEntity>() {
    @Override
    public void success(AccessTokenEntity result) {
         //Your Success Code
    }

    @Override
    public void failure(WebAuthError error) {
    	//Your Failure Code
    }
});
```        

You can get the url for the registeration and you can load in your favorite browser for this you can call 

```java
Cidaas.getInstance(yourContext).getRegistrationURL(new EventResult<String>() {
    @Override
    public void success(String result) {
         //Your Success Code
    }

    @Override
    public void failure(WebAuthError error) {
          //Your Failure Code
    }
});

```
#### Add custom scheme

Use [customScheme](https://developer.android.com/training/app-links/deep-linking) or [App Link](https://developer.android.com/studio/write/app-link-indexing) to return back the control from browser to App.

    Note : Don't forget to add the custom scheme url in your App's redirect url section



If you use deep link or custom scheme, After adding intent-filter in the manifest file , add  **android:launchMode="singleTop"** in the activity you redirected, For example if you redirect you link from browser to mainactivity

```java
 <activity android:name=".MainActivity" android:launchMode="singleTop">
 ```
 and resume the SDK from your activity
```java

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String token = intent.getDataString();
        if (token != null) {
            cidaas.handleToken(token);
            
        } else {
           //your else part
        }
    }

}
```

If you use app links, configure your Domain setup and resume the SDK from your activity


```java
  @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String token = intent.getDataString();
        if (token != null) {
            cidaas.handleToken(token);
            
        } else {
           //your else part
        }
    }
}
```
#### Common Methods
##### Getting AccessToken

You can get the access token for the current user using the following call

```Java
cidaas.getAccessToken(yourSub, new EventResult<AccessTokenEntity>() {
 @Override
 public void success(AccessTokenEntity result) {
      //Your Success Code
  }

  @Override
  public void failure(WebAuthError error) {
       //Your Failure Code
   }
  }); 
```

By Default the access token is renewed when you call the getAccessTokenMethod , If you want to renew token manually you need to call the Following method

```Java
cidaas.getAccessTokenFromRefreshToken(refreshToken, new EventResult<AccessTokenEntity>() {
   @Override
   public void success(AccessTokenEntity result) {
      // Your Success Code                
   }

   @Override
   public void failure(WebAuthError error) {
      // Your Failure Code
    }
  });
        
```

##### Enable PKCE flow

  By Default PKCE flow is enabled , If you want to disable the PKCE flow , you can call the following method
  
  ```Java
Cidaas.getInstance(yourContext).setENABLE_PKCE(false);
```
> ##### Note:- If you Disable the PKCE flow, you must use add the 'ClientSecret' in your cidaas.xml


To know whether the PKCE is enabled or not, use the following method
 ```Java
 Cidaas.getInstance(yourContext).isENABLE_PKCE();
 ```
it will return a boolean value ,return true if PKCE is enabled and false if disabled

##### Enable Log

  By Default Log is disabled , If you want to enable the log , you can call the following method
  
  ```Java
  Cidaas.getInstance(yourContext).enableLog();
        
```
To disable the Log

  ```Java
Cidaas.getInstance(yourContext).disableLog();
        
```

To know whether the Log is enabled or not, use the following method
 ```Java
Cidaas.getInstance(yourContext).isLogEnable();
 ```
it will return a boolean value ,return true if Log is enabled and false if disabled

##### User information

```Java
Cidaas.getInstance(yourContext).getUserInfo("yourSub", new EventResult<UserinfoEntity>() {
 @Override
   public void success(UserinfoEntity result) {
      //Your Success Code
     }

 @Override
  public void failure(WebAuthError error) {
      //Your Failure code
     }
});
```        


### Embedded Browser Login

You can use embedded browser to login with cidaas , For this do the following steps
1. You have to add '< uses-permission  android:name="android.permission.ACCESS_NETWORK_STATE"/>' in your manifest file.
2. Create an instance for CidaasSDKLayout using the activity context.

```Java
  CidaasSDKLayout cidaasSDKLayout = CidaasSDKLayout.getInstance(this);
  
     or
  
  CidaasSDKLayout cidaasSDKLayout = new CidaasSDKLayout(this);

```
3.Call ****loginWithEmbeddedBrowser()**** .

4.Pass the relative layout as argument

```Java
 RelativeLayout relativeLayout=findViewById(R.id.relative_layout_for_webView);
 
 cidaasSDKLayout.loginWithEmbeddedBrowser(relativeLayout, new EventResult<AccessTokenEntity>() {
 @Override
 public void success(AccessTokenEntity result) {
      //Your Success Code
  }

  @Override
  public void failure(WebAuthError error) {
       //Your Failure Code
   }
  }); 
```

##### Get Access Token From social
If you have access token for google or facebook, then you can use following method to get the access token of cidaas.


```Java
SocialAccessTokenEntity socialAccessTokenEntity = new SocialAccessTokenEntity();
socialAccessTokenEntity.setToken(yourtoken);
socialAccessTokenEntity.setDomainURL(yourDomainURL);
socialAccessTokenEntity.setProvider(yourprovider);
socialAccessTokenEntity.setViewType(yourviewType);

Cidaas.getInstance(yourActivityContext).getAccessTokenBySocial(socialAccessTokenEntity, new EventResult<AccessTokenEntity>() {
    @Override
    public void success(AccessTokenEntity result) {
        //Your Success Code
    }

    @Override
    public void failure(WebAuthError error) {
       //Your Failure code
    }
 });

```
here yourviewType is "login" and provider is "google" or "facebook" 