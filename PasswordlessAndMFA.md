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

 cidaasVerification.setupEmail("subID", new EventResult<SetupResponse>() {
            @Override
            public void success(SetupResponse result) {
               // your success code here
            }

            @Override
            public void failure(WebAuthError error) {
               // your failure code

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
 cidaasVerification.enrollEmail("your-code", "subID", "exchangeID", new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
              //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code

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
 

        LoginRequest loginRequest=LoginRequest.getPasswordlessEmailRequestEntity("email","requestID");

        CidaasVerification.getInstance(this).initiateEmail(loginRequest, new EventResult<InitiateResponse>() {
            @Override
            public void success(InitiateResponse result) {
               //Your Success Code
            }
            @Override
            public void failure(WebAuthError error) {
                // your failure code
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

 CidaasVerification.getInstance(this).verifyCode("your_code", "exchangeID", AuthenticationType.EMAIL, "requestID", UsageType.PASSWORDLESS, new EventResult<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                //Your Success Code

            }
            @Override
            public void failure(WebAuthError error) {
                // your failure code
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

CidaasVerification.getInstance(getApplicationContext()).setupSMS("subID", new EventResult<SetupResponse>() {
            @Override
            public void success(SetupResponse result) {
               //Your Success Code
            } 
            @Override
            public void failure(WebAuthError error) {
                // your failure code
            } });
    
    
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
cidaasVerification.enrollSMS("your_code", "subID", "exchangeID", new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
               //Your Success Code

            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code

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

 LoginRequest loginRequest = LoginRequest.getPasswordlessEmailRequestEntity("emailID", "requestID");


        CidaasVerification.getInstance(this).initiateSMS(loginRequest, new EventResult<InitiateResponse>() {
            @Override
            public void success(InitiateResponse result) {
               //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code
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

#### Verify SMS by entering code

Once you received your verification code via SMS, you need to verify the code. For that verification, call **verifySMS()**.

```java
CidaasVerification.getInstance(this).verifyCode("your_code", "exchangeID", AuthenticationType.SMS, "requestID", UsageType.PASSWORDLESS, new EventResult<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
               //Your Success Code

            }
            @Override
            public void failure(WebAuthError error) {
                 // your failure code
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
 CidaasVerification.getInstance(getApplicationContext()).setupIVR("subID", new EventResult<SetupResponse>() {
            @Override
            public void success(SetupResponse result) {
                //Your Success Code
            }
            @Override
            public void failure(WebAuthError error) {
                // your failure code
            } });
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

cidaasVerification.enrollIVR("your_code", "subID", "exchangeID", new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
               //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
               // your failure code

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

CidaasNative.getInstance(getApplicationContext()).getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String requestId = result.getData().getRequestId();
                LoginRequest loginRequest = LoginRequest.getPasswordlessIVRRequestEntity("EmailID", requestId);
      CidaasVerification.getInstance(MultifactorAuthenticationActivity.this).initiateIVR(loginRequest, new EventResult<InitiateResponse>() {
             @Override
             public void success(InitiateResponse result) {
                       //Your Success Code
                    }

            @Override
             public void failure(WebAuthError error) {
                        // your failure code

                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code
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

CidaasVerification.getInstance(this).verifyCode("your_code", "exchangeID", AuthenticationType.IVR, "requestID", UsageType.PASSWORDLESS, new EventResult<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
              //Your Success Code

            }
            @Override
            public void failure(WebAuthError error) {
                 // your failure code
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

CidaasVerification.getInstance(this).setupBackupCode("subID", new EventResult<SetupResponse>() {
            @Override
            public void success(SetupResponse result) {
               //Your Success Code

            }

            @Override
            public void failure(WebAuthError error) {
               // your failure code

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

 cidaasVerification.verifyCode("your_code", "exchangeID", AuthenticationType.IVR, "requestID", UsageType.PASSWORDLESS, new EventResult<LoginCredentialsResponseEntity>() {
           @Override
           public void success(LoginCredentialsResponseEntity result) {
              //Your Success Code

           }

           @Override
           public void failure(WebAuthError error) {
                // your failure code

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

 ConfigurationRequest configurationRequest = new ConfigurationRequest("subID");
        cidaasVerification.configureTOTP(configurationRequest, new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
               //Your Success Code

            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code
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
 CidaasNative.getInstance(getApplicationContext()).getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String requestId = result.getData().getRequestId();
                LoginRequest loginRequest = LoginRequest.getPasswordlessTOTPRequestEntity("emailID", requestId );
                cidaasVerification.loginWithTOTP(loginRequest, new EventResult<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                       //Your Success Code

                    }

                    @Override
                    public void failure(WebAuthError error) {
                         // your failure code
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code

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
 ConfigurationRequest configurationRequest= new ConfigurationRequest("subID","RED-1234");

        cidaasVerification.configurePattern(configurationRequest,new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse enrollResponse) {
               // your success code
            }

            @Override
            public void failure(WebAuthError error) {
               // your failure code
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

 CidaasNative.getInstance(getApplicationContext()).getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String requestId = result.getData().getRequestId();
                LoginRequest loginRequest = LoginRequest.getPasswordlessPatternLoginRequestEntity("RED-1234", "EMAILID", requestId);
                CidaasVerification.getInstance(getApplicationContext()).loginWithPattern(loginRequest, new EventResult<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                       // your success code
                    }

                    @Override
                    public void failure(WebAuthError error) {
                       // your failure code
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
               // your failure code
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

 final FingerPrintEntity fingerPrintEntity = new FingerPrintEntity(context,"Title","Content");
        //msg in ur title,description
        ConfigurationRequest configurationRequest = new ConfigurationRequest("subID",fingerPrintEntity);

        CidaasVerification.getInstance(this).configureFingerprint(configurationRequest, new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
               //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
                // your failure code
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
 CidaasNative.getInstance(getApplicationContext()).getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String requestId = result.getData().getRequestId();
                FingerPrintEntity fingerPrintEntity1 = new FingerPrintEntity(context, "Touch your Finger", " Please provide fingerID");
                LoginRequest loginRequest = LoginRequest.getPasswordlessFingerprintLoginRequestEntity("EMAILID", requestId, fingerPrintEntity1);

                cidaasVerification.loginWithFingerprint(loginRequest, new EventResult<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        //Your Success Code

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        // your failure code

                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                 // your failure code
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

   ConfigurationRequest configurationRequest = new ConfigurationRequest("subID");

        cidaasVerification.configureSmartPush(configurationRequest, new EventResult<EnrollResponse>() {
            @Override
            public void success(EnrollResponse result) {
               
                //Your Success Code
            }

            @Override
            public void failure(WebAuthError error) {
               
                    //Your Failure Code
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
 CidaasNative.getInstance(getApplicationContext()).getRequestId(new EventResult<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                String requestId = result.getData().getRequestId();
                LoginRequest loginRequest = LoginRequest.getPasswordlessSmartPushLoginRequestEntity("EMAILID", requestId);
                cidaasVerification.loginWithSmartPush(loginRequest, new EventResult<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                       //Your Success Code

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        // your failure code
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                 // your failure code

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

 ConfigurationRequest configurationRequest = new ConfigurationRequest("subID",file, attemptNumber);
                
                cidaasVerification.configureFaceRecognition(configurationRequest,new EventResult<EnrollResponse>() {
                    @Override
                    public void success(EnrollResponse enrollResponse) {
                        //Your Success Code
                    }

                    @Override
                    public void failure(WebAuthError error) {
                         // your failure code
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

    ConfigurationRequest configurationRequest = new ConfigurationRequest("subID",file, attemptNumber );

                CidaasVerification.getInstance(this).configureVoiceRecognition(configurationRequest,new EventResult<EnrollResponse>() {
                    @Override
                    public void success(EnrollResponse enrollResponse) {
                        //Your Success Code
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        // your failure code
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
