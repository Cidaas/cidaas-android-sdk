package de.cidaas.sdk.android.cidaasnative.util;

public class NativeConstants {

    private NativeConstants(){}

    public static final String ERROR_LOGGING_PREFIX = "Error :";
    public static final String EXCEPTION_LOGGING_PREFIX = "Exception :";
    public static final String DEVICE_VERSION = "device-version";
    public static final String DEVICE_MODEL = "device-model";
    public static final String DEVICE_MAKE = "device-make";
    public static final String DEVICE_ID = "device-id";
    public static final String DEVICE_LATTITUDE = "lat";
    public static final String DEVICE_LONGITUDE = "lon";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String USER_AGENT = "user-agent";

    public static final String DOMAIN_URL = "DomainURL";
    public static final String CLIENT_ID = "ClientId";
    public static final String REDIRECT_URL = "RedirectURL";

    public static final String DOMAIN_REDIRECT_URL_CLIENT_ID_EMPTY_MESSAGE = "DomainURL or ClientId or RedirectURL must not be empty";
    public static final String EXCEPTION_REQUEST_ID_MESSAGE = "Exception :Cidaas :getRequestId()";
}
