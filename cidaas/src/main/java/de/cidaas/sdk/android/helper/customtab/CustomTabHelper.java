package de.cidaas.sdk.android.helper.customtab;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class CustomTabHelper {

    public static CustomTabHelper sharedinstance;

    public static CustomTabHelper getShared() {
        if (sharedinstance == null) {
            sharedinstance = new CustomTabHelper();
        }
        return sharedinstance;
    }


    //Known Browsers with Custom Tabs support
    private static final String TAG = "CustomTabsHelper";
    private static final String CHROME_STABLE = "com.android.chrome";
    private static final String CHROME_SYSTEM = "com.google.android.apps.chrome";
    private static final String CHROME_BETA = "com.android.chrome.beta";
    private static final String CHROME_DEV = "com.android.chrome.dev";

    private static final String ACTION_CUSTOM_TABS_CONNECTION =
            "android.support.customtabs.action.CustomTabsService";

    public String getPackageNameToUse(Context context) {


        PackageManager pm = context.getPackageManager();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
        ResolveInfo webHandler = pm.resolveActivity(browserIntent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PackageManager.MATCH_ALL : PackageManager.MATCH_DEFAULT_ONLY);
        String defaultBrowser = null;
        if (webHandler != null) {
            defaultBrowser = webHandler.activityInfo.packageName;
        }

        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(browserIntent, 0);
        List<String> customTabsBrowsers = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                customTabsBrowsers.add(info.activityInfo.packageName);
            }
        }
        if (customTabsBrowsers.contains(defaultBrowser)) {
            return defaultBrowser;
        } else if (customTabsBrowsers.contains(CHROME_STABLE)) {
            return CHROME_STABLE;
        } else if (customTabsBrowsers.contains(CHROME_SYSTEM)) {
            return CHROME_SYSTEM;
        } else if (customTabsBrowsers.contains(CHROME_BETA)) {
            return CHROME_BETA;
        } else if (customTabsBrowsers.contains(CHROME_DEV)) {
            return CHROME_DEV;
        } else if (!customTabsBrowsers.isEmpty()) {
            return customTabsBrowsers.get(0);
        } else {
            return null;
        }
    }


    /**
     * Used to check whether there is a specialized handler for a given intent.
     *
     * @param intent The intent to check with.
     * @return Whether there is a specialized handler for the given intent.
     */
    private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER);
            if (handlers == null || handlers.size() == 0) {
                return false;
            }
            for (ResolveInfo resolveInfo : handlers) {
                IntentFilter filter = resolveInfo.filter;
                if (filter == null) continue;
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue;
                if (resolveInfo.activityInfo == null) continue;
                return true;
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Runtime exception while getting specialized handlers");
        }
        return false;
    }

}
