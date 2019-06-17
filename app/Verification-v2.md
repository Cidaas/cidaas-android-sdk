# Passwordless or Multifactor Authentication

Cidaas provides numerous options to ensure safe and diverse mechanisms for login. It is a good practice to enable multiple factors during login, to ensure that user identities and accesses are not compromised. To improve convenience, cidaas offers passwordless mechanisms as well. Depending on the end user's comfort, you can offer any of the multi-factor authentication methods available in cidaas. 

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
* [Touch ID](#touchid-verification)
    <!--ts-->
    * [Configuration](#configure-touchid-verification)
    * [Usage](#login-via-touchid-verification)
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
    
    
#### Face Recognition

Biometrics plays an important role in the modern world. Cidaas can register a user's face, extract unique features from it, and use that to identify when they present their face for identification.  To use Face Recognition as a passwordless login, you need to configure it first.

#### Configure Face Recognition

To configure Face Recognition, call **configureFaceRecognition()**.

```java
 //Create ConfigureRequest for face recognition
 ConfigureRequest configureRequest=new ConfigureRequest(your_sub,your_image_file,your_face_attempt);

 CidaasVerification.getInstance(this).configureFaceRecognition(configureRequest,new Result<EnrollResponse>() {
     @Override
     public void success(EnrollResponse enrollResponse) {
         //Your Success Code
     }

     @Override
     public void failure(WebAuthError error) {
        //Your Failure Code
     }
 });

```

**Response:**

```java
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Face Recognition

Once you configure Face Recognition, you can login with Face Recognition for Passwordless authentication. To login, call **loginWithFaceRecognition()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithFaceRecognition(photo: photo, passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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

#### Voice Recognition

Biometric plays an important role in the modern world. cidaas can record your user's voice, extract unique features and use that to verify. To use Voice Recognition for passwordless login, you need to configure it first.

#### Configure Voice Recognition

To configure Voice Recognition, call **configureVoiceRecognition()**.

```java
//Create ConfigureRequest for voice recognition
 ConfigureRequest configureRequest=new ConfigureRequest(your_sub,your_voice_file,your_voice_attempt);

 CidaasVerification.getInstance(this).configureVoiceRecognition(configureRequest,new Result<EnrollResponse>() {
     @Override
     public void success(EnrollResponse enrollResponse) {
         //Your Success Code
     }

     @Override
     public void failure(WebAuthError error) {
        //Your Failure Code
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

Once you configure Voice Recognition, you can login with Voice Recognition as Passwordless authentication. To login, call **loginWithVoiceRecognition()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithVoiceRecognition(voice: audioData, passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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

#### TouchId Verification
You may want to allow users to use their touchId on their mobile devices or computer peripheral to be used for passwordless login.To use TouchId Verification for passwordless login, you need to configure it first.

#### Configure Fingerprint

To configure TouchId Verification, call **configureFingerprint()**.

```java
//Create ConfigureRequest for Fingerprint
 //Fingerprint entity
 final FingerPrintEntity fingerPrintEntity=new FingerPrintEntity(Your Activity Context,Your Title,your Description);
        
 ConfigureRequest configureRequest=new ConfigureRequest(sub,fingerPrintEntity);

 CidaasVerification.getInstance(this).configureFingerprint(configureRequest, new Result<EnrollResponse>() {
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

```swift
{
    "success": true,
    "status": 200,
    "data": {
        "statusId": "5f5cbb84-4ceb-4975-b347-4bfac61e9248"
    }
}
```

#### Login via Touch Id Verification

Once you have configured Touch Id Verification, you can also login with Touch Id Verification for Passwordless authentication. To login, call **loginWithTouchId()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithTouchId(passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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

#### Pattern Recognition
If you want to offer a passwordless login after securing it with the secure pattern that user can define on their device, you can use this option.  To use Pattern Recognition for passwordless login, you need to configure it first.

#### Configure Pattern Recognition

To configure Pattern Recognition, call **configurePatternRecognition()**.

```swift
cidaas.configurePatternRecognition(pattern: "RED[1,2,3]", sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

Once you have configured Pattern Recognition, you can also login with Pattern Recognition for Passwordless authentication. To login, call **loginWithPatternRecognition()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithPatternRecognition(pattern: "RED[1,2,3], passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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

### SmartPush Notification
SmartPush notification can be used when you would like users to receive a number on their device and use that to authenticate instead of password. To use SmartPush Notification for passwordless login, you need to configure it first.

#### Configure SmartPush Notification

To configure SmartPush Notification, call **configureSmartPush()**.

```swift
cidaas.configureSmartPush(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Login via SmartPush Notification

Once you configure SmartPush Notification, you can also login with SmartPush Notification for Passwordless authentication. To login, call **loginWithSmartPush()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithSmartPush(passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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
#### TOTP
You can configure passwordless login with an OTP that has to be valid only for a fixed duration. To use TOTP for passwordless login, you need to configure TOTP physical verification first.

#### Configure TOTP

To configure TOTP verification, call **configureTOTP()**.

```swift
cidaas.configureTOTP(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break 
    }
}
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

Once you configure TOTP, you can login with TOTP for Passwordless authentication. To login, call **loginWithTOTP()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithTOTP(passwordlessEntity: passwordlessEntity) {
    switch $0 { 
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
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

#### Email

To setup a passwordless login, where user types only an Email, you need to configure your Email first and verify. By default, when you verify your Email during account verification, you are setup for passwordless login.

#### Configure Email

To receive a verification code via Email, call **configureEmail()**.

```swift
cidaas.configureEmail(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Enroll Email

Once you receive your verification code via Email, you need to verify that code. For that verification, call **enrollEmail()**.

```swift
cidaas.enrollEmail(code: "658144") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the Email

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

Once you have configured for Email login, you can also login with your Email for Passwordless authentication. To receive a verification code via Email, call **loginWithEmail()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithEmail(passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Verify Email

Once you receive your verification code via Email, you need to verify the code. For that verification, call **verifyEmail()**.

```swift
cidaas.verifyEmail(code: "123123") {
    switch $0 {
        case .success(let verifySuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the Email

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

To use SMS as a passwordless login, you need to configure SMS physical verification first, and verify your mobile number. If you have already verified your mobile number using SMS during account verification, it is by default setup for passwordless login. 

#### Configure SMS

To receive a verification code via SMS, call **configureSMS()**.

```swift
cidaas.configureSMS(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
} 
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

#### Enroll SMS

Once you receive your verification code via SMS, you need to verify the code. For that verification, call **enrollSMS()**.

```swift
cidaas.enrollSMS(code: "123123") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the SMS

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

Once you configure SMS, you can also login with SMS for Passwordless authentication. To receive a verification code via SMS, call **loginWithSMS()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.mobile = "+919876543210" // must starts with country code
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithSMS(passwordlessEntity: passwordlessEntity) {
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Verify SMS

Once you receive your verification code via SMS, you need to verify the code. For that verification, call **verifySMS()**.

```swift
cidaas.verifySMS(code: "123123") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the SMS

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

To use IVR as a passwordless login, you need to configure IVR physical verification first and verify your mobile number. If you have already verified your mobile number through account verification via IVR, it is already configured. 

#### Configure IVR

To receive a verification code via IVR, call **configureIVR()**.

```swift
cidaas.configureIVR(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Enroll IVR

Once you receive your verification code for IVR verification call, you need to verify the code. For that verification, call **enrollIVR()**.

```swift
cidaas.enrollIVR(code: "123123") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the IVR verification call

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

Once you configure IVR, you can also login with IVR for Passwordless authentication. To receive a verification code via IVR verification call, call **loginWithIVR()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.mobile = "+919876543210" // must starts with country code
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithIVR(passwordlessEntity: passwordlessEntity){
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

#### Verify IVR

Once you receive your verification code via IVR, you need to verify the code. For that verification, call **verifyIVR()**.

```swift
cidaas.verifyIVR(code: "123123") {
    switch $0 {
        case .success(let verifySuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from the IVR verification call

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

To use Backupcode for passwordless login, you need to configure Backupcode physical verification first.

#### Configure BackupCode

To configure or view the Backupcode, call **configureBackupcode()**.

```swift
cidaas.configureBackupcode(sub: "7dfb2122-fa5e-4f7a-8494-dadac9b43f9d") {
    switch $0 {
        case .success(let configureSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
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

Once you configure Backupcode, you can also login with Backupcode for Passwordless authentication. To login with Backupcode, call **loginWithBackupcode()**.

```swift
let passwordlessEntity = PasswordlessEntity()
passwordlessEntity.email = "xxx@gmail.com"
passwordlessEntity.usageType = UsageTypes.PASSWORDLESS.rawValue

cidaas.loginWithBackupcode(code: "63537876", passwordlessEntity: passwordlessEntity){
    switch $0 {
        case .success(let loginWithSuccess):
            // your success code here
        break
        case .failure(let error):
            // your failure code here
        break
    }
}
```
Here, **code** is the key you would get from your saved Backup codes

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
