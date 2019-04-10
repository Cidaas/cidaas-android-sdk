package com.example.cidaasv2.Controller.Repository.LocalAuthentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.cidaasv2.Helper.Entity.LocalAuthenticationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.R;

import timber.log.Timber;

public class LocalAuthenticationController {
    private Context context;

    private static final int LOCAL_AUTH_REQUEST_CODE = 303;
    private static final int LOCAL_REQUEST_CODE = 302;
    private static final int RESULT_OK = -1;


    public static LocalAuthenticationController shared;
    private Activity activityFromCidaas;
    private Result<LocalAuthenticationEntity> localAuthenticationEntityCallback;

    public LocalAuthenticationController(Context contextFromCidaas) {

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LocalAuthenticationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new LocalAuthenticationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void localAuthentication(final Activity activity, Result<LocalAuthenticationEntity> result) {
        try {


            activityFromCidaas=activity;
            localAuthenticationEntityCallback=result;

            KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
            boolean isSecure = keyguardManager.isKeyguardSecure();

            if (isSecure) {

                Intent intent = keyguardManager.createConfirmDeviceCredentialIntent(null, null);
                activity.startActivityForResult(intent, LOCAL_REQUEST_CODE);
            } else {
                // tabPager.setVisibility(View.GONE);
                // no lock screen set, show the new lock needed screen
                //showDialogToSetupLock(activity,result);
                result.failure(new WebAuthError(context).customException(WebAuthErrorCode.NO_LOCAL_AUHTHENTICATION_FOUND, "NO LOCAL AUTHENTICATION FOUND", 417));
            }
        }
        catch (Exception e)
        {
            result.failure(new WebAuthError(context).serviceException("Exception :LocalAuthenticationController :setAccessToken()",WebAuthErrorCode.LOCAL_AUHTHENTICATION_FAILED, e.getMessage()));
        }
    }

    //------------------------------------------------------------------------------------------Local Authentication----------------------------------------

    //Cidaas Set OnActivity Result For Handling Device Authentication
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if(activityFromCidaas!=null && localAuthenticationEntityCallback!=null ) {
                if (requestCode == LOCAL_REQUEST_CODE || requestCode == LOCAL_AUTH_REQUEST_CODE) {

                    if (requestCode == LOCAL_REQUEST_CODE || requestCode == LOCAL_AUTH_REQUEST_CODE) {
                        switch (requestCode) {
                            case LOCAL_REQUEST_CODE:
                                if (resultCode == RESULT_OK) {

                                    //Send Positive callback
                                    String user = "User Authenticated";

                                    LocalAuthenticationEntity localAuthenticationEntity=new LocalAuthenticationEntity();
                                    localAuthenticationEntity.setMessage(user);
                                    localAuthenticationEntity.setRequestCode(LOCAL_REQUEST_CODE);
                                    localAuthenticationEntity.setResultCode(resultCode);
                                    localAuthenticationEntityCallback.success(localAuthenticationEntity);


                                } else {
                                    // user did not authenticate so send failure callback


                                    String user = "User Cancelled the Authentication";


                                    localAuthenticationEntityCallback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.LOCAL_AUHTHENTICATION_CANCELLED,user,417));

                                    Timber.d("User" + user);

                                }
                                break;
                            case LOCAL_AUTH_REQUEST_CODE:
                                localAuthentication(activityFromCidaas,localAuthenticationEntityCallback);
                                break;
                            default:
                                //.onActivityResult(requestCode, resultCode, data);
                        }
                    }

                }
            }
           /* else
            {
                localAuthenticationEntityCallback.failure(new WebAuthError(context).customException(WebAuthErrorCode.LOCAL_AUHTHENTICATION_FAILED, "Call back must not be null", 417));
            }*/
        } catch (Exception e) {
            localAuthenticationEntityCallback.failure(new WebAuthError(context).serviceException("Exception :LocalAuthenticationController :onActivityResult()",WebAuthErrorCode.LOCAL_AUHTHENTICATION_FAILED,e.getMessage()));
        }
    }

    //Show the Alert Dilog Which is go to settings
    public void showDialogToSetupLock(final Activity activity,Result<LocalAuthenticationEntity> result) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.lock_setting_dialog, null);
            alertDialogBuilder.setView(dialog);
            alertDialogBuilder.setCancelable(false);
            Button btn_ok = dialog.findViewById(R.id.btn_ok);
            final AlertDialog alertDialog = alertDialogBuilder.create();

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }


                    String manufacturer = "xiaomi";
                    if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
                        //this will open auto start screen where user can enable permission for your app
                        activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), LOCAL_AUTH_REQUEST_CODE);
                    } else {

                        activity.startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), LOCAL_AUTH_REQUEST_CODE);
                    }
                }
            });
            alertDialog.show();
        }
        catch (Exception e)
        {
            result.failure(new WebAuthError(context).serviceException("Exception :LocalAuthenticationController :showDialogToSetupLock()",WebAuthErrorCode.LOCAL_AUHTHENTICATION_FAILED, e.getMessage()));
        }
    }
}
