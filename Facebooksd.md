# Facebook Native SDK

The steps here will guide you through setting up Facebook sign in via native app in your android mobile using the cidaas android SDK.

## Table of Contents

<!--ts-->
* [Facebook Native SDK](#facebook-native-sdk)
     <!--ts-->
    * [Creating app](#creating-facebook-app)
    * [Login after Consent](#steps-to-be-followed-in-code)
    <!--te-->
<!--te-->


#### Initialisation

The first step of integrating the Cidaas Facebook native sdkP is the initialisation process.
```java
 
CidaasFacebook cidaasFacebook= CidaasFacebook.getInstance(your Activity Context);

or

CidaasFacebook cidaasFacebook = new CidaasFacebook(your Activity Context);

```

#### Facebook native SDK

If you want to use your Facebook app in your mobile to sign in with cidaas please use the following steps

#### Creating Facebook App

The following steps are to be followed to create a **Facebook App**

1. Go to [Facebook Developers Console](https://developers.facebook.com/), and login with your facebook , go to myapp

2. Add a new App then give it a valid name and click **Create App Id** and  Take note of the App ID

3. On the left side, you have the navigation drawer. Click **Settings** and then Basic

4. Click **Add Platform** and Choose Android

5. Enter your project's package name and other details

6. Turn on **Single Sign On** and click **Save changes**

7. Go to strings.xml and add the client id
```xml

   <string name="facebook_app_id">your Facebook Client id</string>

```

#### Steps to be followed in code

Once you create android app  in Facebook developer console successfully,

You can get the requestID from the cidaas native sdk

```java
  cidaasFacebook.login(requestId,new EventResult<AccessTokenEntity>(){
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

After that override the onActivityResult method, Please check for request code to 9001(9001 for Google sdk ) if not call cidaasFacebook authorize method

```java
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9001) {
            cidaasFacebook.authorize(requestCode, resultCode, data);
        }
         else
        {
            cidaasFacebook.authorize(requestCode, resultCode, data);
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
