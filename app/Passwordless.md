# Passwordless or Multifactor Authentication

cidaas provides numerous options to ensure safe and diverse mechanisms for login. It is a good practise to enable multiple factors during login, to ensure there is no misuse of authentication mechanism. To improve convenience, cidaas offers passwordless mechanisms as well. Depending on the end user's comfort, you can offer any of the multi-factor authentication available in cidaas.    

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
    * [Configuration](#configure-Fingerprint-verification)
    * [Usage](#login-via-Fingerprint-verification)
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
    
#### Device Verification
    
For TOTP, Pattern, Touch ID, Smart Push, Face and Voice verification, you need to verify the device to provide more security. For that call **validateDevice()** from your AppDelegate's didReceiveRemoteNotification method.
    
```swift
func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any]) {
    Cidaas.shared.validateDevice(userInfo: userInfo)
}
```

#### Email

To setup a passwordless login, where user types only an Email, you need to configure your Email first and verify. By default, when you verify your Email during account verification, you are setup for passwordless login.


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

#### Verify Email by entering code

Once you received your verification code via Email, you need to verify that code. For that verification, call
 **enrollEmail()**.
 
```java
cidaas.enrollEmail("your_code","your_status_id", new Result < EnrollEmailMFAResponseEntity > () {

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

Once you have configured for Email login, you can also login with your Email via Passwordless authentication. To receive a verification code via Email, call **loginWithEmail()**.
```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithEmail(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {

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

cidaas.verifyEmail("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

To use SMS as a passwordless login, you need to configure SMS physical verification first, and verify your mobile number. If you already verified your mobile number using SMS during account verification, it is by default setup for passwordless login.


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
cidaas.enrollSMS("your_code","your_statusId", new Result < EnrollSMSMFAResponseEntity > () {
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

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithSMS(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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
cidaas.verifySMS("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

To use IVR as a passwordless login, you need to configure IVR physical verification first and verify your mobile number. If you already verify your mobile number through account verification via IVR, it is already configured.

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

cidaas.enrollIVR("your_code","your_statusId", new Result < EnrollIVRMFAResponseEntity > () {
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

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithIVR(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

cidaas.verifyIVR("your_code","your_statusId", new Result < LoginCredentialsResponseEntity > () {
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

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithBackupcode("yourverificationCode",PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

You can configure passwordless login with an OTP that has to be valid only for a fixed duration. To use TOTP as a passwordless login, you need to configure TOTP physical verification first.

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

Once you configured TOTP, you can also login with TOTP via Passwordless authentication. To login, call **loginWithTOTP()**.

```java
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithTOTP(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

If you want to offer a passwordless login after securing it with the secure pattern that user can define on their device, you can use this option. To use Pattern Recognition as a passwordless login, you need to configure it first.

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

Once you have configured Pattern Recognition, you can also login with Pattern Recognition via Passwordless authentication. To login, call **loginWithPatternRecognition()**.

```java

PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithPatternRecognition("RED[1,2,3]", PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

You may want to allow users to use their Fingerprint on their mobile devices or computer peripheral to be used for passwordless login.To do this Fingerprint Verification as a passwordless login, you need to configure it first.

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
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithFingerprint(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

SmartPush notification can be used when you would like users to recieve a number on their device and use that to authenticate instead of password. To use SmartPush Notification as a passwordless login, you need to configure it first.

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
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithSmartPush(PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

Biometrics plays an important role in the modern world. cidaas can register a user's face, extract unique features from it, and use that to identify when they present their face for identification. To use Face Recognition as a passwordless login, you need to configure it first.

#### Configure Face Recognition

To configure Face Recognition, call **configureFaceRecognition()**.

```java

cidaas.configureFaceRecognition(File photo, "Your Sub", new Result < EnrollFaceRecognitionMFAResponseEntity > () {
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
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithFaceRecognition(File photo, PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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

Biometric plays an important role in the modern world. cidaas can record your user's voice, extract unique features and use that to verify. To use Voice Recognition as a passwordless login, you need to configure it first.
 
#### Configure Voice Recognition

To configure Voice Recognition, call **configureVoiceRecognition()**.

```java

    cidaas.configureVoiceRecognition(File voice, "Your Sub", new Result < EnrollVoiceRecognitionMFAResponseEntity > () {
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
PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
passwordlessEntity.setUsageType(UsageType.MFA);
passwordlessEntity.setTrackId(trackid);
passwordlessEntity.setRequestId(result.getData().getRequestId());
passwordlessEntity.setSub(sub);

cidaas.loginWithVoiceRecognition(File voice,PasswordlessEntity passwordlessEntity, new Result < LoginCredentialsResponseEntity > () {
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
