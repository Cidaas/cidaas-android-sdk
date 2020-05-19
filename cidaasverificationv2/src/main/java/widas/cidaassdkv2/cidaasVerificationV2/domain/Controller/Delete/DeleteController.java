package widas.cidaassdkv2.cidaasVerificationV2.domain.Controller.Delete;

import android.content.Context;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;

import java.util.Dictionary;
import java.util.Map;

import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Delete.DeleteEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Delete.DeleteResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.Helper.VerificationURLHelper;
import widas.cidaassdkv2.cidaasVerificationV2.domain.Service.Delete.DeleteService;

public class DeleteController {

    //Local Variables
    private Context context;


    public static DeleteController shared;

    public DeleteController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static DeleteController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new DeleteController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("DeleteController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------Delete--------------------------------------------------------------
    public void deleteVerification(final DeleteEntity deleteEntity, final Result<DeleteResponse> deleteResult)
    {
        checkDeleteEntity(deleteEntity,deleteResult);
    }


    //-------------------------------------checkDeleteEntity-----------------------------------------------------------
    private void checkDeleteEntity(final DeleteEntity deleteEntity, final Result<DeleteResponse> deleteResult)
    {
        String methodName = "DeleteController:-checkDeleteEntity()";
        try {
            if (deleteEntity.getVerificationType() != null && !deleteEntity.getVerificationType().equals("") && deleteEntity.getSub() != null &&
                    !deleteEntity.getSub().equals("")) {

               addProperties(deleteEntity,deleteResult);
            }
            else
            {
                deleteResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:"+methodName));
                return;
            }

        }
        catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final DeleteEntity deleteEntity, final Result<DeleteResponse> deleteResult)
    {
        String methodName = "DeleteController:-addProperties()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId=loginPropertiesResult.get("ClientId");


                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    deleteEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    deleteEntity.setClient_id(clientId);
                    deleteEntity.setDevice_id(deviceInfoEntity.getDeviceId());

                    //call delete call
                    callDelete(baseurl,deleteEntity,deleteResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    deleteResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call delete Service-----------------------------------------------------------
    private void callDelete(String baseurl,final DeleteEntity deleteEntity, final Result<DeleteResponse> deleteResult)
    {
        String methodName = "DeleteController:-delete()";
        try
        {
            String deleteUrl= VerificationURLHelper.getShared().getDeleteURL(baseurl,deleteEntity.getVerificationType(),deleteEntity.getSub());

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //Delete Service call
            DeleteService.getShared(context).callDeleteService(deleteUrl,headers,deleteEntity,deleteResult);

        }
        catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //--------------------------------------------DeleteAll--------------------------------------------------------------
    public void deleteAllVerification(final Result<DeleteResponse> deleteResult)
    {
        DeleteEntity deleteEntity=new DeleteEntity();
        callDeleteAll(deleteEntity,deleteResult);
    }


    //--------------------------------------------callDeleteAll--------------------------------------------------------------
    private void callDeleteAll(final DeleteEntity deleteEntity, final Result<DeleteResponse> deleteResult)
    {
        String methodName = "DeleteController:-callDeleteAll()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId=loginPropertiesResult.get("ClientId");

                   //Add Delete Properties
                    DeviceInfoEntity deviceInfoEntity= DBHelper.getShared().getDeviceInfo();
                    deleteEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    deleteEntity.setClient_id(clientId);

                    String deleteAllUrl= VerificationURLHelper.getShared().getDeleteAllURL(baseurl,deviceInfoEntity.getDeviceId());

                    //headers Generation
                    Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                    //Delete Service call
                    DeleteService.getShared(context).callDeleteService(deleteAllUrl,headers,deleteEntity,deleteResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    deleteResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


}
