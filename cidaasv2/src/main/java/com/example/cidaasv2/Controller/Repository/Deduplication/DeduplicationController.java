package com.example.cidaasv2.Controller.Repository.Deduplication;

import android.content.Context;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Repository.Deduplication.DeduplicationService;
import com.example.cidaasv2.Service.Repository.Login.LoginService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class DeduplicationController {
    private Context context;
    private String ConsentName;
    private String ConsentVersion;
    private String TrackId;

    public static DeduplicationController shared;

    public DeduplicationController(Context contextFromCidaas) {

        context = contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static DeduplicationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new DeduplicationController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void getDeduplicationList( final String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult)
    {
        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                String baseurl = result.get("DomainURL");
                DeduplicationService.getShared(context).getDeduplicationList(baseurl,trackId,deduplicaionResult);
            }

            @Override
            public void failure(WebAuthError error) {
             deduplicaionResult.failure(error);
            }
        });


    }

    public void registerDeduplication(@NonNull String baseurl, @NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult)
    {
        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                String baseurl = result.get("DomainURL");
                String clientId = result.get("ClientId");
                if (trackId != null && !trackId.equals("")) {
                    DeduplicationService.getShared(context).registerDeduplication(baseurl,trackId,deduplicaionResult);
                } else {
                    String errorMessage = "TrackId Must not be null";
                    deduplicaionResult.failure(WebAuthError.getShared(context).customException(417, errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                }
            }

            @Override
            public void failure(WebAuthError error) {
                deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
            }
        });

    }

    public void loginDeduplication(@NonNull final String requestId, @NonNull final String sub, @NonNull final String password,
                                   final Result<LoginCredentialsResponseEntity> deduplicaionResult)
    {
      try
      {
              CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                  @Override
                  public void success(Dictionary<String, String> result) {
                      String baseurl = result.get("DomainURL");
                      String clientId = result.get("ClientId");
                      if (sub != null && !sub.equals("") && password != null && !password.equals("")) {
                          LoginEntity loginEntity=new LoginEntity();
                          loginEntity.setUsername(sub);
                          loginEntity.setUsername_type("sub");
                          loginEntity.setPassword(password);


                          LoginCredentialsRequestEntity loginCredentialsRequestEntity=new LoginCredentialsRequestEntity();
                          loginCredentialsRequestEntity.setUsername(sub);
                          loginCredentialsRequestEntity.setPassword(password);
                          loginCredentialsRequestEntity.setUsername_type("sub");
                          loginCredentialsRequestEntity.setRequestId(requestId);

                          LoginService.getShared(context).loginWithCredentials(baseurl,loginCredentialsRequestEntity,deduplicaionResult);

                      } else {
                          String errorMessage = "Sub or requestId or Password Must not be null";
                          deduplicaionResult.failure(WebAuthError.getShared(context).customException(417, errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                      }
                  }

                  @Override
                  public void failure(WebAuthError error) {
                      deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                  }
              });
      }
      catch (Exception e)
      {
          deduplicaionResult.failure(WebAuthError.getShared(context).serviceException("Exception :DeduplicationController :setAccessToken()",WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,e.getMessage()));
      }
    }
}
