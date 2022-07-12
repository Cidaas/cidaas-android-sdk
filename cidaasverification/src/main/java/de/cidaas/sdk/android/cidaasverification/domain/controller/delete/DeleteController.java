package de.cidaas.sdk.android.cidaasverification.domain.controller.delete;

import android.content.Context;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.delete.DeleteEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.delete.DeleteResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.helper.VerificationURLHelper;
import de.cidaas.sdk.android.cidaasverification.domain.service.delete.DeleteService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.entity.UserInfo.UserInfoEntity;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;


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
    public void deleteVerification(final DeleteEntity deleteEntity, final EventResult<DeleteResponse> deleteResult) {
        checkDeleteEntity(deleteEntity, deleteResult);
    }


    //-------------------------------------checkDeleteEntity-----------------------------------------------------------
    private void checkDeleteEntity(final DeleteEntity deleteEntity, final EventResult<DeleteResponse> deleteResult) {
        String methodName = "DeleteController:-checkDeleteEntity()";
        try {
            if (deleteEntity.getVerificationType() != null && !deleteEntity.getVerificationType().equals("") && deleteEntity.getSub() != null &&
                    !deleteEntity.getSub().equals("")) {

                addProperties(deleteEntity, deleteResult);
            } else {
                deleteResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
                        "Error:" + methodName));
                return;
            }

        } catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final DeleteEntity deleteEntity, final EventResult<DeleteResponse> deleteResult) {
        String methodName = "DeleteController:-addProperties()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");


                    //App properties
                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                    deleteEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
                    deleteEntity.setClient_id(clientId);
                    deleteEntity.setDevice_id(deviceInfoEntity.getDeviceId());

                    //call delete call
                    callDelete(baseurl, deleteEntity, deleteResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    deleteResult.failure(error);
                }
            });

        } catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call delete Service-----------------------------------------------------------
    private void callDelete(String baseurl, final DeleteEntity deleteEntity, final EventResult<DeleteResponse> deleteResult) {
        String methodName = "DeleteController:-delete()";
        try {
            String deleteUrl = VerificationURLHelper.getShared().getDeleteURL(baseurl, deleteEntity.getVerificationType(), deleteEntity.getSub());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Delete Service call
            DeleteService.getShared(context).callDeleteService(deleteUrl, headers, deleteEntity, deleteResult);

        } catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //--------------------------------------------DeleteAll--------------------------------------------------------------
    public void deleteAllVerification(String baseURL, String clientId, final EventResult<DeleteResponse> deleteResult) {
//        DeleteEntity deleteEntity = new DeleteEntity();
//        callDeleteAll(deleteEntity, deleteResult);

        DeleteEntity deleteEntity = new DeleteEntity();
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
        deleteEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
        deleteEntity.setDevice_id(deviceInfoEntity.getDeviceId());
        deleteEntity.setSub(userInfoEntity.getSub());
        deleteEntity.setClient_id(clientId);
        callDeleteAll(baseURL,deleteEntity, deleteResult);

    }


    //--------------------------------------------callDeleteAll--------------------------------------------------------------
    private void callDeleteAll(String baseURL, final DeleteEntity deleteEntity,final EventResult<DeleteResponse> deleteResult) {
        String methodName = "DeleteController:-callDeleteAll()";
        try {
            String deleteAllUrl = VerificationURLHelper.getShared().getDeleteAllURL(baseURL, deleteEntity.getDevice_id());
            //String deleteAllUrl = VerificationURLHelper.getShared().getDeleteAllURL(baseurl, deviceInfoEntity.getDeviceId());

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Delete Service call
            DeleteService.getShared(context).callDeleteService(deleteAllUrl, headers, deleteEntity, deleteResult);
//            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
//                @Override
//                public void success(Dictionary<String, String> loginPropertiesResult) {
//                    final String baseurl = loginPropertiesResult.get("DomainURL");
//                    String clientId = loginPropertiesResult.get("ClientId");
//
//                    //Add Delete Properties
//                    DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
//                    deleteEntity.setPush_id(deviceInfoEntity.getPushNotificationId());
//                    deleteEntity.setClient_id(clientId);
//
//                    String deleteAllUrl = VerificationURLHelper.getShared().getDeleteAllURL(baseurl, deviceInfoEntity.getDeviceId());
//
//                    //headers Generation
//                    Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);
//
//                    //Delete Service call
//                    DeleteService.getShared(context).callDeleteService(deleteAllUrl, headers, deleteEntity, deleteResult);
//                }
//
//                @Override
//                public void failure(WebAuthError error) {
//                    deleteResult.failure(error);
//                }
//            });
        } catch (Exception e) {
            deleteResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


}
