package com.example.cidaasv2.Controller.Repository.MFASettings;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListDeviceEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseDataEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Verification.Settings.VerificationSettingsService;

import timber.log.Timber;

public class MFAListSettingsController {



    private Context context;

    public static MFAListSettingsController shared;

    public MFAListSettingsController(Context contextFromCidaas) {

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static MFAListSettingsController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new MFAListSettingsController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

   /* //Get MFA list
    public void getmfaList(@NonNull String sub, final Result<MFAListResponseEntity> result) {
        try {
            //Todo Check notnull in db
            if(savedProperties==null){

                savedProperties= DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            String baseurl = "";
            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "MFALIST readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");
                getmfaListService(baseurl,sub,result);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/
    //Service call To Get MFA list
    public void getmfaList(@NonNull final String baseurl, @NonNull String sub, final Result<MFAListResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                // Change service call to private

                String userDeviceId= DBHelper.getShared().getUserDeviceId(baseurl);

                VerificationSettingsService.getShared(context).getmfaList( baseurl,sub, userDeviceId,new Result<MFAListResponseEntity>() {

                    @Override
                    public void success(MFAListResponseEntity serviceresult) {
                        result.success(serviceresult);

                        MFAListResponseDataEntity[] data=serviceresult.getData();
                        PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();

                        for (MFAListResponseDataEntity mfaList:data
                                ) {
                            if(mfaList.getVerificationType().equals("EMAIL")) {

                                //Todo move to button Click
                                physicalVerificationEntity.setUserDeviceId(mfaList.get_id());

                                if(mfaList.get_id()!=null)
                                {
                                    DBHelper.getShared().setUserDeviceId(mfaList.get_id(),baseurl);
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(WebAuthError error) {

                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {

            result.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e(e.getMessage());
        }
    }

}
