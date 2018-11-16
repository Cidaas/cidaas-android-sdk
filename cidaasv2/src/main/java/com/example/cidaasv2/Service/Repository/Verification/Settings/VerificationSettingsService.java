package com.example.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VerificationSettingsService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static VerificationSettingsService shared;

    public  VerificationSettingsService(Context contextFromCidaas) {
        context=contextFromCidaas;


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    private void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static  VerificationSettingsService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  VerificationSettingsService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getmfaList( String baseurl,String sub,String userDeviceID,DeviceInfoEntity deviceInfoEntityFromParam, final Result<MFAListResponseEntity> callback)
    {
        //Local Variables
        String mfalistUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                if(sub!=null && sub!=""){
                    //Construct URL For RequestId

                    //mfalistUrl=baseurl+ URLHelper.getShared().getMfa_URL();
                   //TODO change to getMfaList();
                     mfalistUrl=baseurl+ URLHelper.getShared().getMfaList();
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.MFA_LIST_FAILURE),
                            400,null,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.PROPERTY_MISSING),
                        400,null,null));
                return;
            }


            boolean common_configs = true;
            if(userDeviceID.equals("")) {

                DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
                //This is only for testing purpose
                if(deviceInfoEntityFromParam==null) {
                    deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                }
                else if(deviceInfoEntityFromParam!=null)
                {
                    deviceInfoEntity=deviceInfoEntityFromParam;
                }

                userDeviceID=deviceInfoEntity.getDeviceId();
            }
            else {

            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getmfaList(mfalistUrl,sub,userDeviceID,common_configs).enqueue(new Callback<MFAListResponseEntity>() {
                @Override
                public void onResponse(Call<MFAListResponseEntity> call, Response<MFAListResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<MFAListResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());



            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

}
