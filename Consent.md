# Consent Management

The steps here will guide you through setting up and managing Consent in your apps using cidaas SDK.

## Table of Contents

<!--ts-->
* [Consent](#consent-management)
     <!--ts-->
    * [Get Consent details](#getting-consent-details)
    * [Login after Consent](#login-after-consent)
    <!--te-->
<!--te-->


#### Initialisation

The first step of integrating consent in cidaas sdk is the initialisation process.
```java

CidaasConsent cidaasConsent= CidaasConsent.getInstance(your Application Context);

or

CidaasConsent cidaasConsent =new CidaasConsent(your Application Context);

```

#### Consent Management

Once user has successfully logged in, you may want your user's to accept the terms and conditions. You can configure different consent forms during setup, and present that to the user after login.

#### Getting Consent Details

To get the consent details call **getConsentDetails()**.


```java

cidaasConsent.getConsentDetails(consentName,new Result < ConsentDetailsResultEntity > () {
 @Override
 public void success(ConsentDetailsResultEntity result) {
  //Your success code here
 }
 @Override
 public void failure(WebAuthError error) {
  //Your failure code here
 }
});
```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "_id":"3543trr",
        "decription":"test consent",
        "title":"test",
        "userAgreeText":"term and condition",
        "url":"https://acb.com"
    }
}
```

#### Login After Consent

For accept the consent you need to call ****loginAfterConsent()****

```java
cidaasConsent.loginAfterConsent(ConsentEntity consentEntity, new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //Your failure code here
 }
});
```


**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "token_type": "Bearer",
        "sub": "51701ec8-f2d7-4361-a727-f8df476a711a",
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTVmNDktNGZlYS04MGNlLTZmYTkzMzk2YjI4NyJ9*****",
        "session_state": "CNT7GGALeoKyTF6Og-cZHAuHUJBQ20M0jLL35oh3UGk.vcNxCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```
