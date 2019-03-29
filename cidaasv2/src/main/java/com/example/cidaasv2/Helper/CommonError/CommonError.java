package com.example.cidaasv2.Helper.CommonError;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

import retrofit2.Response;
import timber.log.Timber;

public class CommonError {

    public static CommonError shared;
    private Context context;

    private ObjectMapper objectMapper=new ObjectMapper();

    public  CommonError(Context contextFromCidaas) {
        context=contextFromCidaas;
    }


    public static CommonError getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  CommonError(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            // Timber.i(e.getMessage());
        }
        return shared;
    }

    //Method to get Error Entity
    public WebAuthError generateCommonErrorEntity(int webAuthErrorCode, Response response)
    {
        try {

            // Handle proper error message
            String errorResponse = response.errorBody().source().readByteString().utf8();
            final CommonErrorEntity commonErrorEntity;
            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

            String errorMessage = "";

            ErrorEntity errorEntity = new ErrorEntity();

            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                errorMessage = commonErrorEntity.getError().toString();
            }
            else {

                //Handle For null using 500 internal servier error by

                //Error Message
                if(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("error").toString().equals("")) {
                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                }

                //Code
                if(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("code").toString().equals("")) {
                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                }

                //More Info
                if(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString().equals("")) {
                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                }

                //Reference Number
                if(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString().equals("")) {
                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                }


                //Status
                if(((LinkedHashMap) commonErrorEntity.getError()).get("status").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("status").toString().equals("")) {
                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("status").toString());
                }

                //Type
                if(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("type").toString().equals("")) {
                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                }

            }


            //Todo Service call For fetching the Consent details
            return  WebAuthError.getShared(context).serviceFailureException(webAuthErrorCode, errorMessage, commonErrorEntity.getStatus(),
                    commonErrorEntity.getError(), errorEntity);

        }
        catch (Exception e) {
            Timber.e("Exception:-"+webAuthErrorCode+"Response Message:-" + response.message()+"Exception message:-" + e.getMessage());
            LogFile.getShared(context).addRecordToLog("Exception:-"+webAuthErrorCode+"Response Message:-" + response.message()+"Exception message:-" + e.getMessage());
            return WebAuthError.getShared(context).serviceException(webAuthErrorCode);

        }
    }
}
