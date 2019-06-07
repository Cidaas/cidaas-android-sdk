package com.example.cidaasv2.Helper.CommonError;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Response;

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
    public WebAuthError generateCommonErrorEntity(int webAuthErrorCode, Response response,String methodName)
    {
        try {

            assert response.errorBody() != null;

            response.message().toLowerCase();

            // Handle proper error message
            String errorResponse = response.errorBody().source().readByteString().utf8();

            if(errorResponse.contains("<!DOCTYPE html>"))
            {
               return WebAuthError.getShared(context).emptyResponseException(webAuthErrorCode, HttpStatusCode.NOT_FOUND,methodName);
            }

            if(response.code()==401)
            {
                return WebAuthError.getShared(context).unAuthorizedAccess(webAuthErrorCode,errorResponse,methodName);
            }
            final CommonErrorEntity commonErrorEntity;
            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

            LogFile.getShared(context).addFailureLog("Exception:- WebAuthErrorCode: "+webAuthErrorCode+"Response Message:-" + response.message()+
                    " ErrorCode:- "+commonErrorEntity.getError().getCode()+ "error message:-" + commonErrorEntity.getError().getError());


            return  WebAuthError.getShared(context).serviceCallException(webAuthErrorCode,  commonErrorEntity.getError().getError(), commonErrorEntity.getStatus(),
                    commonErrorEntity.getError(),errorResponse,methodName);

          /*  String errorMessage = "";

            ErrorEntity errorEntity = new ErrorEntity();*/

          /*  if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("")
                    && commonErrorEntity.getError() instanceof String ) {
                errorMessage = commonErrorEntity.getError().toString();
            }

            else if(commonErrorEntity.getError() instanceof Integer){
                errorMessage=commonErrorEntity.getError_description();
            }*/
          //  else {

                //Handle For null using 500 internal servier error by

                //Error is integer
                //Error Message
               /* if(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("error").toString().equals("")) {
                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                }

                //Code
                if(((LinkedHashMap) commonErrorEntity.getError()).get("code")!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("code").toString().equals("")) {
                    errorEntity.setCode((int) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));

              *//*      String metadata=((LinkedHashMap) commonErrorEntity.getError()).get("metadata").toString();

                    FaceMetaData faceMetaData =new FaceMetaData();

                    faceMetaData =objectMapper.readValue(metadata, FaceMetaData.class);

                    errorEntity.setMetadata(faceMetaData);*//*

                   *//*
                    if(errorEntity.getCode()==3066)
                    {

                    }
                    else if(errorEntity.getCode()==3067)
                    {
                        VoiceMetaData voiceMetaData=new VoiceMetaData();
                        voiceMetaData=objectMapper.readValue(metadata, VoiceMetaData.class);
                        errorEntity.setVoiceMetaData(voiceMetaData);
                    }*//*
                }

                //More Info
                if(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString().equals("")) {
                    errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                }

                //Reference Number
                if(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString().equals("")) {
                    errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                }


                //Status
                if(((LinkedHashMap) commonErrorEntity.getError()).get("status").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("status").toString().equals("")) {
                    errorEntity.setStatus((Integer)((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                }

                //Type
                if(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("type").toString().equals("")) {
                    errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                }

                //metaData
                if(((LinkedHashMap) commonErrorEntity.getError()).get("metadata").toString()!=null && !((LinkedHashMap) commonErrorEntity.getError()).get("metadata").toString().equals("")) {
                    errorEntity.setMetadata((FaceMetaData) ((LinkedHashMap) commonErrorEntity.getError()).get("metadata"));
                }
*/
          //  }



        }
        catch (Exception e) {

            return WebAuthError.getShared(context).methodException("Exception :CommonError :generateCommonErrorEntity()",webAuthErrorCode,e.getMessage());

        }
    }
}
