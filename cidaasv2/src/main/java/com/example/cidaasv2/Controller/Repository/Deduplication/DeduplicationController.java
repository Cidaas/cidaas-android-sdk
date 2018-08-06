package com.example.cidaasv2.Controller.Repository.Deduplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cidaasv2.Controller.Repository.Consent.ConsentController;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.LoginDeduplication.LoginDeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Repository.Deduplication.DeduplicationService;

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

    public void loginDeduplication(@NonNull String baseurl,@NonNull String sub,@NonNull String password,
                                   final Result<LoginDeduplicationResponseEntity> deduplicaionResult)
    {
      try
      {
          LoginEntity loginEntity=new LoginEntity();
          loginEntity.setUsername(sub);
          loginEntity.setUsername_type("sub");
          loginEntity.setPassword(password);

          DeduplicationService.getShared(context).loginDeduplication(baseurl,loginEntity,deduplicaionResult);

      }
      catch (Exception e)
      {

      }
    }
}
