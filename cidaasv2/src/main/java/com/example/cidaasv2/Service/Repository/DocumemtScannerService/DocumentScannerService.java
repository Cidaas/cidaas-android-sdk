package com.example.cidaasv2.Service.Repository.DocumemtScannerService;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DocumentScannerService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static DocumentScannerService shared;

    public DocumentScannerService(Context contextFromCidaas) {

        context=contextFromCidaas;

        if(service==null) {
            service=new CidaassdkService();
        }
    }

    public static DocumentScannerService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new DocumentScannerService(contextFromCidaas);

            }

        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Get Deduplication info
    public void sendDocuemntToService(String baseurl, File photoDoc, String accessToken,DeviceInfoEntity deviceInfoEntityFromparam,final Result<DocumentScannerServiceResultEntity> callback)
    {
        //Local Variables
        String DocumentURL = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId

                //Todo Chnage URL Global wise
                DocumentURL=baseurl+ URLHelper.getShared().getDocumentScanner();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            // Get Device Information

            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
            }


            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoDoc);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "cidaasDocs.jpg", requestFile);


            //Todo - check Construct Headers pending,Null Checking Pending

            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            headers.put("access_token",accessToken);

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.enrollDocument(DocumentURL,headers,photo).enqueue(new Callback<DocumentScannerServiceResultEntity>() {
                @Override
                public void onResponse(Call<DocumentScannerServiceResultEntity> call, Response<DocumentScannerServiceResultEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DOCUMENT_VERIFICATION_FAILURE,
                                    "Service failure but successful response" , 400,null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DOCUMENT_VERIFICATION_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<DocumentScannerServiceResultEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DOCUMENT_VERIFICATION_FAILURE,
                            t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).serviceException("Exception :DocumentScannerService :sendDocuemntToService()",WebAuthErrorCode.DOCUMENT_VERIFICATION_FAILURE,e.getMessage()));
        }
    }
}
