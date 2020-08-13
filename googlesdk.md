# Google Native SDK

The steps here will guide you through setting up google sign in via native app in your android mobile using the cidaas android SDK.

## Table of Contents

<!--ts-->
* [Google Native SDK](#google-native-sdk)
     <!--ts-->
    * [Creating app](#creating-google-app)
    * [Steps to be followed in code](#steps-to-be-followed-in-code)
    <!--te-->
<!--te-->


#### Initialisation

The first step of integrating the Cidaas google native sdkP is the initialisation process.
```java
 
CidaasGoogle cidaasGoogle= CidaasGoogle.getInstance(your Activity Context);

or

CidaasGoogle cidaasGoogle = new CidaasGoogle(your Activity Context);

```

#### Google native SDK

If you want to use your google app in your mobile to sign in with cidaas please use the following steps

#### Creating Google App

The following steps are to be followed to create a **Google App**

1. Go to [Google Developers Console](https://developers.google.com/identity/sign-in/android/start), and click **Configure a project** button

2. A Popup will come , create the project with a name and accept the terms and conditions , Choose android in Where you are coming from and give the package name and SHA-1 Key details

3. Once you logged in google console , Create a webclient For OAuth2 and fill the details including redirect url , After that click download button for webclient, 

4. A json file is downloaded , rename it with  google-services.json and put in inside the assets folder(where cidaas.xml exists) 

5. Go to strings.xml and add the client id
```xml

 <string name="server_client_id">your Google Client id.apps.googleusercontent.com</string>

```

#### Steps to be followed in code

Once you create android client and web client in google developer console successfully,

You can get the requestID from the cidaas native sdk

```java
  cidaasGoogle.login(requestId,new EventResult<AccessTokenEntity>(){
            @Override
            public void success(AccessTokenEntity result) {
               // Your success code
            }

            @Override
            public void failure(WebAuthError error) {
               // Your Failure code 
            }
        });
```

After that override the onActivityResult method, Please check for request code to 9001 and call cidaasGoogle authorize method

```java
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9001) {
            cidaasGoogle.authorize(requestCode, resultCode, data);
        }

       cidaas.onActivityResult(requestCode,resultCode,data);
    }

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
