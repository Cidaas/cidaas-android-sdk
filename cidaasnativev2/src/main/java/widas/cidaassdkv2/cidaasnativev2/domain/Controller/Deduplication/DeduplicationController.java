package widas.cidaassdkv2.cidaasnativev2.domain.Controller.Deduplication;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.LoginCredentialsResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Login.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;

import java.util.Dictionary;

import timber.log.Timber;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Deduplication.DeduplicationResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Login.LoginCredentialsRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.domain.Service.Deduplication.DeduplicationService;
import widas.cidaassdkv2.cidaasnativev2.domain.Service.Login.NativeLoginService;

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

    //-------------------------------------------------getDeduplicationList-------------------------------------------------------------

    public void getDeduplicationList( final String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult)
    {
        String methodName="DeduplicationController:getDeduplicationList()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    DeduplicationService.getShared(context).getDeduplicationList(baseurl, trackId, deduplicaionResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    deduplicaionResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            deduplicaionResult.failure(WebAuthError.getShared(context).methodException("Exception:"+methodName,WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE
                    ,e.getMessage()));
        }
    }

    //-------------------------------------------------registerDeduplication-------------------------------------------------------------
    public void registerDeduplication(@NonNull String baseurl, @NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult)
    {
        final String methodName="DeduplicationController:registerDeduplication()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");
                    if (trackId != null && !trackId.equals("")) {
                        DeduplicationService.getShared(context).registerDeduplication(baseurl, trackId, deduplicaionResult);
                    } else {
                        String errorMessage = "TrackId Must not be null";
                        deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));

                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    deduplicaionResult.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("",methodName));
                }
            });
        }
        catch (Exception e)
        {
            deduplicaionResult.failure(WebAuthError.getShared(context).methodException("Exception:"+methodName,WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE
                    ,e.getMessage()));
        }

    }

    //-------------------------------------------------registerDeduplication-------------------------------------------------------------

    //todo Log
    public void loginDeduplication(@NonNull final String requestId, @NonNull final String sub, @NonNull final String password,
                                   final Result<LoginCredentialsResponseEntity> deduplicaionResult)
    {
        final String methodName="DeduplicationController:loginDeduplication()";
      try
      {
              CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                  @Override
                  public void success(Dictionary<String, String> result) {

                      LoginEntity loginEntity = new LoginEntity();
                      loginEntity.setUsername(sub);
                      loginEntity.setUsername_type("sub");
                      loginEntity.setPassword(password);

                      loginCredenditalsWithSub(result,loginEntity,requestId,deduplicaionResult);
                  }



                  @Override
                  public void failure(WebAuthError error) {
                      deduplicaionResult.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("",methodName));
                  }
              });
      }
      catch (Exception e)
      {
          deduplicaionResult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,
                  WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,e.getMessage()));
      }
    }

    //-------------------------------------------------registerDeduplication-------------------------------------------------------------

    private void loginCredenditalsWithSub(Dictionary<String, String> result,LoginEntity loginEntity,String requestId,
                                          final Result<LoginCredentialsResponseEntity> deduplicaionResult)
    {
        String methodName="DeduplicationController :loginCredenditalsWithSub()";
        try {
            String baseurl = result.get("DomainURL");
            String clientId = result.get("ClientId");
            if (loginEntity.getUsername() != null && !loginEntity.getUsername().equals("") && loginEntity.getPassword() != null &&
                    !loginEntity.getPassword().equals("")) {



                LoginCredentialsRequestEntity loginCredentialsRequestEntity = new LoginCredentialsRequestEntity();
                loginCredentialsRequestEntity.setUsername(loginEntity.getUsername());
                loginCredentialsRequestEntity.setPassword(loginEntity.getPassword());
                loginCredentialsRequestEntity.setUsername_type("sub");
                loginCredentialsRequestEntity.setRequestId(requestId);

                NativeLoginService.getShared(context).loginWithCredentials(baseurl, loginCredentialsRequestEntity, deduplicaionResult);

            } else {
                String errorMessage = "Sub or requestId or Password Must not be null";
                deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
            }
        }
        catch (Exception e)
        {
            deduplicaionResult.failure(WebAuthError.getShared(context).methodException("Exception:"+methodName,WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                    e.getMessage()));
        }
    }
}
