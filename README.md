# Cidaas Android SDK V2

The steps here will guide you through setting up and managing authentication and authorization in your apps using cidaas SDK.

https://docs.cidaas.de/

## Requirements

    minSdkVersion 21

## Add cidaas sdk to your project

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
 implementation 'com.github.Cidaas:cidaas-v2-sdk-android:0.0.0.3'
}
 ```
 ## Getting started

Create a xml file named as <b>Cidaas.xml</b> and fill all the inputs in key value pair. The inputs are below mentioned.

> ##### Note:- The File name must be cidaas.xml 

The xml file should be like this:

``` 
<?xml version="1.0" encoding="utf-8"?>
<resources>
<item name="DomainURL" type="string">DomainURL</item>
<item name="ClientId" type="string">ClientId</item>
<item name="RedirectURL">RedirectURL</item>
</resources> 

```
### Getting client Id and urls

You can get the property file for your application from the cidaas AdminUI.


### Steps for integrate native Android SDKs:

#### Initialisation

The first step of integrating cidaas sdk is the initialisation process.
```java

Cidaas cidaas = Cidaas.getInstance(getApplicationContext);

or

Cidaas cidaas =new Cidaas(getApplicationContext); 

```

### Usage

#### Getting Request Id

All the login and registration process done with the help of requestId. To get the requestId, call ****getRequestId()****.
```java

cidaas.getRequestId(new Result < AuthRequestResponseEntity > () {
@Override
public void success(AuthRequestResponseEntity result) {

// Your success code here

}

@Override
public void failure(WebAuthError error) {

// Your failure code here

}
});

```
**Response:**

```json
{
    "success":true,
    "status":200,
    "data": {
        "groupname":"default",
        "lang":"en-US,en;q=0.9,de-DE;q=0.8,de;q=0.7",
        "view_type":"login",
        "requestId":"45a921cf-ee26-46b0-9bf4-58636dced99f”
    }
}
```

#### Getting Tenant Info

It is more important to get the tenant information such as Tenant name and all login types ('Email', 'Mobile', 'Username') available for particular tenant. To get the tenant information, call ****getTenantInfo()****.


```java

cidaas.getTenantInfo(new Result < TenantInfoEntity > () {

@Override
public void success(TenantInfoEntity result) {

// Your success code here

}

@Override
public void failure(WebAuthError error) {

// Your failure code here

}
});

```
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "tenant_name": "Cidaas Management",
        "allowLoginWith": [
            "EMAIL",
            "MOBILE",
            "USER_NAME"
        ]
    }
}
```

#### Get Client Info

After getting tenant information, you need to get client information that contains client name, logo url specified for the client in the Admin's Apps section, and all the social providers configured for the App. To get client information, call ****getClientInfo()****.

```java
cidaas.getClientInfo("your RequestId", new Result < ClientInfoEntity > () {
@Override
public void success(ClientInfoEntity result) {

// Your success code here

}

@Override
public void failure(WebAuthError error) {

// Your failure code here

}
});

```
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "passwordless_enabled": true,
        "logo_uri": "https://www.cidaas.com/wp-content/uploads/2018/02/logo-black.png",
        "login_providers": [
            "facebook",
            "linkedin"
        ],
        "policy_uri": "",
        "tos_uri": "",
        "client_name": "demo-app"
    }
}

