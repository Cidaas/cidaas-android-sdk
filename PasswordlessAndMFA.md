# Passwordless and Multifactor Authentication

Cidaas provides numerous options to ensure safe and diverse mechanisms for login. It is a good practise to enable multiple factors during login, to improve the login security of your users.  
To improve convenience, cidaas offers passwordless mechanisms as well. Depending on the end user's comfort, you can offer any of the multi-factor authentication available in cidaas.

## Table of Contents
<!--ts-->
* [Face](#face-recognition)
    <!--ts-->
    * [Configuration](#configure-face-recognition)
    * [Usage](#login-via-face-recognition)
    <!--te-->
* [Voice](#voice-recognition)
    <!--ts-->
    * [Configuration](#configure-voice-recognition)
    * [Usage](#login-via-voice-recognition)
    <!--te-->
* [Fingerprint](#fingerprint-verification)
    <!--ts-->
    * [Configuration](#configure-fingerprint-verification)
    * [Usage](#login-via-fingerprint-verification)
    <!--te-->
* [Pattern](#pattern-recognition)
    <!--ts-->
    * [Configuration](#configure-pattern-recognition)
    * [Usage](#login-via-pattern-recognition)
    <!--te-->
* [Smart Push](#smartpush-notification)
    <!--ts-->
    * [Configuration](#configure-smartpush-notification)
    * [Usage](#login-via-smartpush-notification)
    <!--te-->
* [TOTP](#totp)
    <!--ts-->
    * [Configuration](#configure-totp)
    * [Usage](#login-via-totp)
    <!--te-->
* [Email](#email)
    <!--ts-->
    * [Configuration](#configure-email)
    * [Usage](#login-via-email)
    <!--te-->
* [SMS](#sms)
    <!--ts-->
    * [Configuration](#configure-sms)
    * [Usage](#login-via-sms)
    <!--te-->
* [IVR](#ivr)
    <!--ts-->
    * [Configuration](#configure-ivr)
    * [Usage](#login-via-ivr)
    <!--te-->
* [Backupcode](#backupcode)
    <!--ts-->
    * [Configuration](#configure-backupcode)
    * [Usage](#login-via-backupcode)
    <!--te--> 
    
    
    
#### Initialisation

The first step of integrating the cidaas android sdk and to sue the verification feature is the initialisation process:
```java

CidaasVerification cidaasNative = CidaasNative.getInstance(your Activity Context);

or

CidaasVerification cidaasVerification=new CidaasVerification(Your Application Context);

```

    
#### Device Verification
    
For TOTP, Pattern, Touch ID, Smart Push, Face and Voice verification, you need to verify the device to provide more security. For that call **setremoteMessage()** from your MyFirebaseMessagingService's onMessageReceived method.
    
```java
 public void onMessageReceived(RemoteMessage remoteMessage) {
        Cidaas de.cidaas=Cidaas.getInstance(this);
        de.cidaas.setremoteMessage(remoteMessage.getData());
  }
```

#### Email

To setup the passwordless login, where the user verifies himself via an email code, you will need to configure his email first and verify it. By default, when you verify an email during account verification, you are setup for passwordless login.


#### Configure Email

To receive a verification code via Email, call **configureEmail()**.
```java

de.cidaas.configureEmail("your sub", new Result < SetupEmailMFAResponseEntity > () {
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

#### Verify Email by entering code

Once the user has received his verification code via email, he will need to verify that code. For that verification, call
 **enrollEmail()**.
 
```java
de.cidaas.enrollEmail("your_code","your_status_id", new Result < EnrollEmailMFAResponseEntity > () {

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

Once the user has verified his email & configured it to be able to use it for login, he can also login with his email via the passwordless authentication process. To receive a verification code via email, call **loginWithEmail()**.
```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithEmail(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {

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

Once the user received his verification code via Email, you will need to verify the code. For that verification, call **verifyEmail()**.
```java

de.cidaas.verifyEmail("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

To use SMS for the passwordless login, the user will need to configure the SMS physical verification first and then verify his mobile number. If has already verified his mobile number using the SMS during account verification, it is by default setup for passwordless login.


#### Configure SMS

To receive a verification code via SMS, call **configureSMS()**.

```java

de.cidaas.configureSMS(sub, new Result < SetupSMSMFAResponseEntity > () {
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

Once you have received the verification code via SMS, you need to verify the code. For that verification, call **enrollSMS()**.

```java
de.cidaas.enrollSMS("your_code","your_statusId", new Result < EnrollSMSMFAResponseEntity > () {
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

Once you configured SMS, you can also login with SMS via passwordless authentication. To receive the verification code via SMS, call **loginWithSMS()**.
```java

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithSMS(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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
de.cidaas.verifySMS("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

To use IVR for the passwordless login, you need to configure the IVR physical verification first and verify the users mobile number. If you already have verified the users mobile number through the account verification via IVR, it is already configured.

#### Configure IVR

To receive the verification code via IVR, call **configureIVR()**.
```java
de.cidaas.configureIVR(sub, new Result < SetupIVRMFAResponseEntity > () {
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

Once you have received the verification code via IVR verification call, you need to verify the code. For that verification, call **enrollIVR()**.

```java

de.cidaas.enrollIVR("your_code","your_statusId", new Result < EnrollIVRMFAResponseEntity > () {
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

Once you configured IVR, you can also login with IVR via passwordless authentication. To receive the verification code via IVR verification call, call **loginWithIVR()**.

```java

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithIVR(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

Once you received the verification code via IVR, you need to verify the code. For that verification, call **verifyIVR()**.

```java

de.cidaas.verifyIVR("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

To use a backupcode as a passwordless login, you need to configure backupcode physical verification first.

#### Configure BackupCode

To configure or view the backupcode, call **configureBackupcode()**.
```java

de.cidaas.configureBackupcode(sub, new Result < SetupBackupcodeMFAResponseEntity > () {
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

Once you have configured backupcode, you can also login with backupcode with the passwordless authentication process. To login with backupcode, call **loginWithBackupcode()**.

```java

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithBackupcode("yourverificationCode",PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

You can configure passwordless login with an OTP that has to be valid only for a fixed duration. To use the TOTP as a passwordless login, you need to configure TOTP physical verification first.

#### Configure TOTP

To configure the TOTP verification, call **configureTOTP()**.

```java

de.cidaas.configureTOTP("Your Sub", new Result < EnrollTOTPMFAResponseEntity > () {
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

```json
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via TOTP

Once you have configured TOTP, you can also login with TOTP via the passwordless authentication. To login, call **loginWithTOTP()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithTOTP(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

If you want to offer a passwordless login after securing it with the secure pattern that user can define on their device, you can use this option. To use the pattern recognition as a passwordless login, you need to configure it first.

#### Configure Pattern Recognition

To configure the pattern recognition, call **configurePatternRecognition()**.

```java
de.cidaas.configurePatternRecognition("RED[1,2,3]", "Your Sub", new Result < EnrollPatternMFAResponseEntity > () {
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

Once you have configured pattern recognition, you can login with pattern recognition via the passwordless authentication. To login, call **loginWithPatternRecognition()**.

```java

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithPatternRecognition("RED[1,2,3]", PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

#### Fingerprint Verification

You may want to allow users to use their fingerprint on their mobile devices or computer peripheral to be used for passwordless login. To do this fingerprint verification for the passwordless login, you need to configure it first.

#### Configure fingerprint Verification

To configure the fingerprint verification, call **configureFingerprint()**.

```java

de.cidaas.configureFingerprint("Your Sub", new Result < EnrollFingerprintMFAResponseEntity > () {
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

Once you have configured fingerprint verification, you can also login with the fingerprint Id Verification via passwordless authentication. To login, call **loginWithFingerprint()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithFingerprint(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

SmartPush notification can be used when you would like users to receive a number on their device and use this to authenticate instead of using a password. To use the smart push notification as a passwordless login, you need to configure it first.

#### Configure SmartPush Notification

To configure smart push Notification, call **configureSmartPush()**.

```java

   de.cidaas.configureSmartPush("Your Sub", new Result < EnrollSmartPushMFAResponseEntity > () {
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

Once you have configured the smart push notification, you can also login with smart push notification via passwordless authentication. To login, call **loginWithSmartPush()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithSmartPush(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

Biometrics plays an important role in the modern world. Cidaas can register the user's face, extract unique features from it, and use that to identify when they present their face for identification. To use face recognition as a passwordless login, you need to configure it first.

#### Configure Face Recognition

To configure Face Recognition, call **configureFaceRecognition()**.

```java

de.cidaas.configureFaceRecognition(File photo, "Your Sub", new Result < EnrollFaceRecognitionMFAResponseEntity > () {
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

Once the user has configured the face recognition, he can login with face recognition via passwordless authentication. To login, call **loginWithFaceRecognition()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithFaceRecognition(File photo, PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

Biometric plays an important role in the modern world. Cidaas can record your user's voice, extract unique features and use that to verify. To use voice recognition as a passwordless login, you need to configure it first.
 
#### Configure Voice Recognition

To configure voice recognition, call **configureVoiceRecognition()**.

```java

    de.cidaas.configureVoiceRecognition(File voice, "Your Sub", new Result < EnrollVoiceRecognitionMFAResponseEntity > () {
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

Once you have configured the voice recognition, you can login with voice recognition via passwordless authentication. To login, call **loginWithVoiceRecognition()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

de.cidaas.loginWithVoiceRecognition(File voice,PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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


##### Login History

To know the Login history of a user you can call the following methods:

```Java
        UserLoginInfoEntity userLoginInfoEntity=new UserLoginInfoEntity();
        userLoginInfoEntity.setStartDate("2019-03-04T00:00:00.000Z");
        userLoginInfoEntity.setEndDate("2019-03-11T00:00:00.000Z");
        userLoginInfoEntity.setVerificationType("Your Verification type");
        userLoginInfoEntity.setSub(your sub);

        cidaas.getUserLoginInfo(userLoginInfoEntity, new EventResult<UserLoginInfoResponseEntity>() {
            @Override
            public void success(UserLoginInfoResponseEntity result) {
                //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
                //Your Failure code
            }
        });
```

> ##### Note:- The Date must be in IST format and verification type means TOTP,Face,Voice et
