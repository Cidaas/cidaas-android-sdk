package com.example.cidaasv2.Controller.Repository.Deduplication;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Repository.Deduplication.DeduplicationService;
import com.example.cidaasv2.Service.Repository.Login.LoginService;

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


    public void getDeduplicationList(String baseurl, String trackId, Result<DeduplicationResponseEntity> deduplicaionResult)
    {
        DeduplicationService.getShared(context).getDeduplicationList(baseurl,trackId,deduplicaionResult);

    }

    public void registerDeduplication(@NonNull String baseurl,@NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult)
    {
        DeduplicationService.getShared(context).registerDeduplication(baseurl,trackId,deduplicaionResult);
    }

    public void loginDeduplication(@NonNull String baseurl, @NonNull String requestId, @NonNull String sub, @NonNull String password,
                                   final Result<LoginCredentialsResponseEntity> deduplicaionResult)
    {
      try
      {
          LoginEntity loginEntity=new LoginEntity();
          loginEntity.setUsername(sub);
          loginEntity.setUsername_type("sub");
          loginEntity.setPassword(password);


          LoginCredentialsRequestEntity loginCredentialsRequestEntity=new LoginCredentialsRequestEntity();
          loginCredentialsRequestEntity.setUsername(sub);
          loginCredentialsRequestEntity.setPassword(password);
          loginCredentialsRequestEntity.setUsername_type("sub");
          loginCredentialsRequestEntity.setRequestId(requestId);

          LoginService.getShared(context).loginWithCredentials(baseurl,loginCredentialsRequestEntity,null,deduplicaionResult);


      }
      catch (Exception e)
      {
          String errorMessage="Error:"+e.getMessage();
          deduplicaionResult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));

      }
    }
}
