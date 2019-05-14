package com.example.cidaasv2.VerificationV2.data.Service;



import com.example.cidaasv2.VerificationV2.data.Entity.EnrollEntity.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.EnrollEntity.EnrollResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;



public interface ICidaasSDK_V2_Services {

    //Setup
    @POST
    Call<SetupResponse> setup(@Url String url, @HeaderMap Map<String,String> headers, @Body SetupEntity setupEntity);

    //Scanned
    @POST
    Call<ScannedResponse> scanned(@Url String url, @HeaderMap Map<String,String> headers, @Body ScannedEntity scannedEntity);

    //Enroll
    @POST
    Call<EnrollResponseEntity> enroll(@Url String url, @HeaderMap Map<String,String> headers, @Body EnrollEntity enrollEntity);

}
