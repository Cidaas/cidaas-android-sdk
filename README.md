# Cidaas Android SDK V2

[![Build Status](https://travis-ci.org/Cidaas/cidaas-sdk-android-v2.svg?branch=development)](https://travis-ci.org/Cidaas/cidaas-sdk-android-v2) 
[![codecov.io](https://codecov.io/gh/Cidaas/cidaas-sdk-android-v2/branch/development/graph/badge.svg)](https://codecov.io/gh/Cidaas/cidaas-sdk-android-v2/branch/development)
[![jitpack](https://jitpack.io/v/Cidaas/cidaas-sdk-android-v2.svg)](https://jitpack.io/#Cidaas/cidaas-sdk-android-v2)
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
    * [Native UI Integration](/app/PureNative.md)
    <!--te-->

## Requirements

    minSdkVersion 21

### Steps for integrate native Android SDKs:
### Installation


Cidaas SDK is available through jitpack.io.  Please ensure that you are using the latest versions by [checking here](https://jitpack.io/#Cidaas/cidaas-v2-sdk-android)

Add the following Gradle configuration to your Android project:

```java        
allprojects {
    repositories {
        ...
		maven { url 'https://jitpack.io' }
	}
}
```		
 Add the dependency to app module
 ```java
dependencies {
 implementation 'com.github.Cidaas:cidaas-v2-sdk-android:1.0.2'
}
 ```
 ## Getting started
 
 Create an asset folder by right click the app module goto new->folder->Assets folder and click the finish button

Create an xml file named as <b>Cidaas.xml</b> inside the asset folder and fill all the inputs in key value pair. The inputs are below mentioned.

> ##### Note:- The File name must be Cidaas.xml 

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

Cidaas cidaas = Cidaas.getInstance(your Activity Context);

or

Cidaas cidaas =new Cidaas(your Activity Context); 

```

### Usage

> ##### Note:- If you are going to use Native browser login, you must use getInstance() method to create cidaas instance


#### Native Browser Login 
#### Classic Login
You can login using your native browser and redirects to the App once successfully logged in. To login with your native browser call ****loginWithBrowser()****.

```java
 cidaas.loginWithBrowser(yourContext, "NullableColorParameterInColorCode", new Result<AccessTokenEntity>() {
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

#### Social Login
You can also perform social login using your native browser and redirects to the App once successfully logged in. To perform social login call ****loginWithSocial()****.

```swift
 cidaas.loginWithSocial(yourContext, yourSocialProvider, "optionalColorParameterInColorCode", new Result<AccessTokenEntity>() {
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

Use [customScheme](https://developer.android.com/training/app-links/deep-linking) or [App Link](https://developer.android.com/studio/write/app-link-indexing) to return back the control from browser to App.

    Note : Don't forget to add the custom scheme url in your App's redirect url section


If you use deep link or custom scheme, add intent in manifest and resume the SDK from your activite's **onCreate** method

```java
String token = getIntent().getDataString();
if (token != null) {
	cidaas.handleToken(token);
}
```

If you use app links, configure your Domain setup and resume the SDK from your activite's **onCreate** method


```java
 String token = getIntent().getDataString();
 if (token != null) {
 	cidaas.handleToken(token);
}
```

