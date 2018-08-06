package com.example.cidaasv2.Controller.Repository.Client;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Repository.Client.ClientService;
import com.example.cidaasv2.Service.Repository.OauthService;

import timber.log.Timber;

public class ClientController {

    private String authenticationType;
    private String verificationType;
    private Context context;

    public static ClientController shared;

    public ClientController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static ClientController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new ClientController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Get Client Info

  /*  public void getClientInfo(@NonNull String RequestId, final Result<ClientInfoEntity> clientInfoEntityResult) {
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
                        clientInfoEntityResult.failure(error);
                    }
                });
            }
            String baseurl = "";
            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Request-Id readProperties failure : " +
                "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                clientInfoEntityResult.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");
                getClientInfoService(baseurl,RequestId,clientInfoEntityResult);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }*/

    //Service call To Get Client Info
    public void getClientInfo(@NonNull String baseurl,@NonNull String RequestId,final Result<ClientInfoEntity> clientInfoEntityResult){
        try{

            if (baseurl != null && !baseurl.equals("") && RequestId != null && !RequestId.equals("")) {
                // Change service call to private
                ClientService.getShared(context).getClientInfo(RequestId, baseurl, new Result<ClientInfoEntity>() {

                    @Override
                    public void success(ClientInfoEntity serviceresult) {
                        clientInfoEntityResult.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        clientInfoEntityResult.failure(error);
                    }
                });
            }
            else
            {


                clientInfoEntityResult.failure(  WebAuthError.getShared(context)
                        .customException(WebAuthErrorCode.PROPERTY_MISSING,"one of the  ClientInfoService properties missing",400));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


}
