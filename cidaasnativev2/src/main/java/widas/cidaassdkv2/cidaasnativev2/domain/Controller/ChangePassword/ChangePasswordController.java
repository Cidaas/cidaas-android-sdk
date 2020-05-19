package widas.cidaassdkv2.cidaasnativev2.domain.Controller.ChangePassword;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;

import java.util.Dictionary;

import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.domain.Service.ChangePassword.ChangePasswordService;

public class ChangePasswordController {
    private String authenticationType;
    private String verificationType;
    private Context context;

    public static ChangePasswordController shared;

    public ChangePasswordController(Context contextFromCidaas) {

        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";
        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;

    // Generate Code Challenge and Code verifier
    public void generateChallenge() {
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier = generator.getCodeVerifier();
        codeChallenge = generator.getCodeChallenge(codeVerifier);

    }

    public static ChangePasswordController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ChangePasswordController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ChangePasswordController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }

    //ChangePasswordService
    public void changePassword(@NonNull final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    checkandChangePasswordService(baseurl, changePasswordRequestEntity, resetpasswordResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetpasswordResult.failure(error);
                }
            });


        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :ChangePassword Controller :changePassword()",
                    WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }

    private void checkandChangePasswordService(String baseurl, @NonNull final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult) {
        try {

            if (changePasswordRequestEntity.getConfirm_password() != null && !changePasswordRequestEntity.getConfirm_password().equals("")
                    && changePasswordRequestEntity.getNew_password() != null && !changePasswordRequestEntity.getNew_password().equals("")
                    && changePasswordRequestEntity.getIdentityId() != null && !changePasswordRequestEntity.getIdentityId().equals("")
                    && changePasswordRequestEntity.getOld_password() != null && !changePasswordRequestEntity.getOld_password().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                ChangePasswordService.getShared(context).changePassword(changePasswordRequestEntity, baseurl, null, resetpasswordResult);
            } else {
                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("Confirm Password or new password or IdentityID or Old password or baseurl must not be null"
                ,"Error :ChangePassword Controller :checkandChangePasswordService()"));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :ChangePassword Controller :checkandChangePasswordService()",
                    WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }
}
