package com.example.cidaasv2.Helper.Enums;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class WebAuthErrorCode {

    //General
  public static final int DEFAULT=10000;
  public static final int FILE_NOT_FOUND=10001;
  public static final int NO_CONTENT_IN_FILE=10002;
  public static final int PROPERTY_MISSING=10003;
  public static final int REQUEST_ID_SERVICE_FAILURE=10004;
  public static final int EMPTY_REQUEST_ID_SERVICE=10005;
  public static final int EMPTY_LOGIN_URL = 10006;
  public static final int EMPTY_REDIRECT_URL = 10007;
  public static final int EMPTY_DELEGATE = 10008;
  public static final int USER_CANCELLED_LOGIN = 10009;
  public static final int CODE_NOT_FOUND = 10010;

  //AccessToken
  public static final int ACCESSTOKEN_SERVICE_FAILURE = 10011;
  public static final int EMPTY_LOGIN_URL_SERVICE = 10012;
  public static final int ERROR_JSON_PARSING = 10013;
  public static final int USER_INFO_SERVICE_FAILURE = 10014;
  public static final int EMPTY_CALLBACK = 10015;
  public static final int NO_USER_FOUND = 10016;
  public static final int REFRESH_TOKEN_SERVICE_FAILURE = 10017;

  //Basic calls
    public  static  final  int EMPTY_TENANT_INFO_SERVICE=10018;
    public  static  final  int EMPTY_CLIENT_INFO_SERVICE=10019;
  public static final int TENANT_INFO_FAILURE = 10020;
  public static final int CLIENT_INFO_FAILURE = 10021;
  public static final int REQUEST_ID_MISSING = 10020;

    public  static  final  int EMPTY_LOGIN_WITH_CREDENTIALS_FAILURE=10018;
  public static final int LOGINWITH_CREDENTIALS_FAILURE = 10021;
  public static final int REGISTRATION_SETUP_FAILURE = 10022;



  public static final int CONSENT_URL_FAILURE = 10023;
  public static final int CONSENT_STRING_FAILURE = 10024;
  public static final int ACCEPT_CONSENT_FAILURE = 10025;

  public static final int MFA_LIST_FAILURE = 10026;

  public static final int INITIATE_EMAIL_MFA_FAILURE = 10027;
  public static final int AUTHENTICATE_EMAIL_MFA_FAILURE = 10028;

  public static final int INITIATE_RESET_PASSWORD_FAILURE = 10029;
  public static final int RESET_PASSWORD_VALIDATE_CODE_FAILURE = 10030;
  public static final int RESET_NEWPASSWORD_FAILURE = 10031;
  public static final int CHANGE_PASSWORD_FAILURE = 10032;

  public static final int INITIATE_SMS_MFA_FAILURE = 10033;
  public static final int AUTHENTICATE_SMS_MFA_FAILURE = 10034;

  public static final int INITIATE_IVR_MFA_FAILURE = 10035;
  public static final int AUTHENTICATE_IVR_MFA_FAILURE = 10036;

  public static final int INITIATE_BACKUPCODE_MFA_FAILURE = 10037;
  public static final int AUTHENTICATE_BACKUPCODE_MFA_FAILURE = 10038;

  public static final int RESUME_LOGIN_FAILURE = 10039;

  public static final int INITIATE_FACE_MFA_FAILURE = 10040;
  public static final int AUTHENTICATE_FACE_MFA_FAILURE = 10041;

  public static final int INITIATE_FINGERPRINT_MFA_FAILURE = 10042;
  public static final int AUTHENTICATE_FINGERPRINT_MFA_FAILURE = 10043;

  public static final int INITIATE_VOICE_MFA_FAILURE = 10044;
  public static final int AUTHENTICATE_VOICE_MFA_FAILURE = 10045;

  public static final int INITIATE_PATTERN_MFA_FAILURE = 10046;
  public static final int AUTHENTICATE_PATTERN_MFA_FAILURE = 10047;

  public static final int INITIATE_SMARTPUSH_MFA_FAILURE = 10048;
  public static final int AUTHENTICATE_SMARTPUSH_MFA_FAILURE = 10049;

  public static final int INITIATE_TOTP_MFA_FAILURE = 10050;
  public static final int AUTHENTICATE_TOTP_MFA_FAILURE = 10051;

  public static final int INITIATE_FIDO_MFA_FAILURE = 10052;
  public static final int AUTHENTICATE_FIDO_MFA_FAILURE = 10053;

  public static final int RESUME_CONSENT_FAILURE = 10054;

  public static final int SETUP_SMS_MFA_FAILURE = 10055;
  public static final int ENROLL_SMS_MFA_FAILURE = 10056;

  public static final int SETUP_IVR_MFA_FAILURE = 10057;
  public static final int ENROLL_IVR_MFA_FAILURE = 10058;

  public static final int SETUP_BACKUPCODE_MFA_FAILURE = 10059;
  public static final int ENROLL_BACKUPCODE_MFA_FAILURE = 10060;

  public static final int SETUP_FACE_MFA_FAILURE = 10061;
  public static final int ENROLL_FACE_MFA_FAILURE = 10062;

  public static final int SETUP_FINGERPRINT_MFA_FAILURE = 10063;
  public static final int ENROLL_FINGERPRINT_MFA_FAILURE = 10064;

  public static final int SETUP_VOICE_MFA_FAILURE = 10065;
  public static final int ENROLL_VOICE_MFA_FAILURE = 10066;

  public static final int SETUP_PATTERN_MFA_FAILURE = 10067;
  public static final int ENROLL_PATTERN_MFA_FAILURE = 10068;

  public static final int SETUP_SMARTPUSH_MFA_FAILURE = 10069;
  public static final int ENROLL_SMARTPUSH_MFA_FAILURE = 10070;

  public static final int SETUP_TOTP_MFA_FAILURE = 10071;
  public static final int ENROLL_TOTP_MFA_FAILURE = 10072;

  public static final int SETUP_FIDO_MFA_FAILURE = 10073;
  public static final int ENROLL_FIDO_MFA_FAILURE = 10074;

  public static final int SETUP_EMAIL_MFA_FAILURE = 10075;
  public static final int ENROLL_EMAIL_MFA_FAILURE = 10076;

  public static final int INTERNAL_USER_PROFILE_FAILURE = 10077;

  public static final int INITIATE_ACCOUNT_VERIFICATION_FAILURE = 10078;
  public static final int VERIFY_ACCOUNT_VERIFICATION_FAILURE = 10079;


  public static final int SCANNED_PATTERN_MFA_FAILURE = 10081;
  public static final int SCANNED_FACE_MFA_FAILURE = 10082;
  public static final int SCANNED_VOICE_MFA_FAILURE = 10083;
  public static final int SCANNED_TOTP_MFA_FAILURE = 10084;
  public static final int SCANNED_FINGERPRINT_MFA_FAILURE = 10085;
  public static final int SCANNED_SMARTPUSH_MFA_FAILURE = 10086;
  public static final int SCANNED_FIDO_MFA_FAILURE = 10087;

  public static final int DEDUPLICATION_LIST_FAILURE = 10088;
  public static final int DEDUPLICATION_REGISTRATION_FAILURE = 10089;

  public static final int DELETE_MFA_FAILURE = 10090;

  public static final int DENY_NOTIFICATION = 10091;
  public static final int PENDING_NOTIFICATION_FAILURE = 10092;

  public static final int CONFIGURED_LIST_MFA_FAILURE = 10093;

  public static final int UPDATE_FCM_TOKEN = 10094;

  public static final int GET_LOGIN_URL_FAILURE = 10095;
  public static final int GET_SOCIAL_LOGIN_URL_FAILURE = 10096;

  public static final int ON_CANCEL_FACEBOOK = 10097;

  public static final int GOOGLE_ERROR = 10098;

  public static final int LOGOUT_ERROR = 10099;




  //Common Error Code maintained in Authenticator app
  public static final int DEVICE_VERIFICATION_FAILURE=10080;



  public static final int LOCAL_AUHTHENTICATION_CANCELLED = 10101;
  public static final int LOCAL_AUHTHENTICATION_FAILED = 10102;
  public static final int NO_LOCAL_AUHTHENTICATION_FOUND = 10103;


  public static final int FINGERPRINT_SDK_VERSION_NOT_SUPPORTED = 10104;
  public static final int FINGERPRINT_BIOMETRIC_AUTHENTICATION_NOT_SUPPORTED = 10105;
  public static final int FINGERPRINT_BIOMERTIC_AUTHENTICATION_NOT_AVAILABLE = 10106;
  public static final int FINGERPRINT_BIOMERTIC_AUTHENTICATION_PERMISSION_NOT_GRANTED = 10107;
  public static final int FINGERPRINT_BIOMERTIC_AUTHENTICATION_INTERNAL_ERROR = 10108;
  public static final int FINGERPRINT_AUTHENTICATION_FAILED = 10109;
  public static final int FINGERPRINT_AUTHENTICATION_CANCELLED = 10110;



  public static final int DOCUMENT_VERIFICATION_FAILURE=10111;


  //Common Error Codes
  public static final int TOUCHID_NOT_AVAILABLE = 10178;
  public static final int TOUCHID_NOT_ENROLLED = 10179;
  public static final int TOUCH_ID_PASSCODE_NOT_CONFIGURED = 10180;
  public static final int TOUCH_ID_INVALID_AUTHENTICATION = 10181;
  public static final int TOUCH_ID_APP_CANCELLED = 10182;
  public static final int TOUCH_ID_SYSTEM_CANCELLED = 10183;
  public static final int TOUCH_ID_USER_CANCELLED = 10184;
  public static final int TOUCHID_LOCKED = 10185;
  public static final int TOUCHID_DEFAULT_ERROR = 10186;
  public static final int TOUCHID_INVALID_CONTEXT = 10187;
  public static final int TOUCHID_NOT_INTERACTIVE = 10188;

  public static final int NETWORK_TIMEOUT = 10189;


  public static final int USER_LOGIN_INFO_SERVICE_FAILURE =10190;

  public static final int PARAMS_TO_DICTIONARY_CONVERTER_ERROR = 10191;
  public static final int PARSE_XML = 10192;
  public static final int READ_PROPERTIES_ERROR = 10193;

}




 /* public static final int REFRESH_TOKEN_SERVICE_FAILURE = 10017;
            public static final int EMPTY_TENANT_INFO_SERVICE = 10018;
            public static final int EMPTY_CLIENT_INFO_SERVICE = 10019;
            public static final int TENANT_INFO_SERVICE_FAILURE = 10020;
            public static final int CLIENT_INFO_SERVICE_FAILURE = 10021;
            public static final int EMPTY_LOGIN_WITH_CREDENTIAL_SERVICE = 10022;
            public static final int LOGIN_WITH_CREDENTIAL_SERVICE_FAILURE = 10023;
            public static final int EMPTY_CONSENT_URL_SERVICE = 10024;
            public static final int CONSENT_URL_SERVICE_FAILURE = 10025;
            public static final int EMPTY_CONSENT_DETAILS_SERVICE = 10026;
            public static final int CONSENT_DETAILS_SERVICE_FAILURE = 10027;
            public static final int EMPTY_ACCEPT_CONSENT_SERVICE = 10028;
            public static final int ACCEPT_CONSENT_SERVICE_FAILURE = 10029;
            public static final int EMPTY_MFA_LIST_SERVICE = 10030;
            public static final int MFA_LIST_SERVICE_FAILURE = 10031;
            public static final int EMPTY_INITIATE_EMAIL_SERVICE = 10032;
            public static final int INITIATE_EMAIL_SERVICE_FAILURE = 10033;
            public static final int EMPTY_INITIATE_SMS_SERVICE = 10034;
            public static final int INITIATE_SMS_SERVICE_FAILURE = 10035;
            public static final int EMPTY_INITIATE_IVR_SERVICE = 10036;
            public static final int INITIATE_IVR_SERVICE_FAILURE = 10037;
            public static final int EMPTY_AUTHENTICATE_EMAIL_SERVICE = 10038;
            public static final int AUTHENTICATE_EMAIL_SERVICE_FAILURE = 10039;
            public static final int EMPTY_AUTHENTICATE_SMS_SERVICE = 10040;
            public static final int AUTHENTICATE_SMS_SERVICE_FAILURE = 10041;
            public static final int EMPTY_AUTHENTICATE_IVR_SERVICE = 10042;
            public static final int AUTHENTICATE_IVR_SERVICE_FAILURE = 10043;
            public static final int EMPTY_AUTHENTICATE_BACKUPCODE_SERVICE = 10044;
            public static final int AUTHENTICATE_BACKUPCODE_SERVICE_FAILURE = 10045;
            public static final int EMPTY_INITIATE_BACKUPCODE_SERVICE = 10046;
            public static final int INITIATE_BACKUPCODE_SERVICE_FAILURE = 10047;
            public static final int EMPTY_INITIATE_TOTP_SERVICE = 10048;
            public static final int INITIATE_TOTP_SERVICE_FAILURE = 10049;
            public static final int EMPTY_AUTHENTICATE_TOTP_SERVICE = 10050;
            public static final int AUTHENTICATE_TOTP_SERVICE_FAILURE = 10051;
            public static final int EMPTY_MFA_CONTINUE_SERVICE = 10052;
            public static final int MFA_CONTINUE_SERVICE_FAILURE = 10053;
            public static final int EMPTY_CONSENT_CONTINUE_SERVICE = 10054;
            public static final int CONSENT_CONTINUE_SERVICE_FAILURE = 10055;
            public static final int EMPTY_REGISTRATION_FIELDS_SERVICE = 10056;
            public static final int REGISTRATION_FIELDS_SERVICE_FAILURE = 10057;
            public static final int EMPTY_REGISTRATION_SERVICE = 10058;
            public static final int REGISTRATION_SERVICE_FAILURE = 10059;
            public static final int CONVERSION_EXCEPTION = 10060;
            public static final int EMPTY_PATTERN_SETUP_SERVICE = 10061;
            public static final int PATTERN_SETUP_SERVICE_FAILURE = 10062;
            public static final int NOTIFICATION_TIMEOUT = 10063;
            public static final int EMPTY_VALIDATE_DEVICE_SERVICE = 10064;
            public static final int VALIDATE_DEVICE_SERVICE_FAILURE = 10065;
            public static final int EMPTY_PATTERN_SCANNED_SERVICE = 10066;
            public static final int PATTERN_SCANNED_SERVICE_FAILURE = 10067;
            public static final int EMPTY_PATTERN_ENROLLED_SERVICE = 10068;
            public static final int PATTERN_ENROLLED_SERVICE_FAILURE = 10069;
            public static final int EMPTY_PATTERN_INITIATE_SERVICE = 10070;
            public static final int PATTERN_INITIATE_SERVICE_FAILURE = 10071;
            public static final int EMPTY_PATTERN_AUTHENTICATE_SERVICE = 10072;
            public static final int PATTERN_AUTHENTICATE_SERVICE_FAILURE = 10073;
            public static final int EMPTY_SETUP_EMAIL_SERVICE = 10074;
            public static final int SETUP_EMAIL_SERVICE_FAILURE = 10075;
            public static final int EMPTY_ENROLL_EMAIL_SERVICE = 10076;
            public static final int ENROLL_EMAIL_SERVICE_FAILURE = 10077;
            public static final int EMPTY_SETUP_SMS_SERVICE = 10078;
            public static final int SETUP_SMS_SERVICE_FAILURE = 10079;
            public static final int EMPTY_ENROLL_SMS_SERVICE = 10080;
            public static final int ENROLL_SMS_SERVICE_FAILURE = 10081;
            public static final int EMPTY_SETUP_IVR_SERVICE = 10082;
            public static final int SETUP_IVR_SERVICE_FAILURE = 10083;
            public static final int EMPTY_ENROLL_IVR_SERVICE = 10084;
            public static final int ENROLL_IVR_SERVICE_FAILURE = 10085;
            public static final int EMPTY_SETUP_BACKUPCODE_SERVICE = 10086;
            public static final int SETUP_BACKUPCODE_SERVICE_FAILURE = 10087;

            public static final int EMPTY_TOUCHID_SETUP_SERVICE = 10088;
            public static final int TOUCHID_SETUP_SERVICE_FAILURE = 10089;
            public static final int EMPTY_TOUCHID_SCANNED_SERVICE = 10090;
            public static final int TOUCHID_SCANNED_SERVICE_FAILURE = 10091;
            public static final int EMPTY_TOUCHID_ENROLLED_SERVICE = 10092;
            public static final int TOUCHID_ENROLLED_SERVICE_FAILURE = 10093;
            public static final int EMPTY_TOUCHID_INITIATE_SERVICE = 10094;
            public static final int TOUCHID_INITIATE_SERVICE_FAILURE = 10095;
            public static final int EMPTY_TOUCHID_AUTHENTICATE_SERVICE = 10096;
            public static final int TOUCHID_AUTHENTICATE_SERVICE_FAILURE = 10097;

            public static final int EMPTY_FACE_SETUP_SERVICE = 10098;
            public static final int FACE_SETUP_SERVICE_FAILURE = 10099;
            public static final int EMPTY_FACE_SCANNED_SERVICE = 10100;
            public static final int FACE_SCANNED_SERVICE_FAILURE = 10101;
            public static final int EMPTY_FACE_ENROLLED_SERVICE = 10102;
            public static final int FACE_ENROLLED_SERVICE_FAILURE = 10103;
            public static final int EMPTY_FACE_INITIATE_SERVICE = 10104;
            public static final int FACE_INITIATE_SERVICE_FAILURE = 10105;
            public static final int EMPTY_FACE_AUTHENTICATE_SERVICE = 10106;
            public static final int FACE_AUTHENTICATE_SERVICE_FAILURE = 10107;

            public static final int EMPTY_VOICE_SETUP_SERVICE = 10108;
            public static final int VOICE_SETUP_SERVICE_FAILURE = 10109;
            public static final int EMPTY_VOICE_SCANNED_SERVICE = 10110;
            public static final int VOICE_SCANNED_SERVICE_FAILURE = 10111;
            public static final int EMPTY_VOICE_ENROLLED_SERVICE = 10112;
            public static final int VOICE_ENROLLED_SERVICE_FAILURE = 10113;
            public static final int EMPTY_VOICE_INITIATE_SERVICE = 10114;
            public static final int VOICE_INITIATE_SERVICE_FAILURE = 10115;
            public static final int EMPTY_VOICE_AUTHENTICATE_SERVICE = 10116;
            public static final int VOICE_AUTHENTICATE_SERVICE_FAILURE = 10117;

            public static final int EMPTY_PUSH_SETUP_SERVICE = 10118;
            public static final int PUSH_SETUP_SERVICE_FAILURE = 10119;
            public static final int EMPTY_PUSH_SCANNED_SERVICE = 10120;
            public static final int PUSH_SCANNED_SERVICE_FAILURE = 10121;
            public static final int EMPTY_PUSH_ENROLLED_SERVICE = 10122;
            public static final int PUSH_ENROLLED_SERVICE_FAILURE = 10123;
            public static final int EMPTY_PUSH_INITIATE_SERVICE = 10124;
            public static final int PUSH_INITIATE_SERVICE_FAILURE = 10125;
            public static final int EMPTY_PUSH_AUTHENTICATE_SERVICE = 10126;
            public static final int PUSH_AUTHENTICATE_SERVICE_FAILURE = 10127;

            public static final int EMPTY_TOTP_SETUP_SERVICE = 10128;
            public static final int TOTP_SETUP_SERVICE_FAILURE = 10129;
            public static final int EMPTY_TOTP_SCANNED_SERVICE = 10130;
            public static final int TOTP_SCANNED_SERVICE_FAILURE = 10131;
            public static final int EMPTY_TOTP_ENROLLED_SERVICE = 10132;
            public static final int TOTP_ENROLLED_SERVICE_FAILURE = 10133;
            public static final int EMPTY_TOTP_INITIATE_SERVICE = 10134;
            public static final int TOTP_INITIATE_SERVICE_FAILURE = 10135;
            public static final int EMPTY_TOTP_AUTHENTICATE_SERVICE = 10136;
            public static final int TOTP_AUTHENTICATE_SERVICE_FAILURE = 10137;

            public static final int EMPTY_INITIATE_ACCOUNT_VERIFICATION_SERVICE = 10138;
            public static final int INITIATE_ACCOUNT_VERIFICATION_SERVICE_FAILURE = 10139;

            public static final int EMPTY_VERIFY_ACCOUNT_SERVICE = 10140;
            public static final int VERIFY_ACCOUNT_SERVICE_FAILURE = 10141;

            public static final int EMPTY_INITIATE_RESET_PASSWORD_SERVICE = 10142;
            public static final int INITIATE_RESET_PASSWORD_SERVICE_FAILURE = 10143;

            public static final int EMPTY_HANDLE_RESET_PASSWORD_SERVICE = 10144;
            public static final int HANDLE_RESET_PASSWORD_SERVICE_FAILURE = 10145;

            public static final int EMPTY_DEDUPLICATION_DETAILS_SERVICE = 10146;
            public static final int DEDUPLICATION_DETAILS_SERVICE_FAILURE = 10147;

            public static final int EMPTY_REGISTER_DEDUPLICATION_SERVICE = 10148;
            public static final int REGISTER_DEDUPLICATION_SERVICE_FAILURE = 10149;

            public static final int EMPTY_DEDUPLICATION_LOGIN_SERVICE = 10150;
            public static final int DEDUPLICATION_LOGIN_SERVICE_FAILURE = 10151;

            public static final int EMPTY_CHANGE_PASSWORD_SERVICE = 10152;
            public static final int CHANGE_PASSWORD_SERVICE_FAILURE = 10153;

            public static final int EMPTY_USER_ACTIVITY_SERVICE = 10154;
            public static final int USER_ACTIVITY_SERVICE_FAILURE = 10155;

            public static final int EMPTY_UPDATE_USER_SERVICE = 10156;
            public static final int UPDATE_USER_SERVICE_FAILURE = 10157;

            public static final int EMPTY_IMAGE_UPLOAD_SERVICE = 10158;
            public static final int IMAGE_UPLOAD_SERVICE_FAILURE = 10159;

            public static final int EMPTY_LINK_ACCOUNT_SERVICE = 10160;
            public static final int LINK_ACCOUNT_SERVICE_FAILURE = 10161;

            public static final int EMPTY_GET_LINKED_USERS_SERVICE = 10162;
            public static final int GET_LINKED_USERS_SERVICE_FAILURE = 10163;

            public static final int EMPTY_UNLINK_ACCOUNT_SERVICE = 10164;
            public static final int UNLINK_ACCOUNT_SERVICE_FAILURE = 10165;

            public static final int VERIFY_ACCOUNT_LIST_SERVICE_FAILURE = 10166;

            public static final int LOCATION_LIST_SERVICE_FAILURE = 10167;
            public static final int LOCATION_EMISSION_SERVICE_FAILURE = 10168;

            public static final int BEACON_LIST_SERVICE_FAILURE = 10169;
            public static final int DOCUMENT_ENROLLED_SERVICE_FAILURE = 10170;

            public static final int SOCIAL_TOKEN_SERVICE_FAILURE = 10171;

            public static final int EMPTY_DELETE_VERIFICATION_SERVICE = 10172;
            public static final int DELETE_VERIFICATION_SERVICE_FAILURE = 10173;

            public static final int END_POINTS_SERVICE_FAILURE = 10174;
            public static final int DENY_REQUEST_SERVICE_FAILURE = 10175;
            public static final int UPDATE_FCM_SERVICE_FAILURE = 10176;
            public static final int PENDING_NOTIFICATION_LIST_SERVICE_FAILURE = 10177;*/










  /*public static final int DELETE_EMAIL_MFA_FAILURE = 10090;
  public static final int DELETE_BACKUPCODE_MFA_FAILURE = 10091;
  public static final int DELETE_SMS_MFA_FAILURE = 10092;
  public static final int DELETE_IVR_MFA_FAILURE = 10093;
  public static final int DELETE_PATTERN_MFA_FAILURE = 10094;
  public static final int DELETE_FACE_MFA_FAILURE = 10095;
  public static final int DELETE_VOICE_MFA_FAILURE = 10096;
  public static final int DELETE_TOTP_MFA_FAILURE = 10097;
  public static final int DELETE_FINGERPRINT_MFA_FAILURE = 10098;
  public static final int DELETE_SMARTPUSH_MFA_FAILURE = 10099;*/
