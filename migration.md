# Cidaas Android SDK V2

[![Build Status](https://travis-ci.org/Cidaas/de.cidaas-sdk-android-v2.svg?branch=development)](https://travis-ci.org/Cidaas/de.cidaas-sdk-android-v2)
[![codecov.io](https://codecov.io/gh/Cidaas/de.cidaas-sdk-android-v2/branch/development/graph/badge.svg)](https://codecov.io/gh/Cidaas/de.cidaas-sdk-android-v2/branch/development)
[![jitpack](https://jitpack.io/v/Cidaas/de.cidaas-sdk-android-v2.svg)](https://jitpack.io/#Cidaas/de.cidaas-sdk-android-v2)
[![Platform](https://img.shields.io/badge/Platforms-android-4E4E4E.svg?colorA=28a745)](#installation)


The steps here will guide you to handle the migration changes

> ##### Note:- The Cidaas.xml will be renamed to cidaas.xml 
>
Before the Cidaas Will available as a single package , now it is split into multiple sub-modules for your convenience 

## Cidaas Native
The following Methods under Cidaas are now available in Cidaas Native 

<!--ts-->
* [Getting RequestId](/PureNative.md#getting-request-id)
* [Getting Tenant Information](/PureNative.md#getting-tenant-info)
* [Getting Client Information](/PureNative.md#get-client-info)
* [Login](/PureNative.md#login)
    <!--ts-->
    * [Login with credentials](/PureNative.md#login-with-credentials)
    <!--te-->
* [Registration](/PureNative.md#registration)
    <!--ts-->
    * [Getting Registration Fields](/PureNative.md#getting-registration-fields)
    * [Register user](/PureNative.md#register-user)
    <!--te-->
* [De-duplication](/PureNative.md#de-duplication)
    <!--ts-->
    * [Get Deduplication Details](/PureNative.md#get-deduplication-details)
    * [Register user](/PureNative.md#register-user-1)
    * [Login With Deduplication](/PureNative.md#login-with-deduplication)
    <!--te-->
* [Account Verification](/PureNative.md#account-verification)
    <!--ts-->
    * [Initiate Email verification](/PureNative.md#initiate-email-verification)
    * [Initiate SMS verification](/PureNative.md#initiate-sms-verification)
    * [Initiate IVR verification](/PureNative.md#initiate-ivr-verification)
    * [Verify Account](/PureNative.md#verify-account)
    <!--te-->
* [Forgot Password](/PureNative.md#forgot-password)
    <!--ts-->
    * [Initiate Reset Password](/PureNative.md#initiate-reset-password)
    * [Handle Reset Password](/PureNative.md#handle-reset-password)
    * [Reset Password](/PureNative.md#reset-password)
    <!--te-->
* [Consent](/PureNative.md#consent-management)
     <!--ts-->
    * [Get Consent details](/PureNative.md#getting-consent-details)
    * [Login after Consent](/PureNative.md#login-after-consent)
    <!--te-->
<!--te-->

## Cidaas Verification

The following Methods under Cidaas are now available in Cidaas Verification

* [Face](/Verification-v2.md#face-recognition)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-face-recognition)
    * [Usage](/Verification-v2.md#login-via-face-recognition)
    <!--te-->
* [Voice](/Verification-v2.md#voice-recognition)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-voice-recognition)
    * [Usage](/Verification-v2.md#login-via-voice-recognition)
    <!--te-->
* [Fingerprint](/Verification-v2.md#fingerprint-verification)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-fingerprint)
    * [Usage](/Verification-v2.md#login-via-fingerprint-verification)
    <!--te-->
* [Pattern](/Verification-v2.md#pattern-recognition)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-pattern-recognition)
    * [Usage](/Verification-v2.md#login-via-pattern-recognition)
    <!--te-->
* [Smart Push](/Verification-v2.md#smartpush-notification)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-smartpush-notification)
    * [Usage](/Verification-v2.md#login-via-smartpush-notification)
    <!--te-->
* [TOTP](/Verification-v2.md#totp)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-totp)
    * [Usage](/Verification-v2.md#login-via-totp)
    <!--te-->
* [Email](/Verification-v2.md#email)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-email)
    * [Usage](/Verification-v2.md#login-via-email)
    <!--te-->
* [SMS](/Verification-v2.md#sms)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-sms)
    * [Usage](/Verification-v2.md#login-via-sms)
    <!--te-->
* [IVR](/Verification-v2.md#ivr)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-ivr)
    * [Usage](/Verification-v2.md#login-via-ivr)
    <!--te-->
* [Backupcode](/Verification-v2.md#backupcode)
    <!--ts-->
    * [Configuration](/Verification-v2.md#configure-backupcode)
    * [Usage](/Verification-v2.md#login-via-backupcode)
    <!--te--> 
    
## Cidaas Consent
 The following Methods under Cidaas are now available in Cidaas Consent
 
 * [Consent](#consent-management)
      <!--ts-->
     * [Get Consent details](/Consent.md#getting-consent-details)
     * [Login after Consent](/Consent.md#login-after-consent)
     <!--te-->
     
     
  #Cidaas
  
  The following methods are available in Cidaas 
  
  
  * [Native Browser Login](#native-browser-login)
         <!--ts-->
         * [Classic Login](#classic-login)
         * [Social Login](#social-login)
         <!--te-->
     *  [Embedded Browser Login](#embedded-browser-login)
     * [Native UI Integration](/PureNative.md)
     * [Verification v2](/Verification-v2.md)
     <!--te-->
     