```

#### Registration
#### Getting Registration Fields

Before registration, you need to know what are all the fields must show to the user while registration. For getting the fields, call ****getRegistrationFields()****.

```java
cidaas.getRegistrationFields("Your_RequestId","Your_locale" ,new Result < RegistrationSetupResponseEntity > () {
@Override
public void success(RegistrationSetupResponseEntity result) {
// your success code here

}

@Override
public void failure(WebAuthError error) {
// your failure code here

}
```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": [{
        "dataType": "EMAIL",
        "fieldGroup": "DEFAULT",
        "isGroupTitle": false,
        "fieldKey": "email",
        "fieldType": "SYSTEM",
        "order": 1,
        "readOnly": false,
        "required": true,
        "fieldDefinition": {},
        "localeText": {
            "locale": "en-us",
            "language": "en",
            "name": "Email",
            "verificationRequired": "Given Email is not verified.",
            "required": "Email is Required"
        }
    },
    {
        "dataType": "TEXT",
        "fieldGroup": "DEFAULT",
        "isGroupTitle": false,
        "fieldKey": "given_name",
        "fieldType": "SYSTEM",
        "order": 2,
        "readOnly": false,
        "required": true,
        "fieldDefinition": {
            "maxLength": 150
        },
        "localeText": {
            "maxLength": "Givenname cannot be more than 150 chars",
            "required": "Given Name is Required",
            "name": "Given Name",
            "language": "en",
            "locale": "en-us"
        }
    }]
}
```

#### Register user

Registration is the most important thing for all. To register a new user, call ****registerUser()****.
```java 

RegistrationEntity registrationEntity = new RegistrationEntity();

registrationEntity.setUsername("davidjhonson");
registrationEntity.setEmail("davidjhonson@gmail.com");
registrationEntity.setGiven_name("David");
registrationEntity.setFamily_name("Jhonson");
registrationEntity.setPassword("123456");
registrationEntity.setPassword_echo("123456");
registrationEntity.setMobile_number("+919876553230");
Date date = new Date();
date.setDate(27 / 12 / 1994);
registrationEntity.setBirthdate(date);
registrationEntity.setGender("Male");
registrationEntity.setWebsite("http://google.com");


RegistrationCustomFieldEntity registrationCustomFieldEntity = new RegistrationCustomFieldEntity();
registrationCustomFieldEntity.setKey("Pincode");
registrationCustomFieldEntity.setValue("123456");
registrationCustomFieldEntity.setDataType("String");
registrationCustomFieldEntity.setId("pincode");
registrationCustomFieldEntity.setInternal(true);

Dictionary < String, RegistrationCustomFieldEntity > customFileds = new Hashtable < > ();
customFileds.put(registrationCustomFieldEntity.getKey(), registrationCustomFieldEntity);
registrationEntity.setCustomFields(customFileds);

cidaas.registerUser("Yoour_requestId", registrationEntity, new Result < RegisterNewUserResponseEntity > () {

@Override
public void success(RegisterNewUserResponseEntity result) {
//Your success code here
}

@Override
public void failure(WebAuthError error) {
//Your failure code here
}

```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
     	"track_id":"45a921cf-ee26-46b0-9bf4-58636dced99f",
        "sub": "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d",
        "userStatus": "VERIFIED",
        "email_verified": false,
        "suggested_action": "DEDUPLICATION"
    }
}
```

After you get the success response from the ****registerUser()****, You may get a suggested_action like ****"DEDUPLICATION"**** in the data of success respone. At that time, you have to follow the following steps

#### De-duplication
 User de-duplication is a process that eliminates redundant copies of user thus reducing storage overhead as well as other inefficiencies. This process can be triggered during registion itself by following next steps.

When a user is being registered system does a de-duplication check, to verify if that is already existing. System then shows the list of potential duplicate users whose data seems to match most of the information entered during this registration. System then gives an option to the user to use one of the found duplicate record or reject all of them and register this new values as a fresh user.

In order to implement above functionality few of the below methods have to be called.

#### Get Deduplication Details

To get the list of similar users, call ****getDeduplicationDetails()**** . If this method is used, system uses some heuristic algorithms and finds out any similar user exists in system and returns them.

> #### Note :- You can get the track id from thein the data of success respone of registerUser().

```java
 cidaas.getDeduplicationDetails("your_track_id", new Result < DeduplicationResponseEntity > () {
  @Override
  public void success(DeduplicationResponseEntity result) {
   //Your success code here.
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
        "email": "xxx@gmail.com",
        "deduplicationList": [
        {
            "provider": "SELF",
            "sub": "39363935-4d04-4411-8606-6805c4e673b4",
            "email": "xxx********n2716@g***l.com",
            "emailName": "xxx********n2716",
            "firstname": "xxx",
            "lastname": "yyy",
            "displayName": "xxx yyy",
            "currentLocale": "IN",
            "country": "India",
            "region": "Delhi",
            "city": "Delhi",
            "zipcode": "110008"
        },
        {
            "provider": "SELF",
            "sub": "488b8128-5584-4c25-9776-6ed34c6e7017",
            "email": "xx****n21@g***l.com",
            "emailName": "xx****n21",
            "firstname": "xxx",
            "lastname": "yyy",
            "displayName": "xxx yyy",
            "currentLocale": "IN",
            "country": "India",
            "region": "Delhi",
            "city": "Delhi",
            "zipcode": "110008"
        }]
    }
}
```

#### Register User

While registering user, if system found similar users already registered,that list is shown to user. User can decide whether to use one of the existing logins, or choose to ignore all shown details. ****registerUser()**** method can be called to ignore shown result and register details in registration form as a new user.

```java
cidaas.registerUser("your track id", new Result < RegisterDeduplicationEntity > () {
 @Override
 public void success(RegisterDeduplicationEntity result) {
  //Your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //Your failure code here
 }
});
```

**Response:**

```java
{
    "success": true,
    "status": 200,
    "data": {
        "sub": "51701ec8-f2d7-4361-a727-f8df476a711a",
        "userStatus": "VERIFIED",
        "email_verified": false,
        "suggested_action": "LOGIN"
    }
} 
```


#### Login With Deduplication

While registering user, if system found similar users already registered,that list is shown to user. User can decide whether to use one of the existing logins, or choose to ignore all shown details. ****loginWithDeduplication()**** method can be called to use one of those existing logins shown by the system. Note that, System will still use the secure authentication and verifications that were setup for earlier user, before login.

```java
cidaas.loginWithDeduplication("your_sub", "your_password", new Result < LoginDeduplicationResponseEntity > () {
 @Override
 public void success(LoginDeduplicationResponseEntity result) {
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTVmNDktNGZlYS04MGNlLTZmYTkzMzk2YjI4NyJ9*****",
        "session_state": "CNT7GGALeoKyTF6Og-cZHAuHUJBQ20M0jLL35oh3UGk.vcNxCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```
#### Account Verification

Once registration completed, you need to verify your account either by Email or SMS or IVR verification call. First you need to initiate the account verification.

#### Initiate Email verification

To receive a verification code via Email, call **initiateEmailVerification()**.

```java
cidaas.initiateEmailVerification("your_sub", "your_requestId", new Result < RegisterUserAccountInitiateResponseEntity > () {
@Override
public void success(RegisterUserAccountInitiateResponseEntity result) {
//your Success Code
}

@Override
public void failure(WebAuthError error) {
 //your failure code
}
});

```
#### Initiate SMS verification

To receive a verification code via SMS, call **initiateSMSVerification()**.

```java



cidaas.initiateSMSVerification("Your_sub", "Your_requestId", new Result<RegisterUserAccountInitiateResponseEntity>() {

@Override
public void success(RegisterUserAccountInitiateResponseEntity result) {

//Your success code here

}


@Override
public void failure(WebAuthError error) {

//Your failure code here

}

});


```
#### Initiate IVR verification

To receive a verification code via IVR verification call, call **initiateIVRVerification()**.

```java

cidaas.initiateIVRVerification("your_sub", "your_requestId", new Result < RegisterUserAccountInitiateResponseEntity > () {

@Override
public void success(RegisterUserAccountInitiateResponseEntity result) {
//Your Success code here

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
        "accvid":"353446"
    }
}
```

#### Verify Account

Once you received your verification code via any of the mediums like Email, SMS or IVR, you need to verify the code. For that verification, call **verifyAccount()**.
```java

cidaas.verifyAccount("your code", new Result < RegisterUserAccountVerifyResponseEntity > () {

@Override
public void success(RegisterUserAccountVerifyResponseEntity result) {

//your success code here

}

@Override
public void failure(WebAuthError error) {

//your failure code here

}
});

```
**Response:**

```json
{
    "success": true,
    "status": 200
}
```

#### Login
#### Login with credentials

To login with your username and password, call ****loginWithCredentials()****.
```java

LoginEntity loginEntity = new LogintEntity();

loginEntity.setUsername("davidjhonson@gmail.com");
loginEntity.setPassword("123456");
loginEntity.setUsername_type("email");

cidaas.loginWithCredentials("Your RequestId", loginEntity, new Result < LoginEntity > () {

@Override
public void success(LoginEntity result) {
  // Your success code here
}

@Override
public void failure(WebAuthError error) {
  // Your failure code here
}

```
**Response:**
```json
{
    "success": true,
    "status": 200,
    "data": {
        "token_type": "Bearer",
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### Forgot Password

There is an option to reset password if password is forgotten.

#### Initiate Reset Password

For resetting password, you can get a verification code either via Email or SMS. To do that, you can call initateRestPassword().

****initateRestPassword()****.
```java
//object initiations
ResetPasswordRequestEntity resetPasswordRequestEntity = new ResetPasswordRequestEntity();

resetPasswordRequestEntity.setEmail("Your_email_id");
resetPasswordRequestEntity.setResetMedium("email");

cidaas.initiateResetPassword("Your_requestId", resetPasswordRequestEntity, new Result < ResetPasswordResponseEntity > () {
@Override
public void success(ResetPasswordResponseEntity result) {
   //Your success code here
}

@Override
public void failure(WebAuthError error) {
    //Your failure code here.
}
});

```
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "rprq": "f595edfb-754e-444c-ba01-6b69b89fb42a",
        "reset_initiated": true
    }
}

```

#### Handle Reset Password

Once verification code received, verify that code by calling ****handleRestPassword()****.
```java

cidaas.handleResetPassword("your verificaton code", new Result < ResetPasswordValidateCodeResponseEntity > () {

@Override
public void success(ResetPasswordValidateCodeResponseEntity result) {
    //Your suucees code here.
}

@Override
public void failure(WebAuthError error) {
    //your failure code here.
}
});

```
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "exchangeId": "1c4176bd-12b0-4672-b20c-9616e93457ed",
        "resetRequestId": "f595edfb-754e-444c-ba01-6b69b89fb42a"
    }
}
```

#### Reset Password

Once code is verified, reset your password with your new password. To reset your password, call ****restPassword()****.

```java

cidaas.resetPassword("your_password", "your_confirm_password", new Result < ResetNewPasswordResponseEntity > () {
@Override
public void success(ResetNewPasswordResponseEntity result) {
    //Your success code here.
}

@Override
public void failure(WebAuthError error) {
    //Your failure code here.
}
});

``
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "reseted":true
    }
}
```

#### Passwordless or Multifactor Authentication

#### Email

To use your Email as a passwordless login, you need to configure your Email first and verify your Email. If you already verify your Email through account verification, by default Email will be configured. 

#### Configure Email

To receive a verification code via Email, call **configureEmail()**.
```java

cidaas.configureEmail("your sub", new Result < SetupEmailMFAResponseEntity > () {
@Override
public void success(SetupEmailMFAResponseEntity result) {
    //Your success code here
}

@Override
public void failure(WebAuthError error) 
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
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Enroll Email by entering code

Once you received your verification code via Email, you need to verify the code. For that verification at the time of configuration, call **enrollEmail()**.
```java
cidaas.enrollEmail(code, new Result < EnrollEmailMFAResponseEntity > () {

@Override
public void success(EnrollEmailMFAResponseEntity result) {
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
        "sub": "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d",
        "trackingCode":"5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Email

Once you configured your Email, you can also login with your Email via Passwordless authentication. To receive a verification code via Email, call **loginWithEmail()**.
```java

cidaas.loginWithEmail(code, trackId, UsageType.MFA, new Result < LoginCredentialsResponseEntity > () {

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
        "statusId": "6f7e672c-1e69-4108-92c4-3556f13eda74"
    }
}
```

#### Verify Email by entering code

Once you received your verification code via Email, you need to verify the code. For that verification, call **verifyEmail()**.
```java

cidaas.verifyEmail(code, new Result < LoginCredentialsResponseEntity > () {
@Override
public void success(LoginCredentialsResponseEntity result) {
  // Your Success code here
}

@Override
public void failure(WebAuthError error) {
  // Your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### SMS

To use SMS as a passwordless login, you need to configure SMS physical verification first and verify your mobile number. If you already verify your mobile number through account verification via SMS, by default SMS will be configured. 

#### Configure SMS

To receive a verification code via SMS, call **configureSMS()**.

```java

cidaas.configureSMS(sub, new Result < SetupSMSMFAResponseEntity > () {
 @Override
 public void success(SetupSMSMFAResponseEntity result) {
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
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Verify SMS by entering code

Once you received your verification code via SMS, you need to verify the code. For that verification, call **enrollSMS()**.

```java
cidaas.enrollSMS(code, new Result < EnrollSMSMFAResponseEntity > () {
 @Override
 public void success(EnrollSMSMFAResponseEntity result) {
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
        "sub": "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d",
        "trackingCode":"5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via SMS

Once you configured SMS, you can also login with SMS via Passwordless authentication. To receive a verification code via SMS, call **loginWithSMS()**.
```java
cidaas.loginWithSMS(sub, trackId, UsageType.MFA, new Result < LoginCredentialsResponseEntity > () {
   @Override
   public void success(LoginCredentialsResponseEntity result) {
    //Your success code here
   }

   @Override
   public void failure(WebAuthError error) {
    //Your failure code here
   }); 
    
```
**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "6f7e672c-1e69-4108-92c4-3556f13eda74"
    }
}
```

#### Verify SMS by entering code

Once you received your verification code via SMS, you need to verify the code. For that verification, call **verifySMS()**.

```java
cidaas.verifySMS(code, new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  // Your Success code here
 }
 @Override
 public void failure(WebAuthError error) {
  // Your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### IVR

To use IVR as a passwordless login, you need to configure IVR physical verification first and verify your mobile number. If you already verify your mobile number through account verification via IVR, by default IVR will be configured. 

#### Configure IVR

To receive a verification code via IVR, call **configureIVR()**.
```java
cidaas.configureIVR(sub, new Result < SetupIVRMFAResponseEntity > () {
 @Override
 public void success(SetupIVRMFAResponseEntity result) {
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
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Verify IVR by entering code

Once you received your verification code via IVR verification call, you need to verify the code. For that verification, call **enrollIVR()**.

```java

cidaas.enrollIVR(code, new Result < EnrollIVRMFAResponseEntity > () {
 @Override
 public void success(EnrollIVRMFAResponseEntity result) {
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
        "sub": "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d",
        "trackingCode":"5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via IVR

Once you configured IVR, you can also login with IVR via Passwordless authentication. To receive a verification code via IVR verification call, call **loginWithIVR()**.

```java
cidaas.loginWithIVR(sub, trackId, UsageType.MFA, new Result < LoginCredentialsResponseEntity > () {
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
        "statusId": "6f7e672c-1e69-4108-92c4-3556f13eda74"
    }
}
```

#### Verify IVR by entering code

Once you received your verification code via IVR, you need to verify the code. For that verification, call **verifyIVR()**.

```java

cidaas.verifyIVR(code, new Result < LoginCredentialsResponseEntity > () {
@Override
public void success(LoginCredentialsResponseEntity result) {
        // Your Success code here
}

@Override
public void failure(WebAuthError error) {
       // Your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### BackupCode

To use Backupcode as a passwordless login, you need to configure Backupcode physical verification first.

#### Configure BackupCode

To configure or view the Backupcode, call **configureBackupcode()**.
```java

cidaas.configureBackupcode(sub, new Result < SetupBackupcodeMFAResponseEntity > () {
 @Override
 public void success(SetupBackupcodeMFAResponseEntity result) {
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
        "statusId": "6f7e672c-1e69-4108-92c4-3556f13eda74",
        "backupCodes": [{
            "code": "63537876",
            "used": false,
        },
        {
            "code": "76482357",
            "used": false,        
        }]
    }
}
```

#### Login via Backupcode

Once you configured Backupcode, you can also login with Backupcode via Passwordless authentication. To login with Backupcode, call **loginWithBackupcode()**.

```java
cidaas.loginWithBackupcode("verificationCode","yourEmail", "yourmobile", "yoursub", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //your success code here.
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here.
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### TOTP

To use TOTP as a passwordless login, you need to configure TOTP physical verification first.

#### Configure TOTP

To configure TOTP verification, call **configureTOTP()**.

```java

cidaas.configureTOTP("Your Sub", new Result < EnrollTOTPMFAResponseEntity > () {
 @Override
 public void success(EnrollTOTPMFAResponseEntity result) {
  //your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
 }
});
    
```



**Response:**

```swift
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via TOTP

Once you configured TOTP, you can also login with TOTP via Passwordless authentication. To login, call **loginWithTOTP()**.

```java

cidaas.loginWithTOTP("yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here  
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```



#### Pattern Recognition

To use Pattern Recognition as a passwordless login, you need to configure it first.

#### Configure Pattern Recognition

To configure Pattern Recognition, call **configurePatternRecognition()**.

```java
cidaas.configurePatternRecognition("RED[1,2,3]", "Your Sub", new Result < EnrollPatternMFAResponseEntity > () {
 @Override
 public void success(EnrollPatternPMFAResponseEntity result) {
  //your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
 }
});
```
**Response:**

```swift
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Pattern Recognition

Once you configured Pattern Recognition, you can also login with Pattern Recognition via Passwordless authentication. To login, call **loginWithPatternRecognition()**.

```java
cidaas.loginWithPatternRecognition("RED[1,2,3]", "yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### fingerprint Verification

To use fingerprint Verification as a passwordless login, you need to configure it first.

#### Configure fingerprint Verification

To configure fingerprint Verification, call **configureFingerprint()**.

```java

cidaas.configureFingerprint("Your Sub", new Result < EnrollFingerprintMFAResponseEntity > () {
 @Override
 public void success(EnrollFingerprintPMFAResponseEntity result) {
  //your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
 }
});
    
```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Fingerprint Verification

Once you configured fingerprint  Verification, you can also login with fingerprint Id Verification via Passwordless authentication. To login, call **loginWithFingerprint()**.

```java
cidaas.loginWithFingerprint("yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here  
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

### SmartPush Notification

To use SmartPush Notification as a passwordless login, you need to configure it first.

#### Configure SmartPush Notification

To configure SmartPush Notification, call **configureSmartPush()**.

```java

   cidaas.configureSmartPush("Your Sub", new Result < EnrollSmartPushMFAResponseEntity > () {
    @Override
    public void success(EnrollSmartPushPMFAResponseEntity result) {
     //your success code here
    }

    @Override
    public void failure(WebAuthError error) {
     //your failure code here
    }
   });
    
```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via SmartPush Notification

Once you configured SmartPush Notification, you can also login with SmartPush Notification via Passwordless authentication. To login, call **loginWithSmartPush()**.

```java

cidaas.loginWithSmartPush("yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here  
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### Face Recognition

Biometric plays an important role in the modern world. cidaas authenticates you by verifying your Face. To use Face Recognition as a passwordless login, you need to configure it first.

#### Configure Face Recognition

To configure Face Recognition, call **configureFaceRecognition()**.

```java

cidaas.configureFaceRecognition(File: image, "Your Sub", new Result < EnrollFaceRecognitionMFAResponseEntity > () {
 @Override
 public void success(EnrollFaceRecognitionPMFAResponseEntity result) {
  //your success code here
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
 }
});
    
```

**Response:**

```json
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Face Recognition

Once you configured Face Recognition, you can also login with Face Recognition via Passwordless authentication. To login, call **loginWithFaceRecognition()**.

```java
cidaas.loginWithFaceRecognition(File: image, "yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here  
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### Voice Recognition

Biometric plays an important role in the modern world. cidaas authenticates you by verifying your voice. To use Voice Recognition as a passwordless login, you need to configure it first.
 
#### Configure Voice Recognition

To configure Voice Recognition, call **configureVoiceRecognition()**.

```java

    cidaas.configureVoiceRecognition(File: Audio, "Your Sub", new Result < EnrollVoiceRecognitionMFAResponseEntity > () {
     @Override
     public void success(EnrollFaceRecognitionPMFAResponseEntity result) {
      //your success code here
     }

     @Override
     public void failure(WebAuthError error) {
      //your failure code here
     }
    });
    
```

**Response:**

```swift
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Voice Recognition

Once you configured Voice Recognition, you can also login with Voice Recognition via Passwordless authentication. To login, call **loginWithVoiceRecognition()**.

```java
cidaas.loginWithVoiceRecognition(File: Audio, "yourEmail", "", "", UsageType.MFA, "your trackId", "your requestId", new Result < LoginCredentialsResponseEntity > () {
 @Override
 public void success(LoginCredentialsResponseEntity result) {
  //Your success code here  
 }

 @Override
 public void failure(WebAuthError error) {
  //your failure code here
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTV",
        "session_state": "CNT7TF6Og-cCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```

#### Consent Management

Once user is successfully logged in, they need to accept the terms and conditions.

#### Getting Consent Details 

To get the consent details call **getConsentDetails()**.


```java

cidaas.getConsentDetails(consentName, consentversion, trackid, new Result < ConsentDetailsResultEntity > () {
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

After accepting the consent you need to continue further by calling ****loginAfterConsent()****

```java
cidaas.loginAfterConsent("Your sub", true, new Result < LoginCredentialsResponseEntity > () {
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
        "expires_in": 86400,
        "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUxNWYxMGE5LTVmNDktNGZlYS04MGNlLTZmYTkzMzk2YjI4NyJ9*****",
        "session_state": "CNT7GGALeoKyTF6Og-cZHAuHUJBQ20M0jLL35oh3UGk.vcNxCNq4Y68",
        "viewtype": "login",
        "grant_type": "login"
    }
}
```


    
