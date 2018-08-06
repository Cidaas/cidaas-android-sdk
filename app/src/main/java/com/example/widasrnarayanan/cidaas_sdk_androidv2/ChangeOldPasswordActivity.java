package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.UserProfile.UserprofileResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;

public class ChangeOldPasswordActivity extends AppCompatActivity {

    Cidaas cidaas;
    String sub,accessToken;
    EditText oldpass,newpass,Confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_old_password);

        cidaas=new Cidaas(this);

        oldpass=findViewById(R.id.oldpasswordText);
        newpass=findViewById(R.id.newpasswordtext);
        Confirmpass=findViewById(R.id.confirmtextbox);

        Intent intent=getIntent();
        sub=intent.getStringExtra("sub");
        accessToken=intent.getStringExtra("accessToken");







    }

    public void changebuttonClick(View view){

        final String oldpassword=oldpass.getText().toString();
        final String newpassword=newpass.getText().toString();
        final String confPass=Confirmpass.getText().toString();


        cidaas.getAccessToken(sub, new Result<AccessTokenEntity>() {
            @Override
            public void success(final AccessTokenEntity accessresult) {
                cidaas.getUserInfo(accessresult.getAccess_token(), new Result<UserinfoEntity>() {
                    @Override
                    public void success(UserinfoEntity result) {
                        Toast.makeText(ChangeOldPasswordActivity.this, ""+result.getLast_used_identity_id(), Toast.LENGTH_SHORT).show();

                        ChangePasswordRequestEntity changePasswordRequestEntity=new ChangePasswordRequestEntity();
                        changePasswordRequestEntity.setAccess_token(accessresult.getAccess_token());
                        changePasswordRequestEntity.setConfirm_password(confPass);
                        changePasswordRequestEntity.setIdentityId(result.getLast_used_identity_id());
                        changePasswordRequestEntity.setNew_password(newpassword);
                        changePasswordRequestEntity.setOld_password(oldpassword);

                        cidaas.changePassword(changePasswordRequestEntity, new Result<ChangePasswordResponseEntity>() {
                            @Override
                            public void success(ChangePasswordResponseEntity result) {
                                Toast.makeText(ChangeOldPasswordActivity.this, "Suceess", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Toast.makeText(ChangeOldPasswordActivity.this, "Fails", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ChangeOldPasswordActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });


       /* cidaas.getInternalUserProfile(accessToken,sub, new Result<UserprofileResponseEntity>() {
            @Override
            public void success(UserprofileResponseEntity userprofileresult) {
                ChangePasswordRequestEntity changePasswordRequestEntity=new ChangePasswordRequestEntity();
                changePasswordRequestEntity.setNew_password(newpassword);
                changePasswordRequestEntity.setConfirm_password(confPass);
                changePasswordRequestEntity.setIdentityId(userprofileresult.getData().getIdentity().get_id());
                changePasswordRequestEntity.setSub(sub);
                changePasswordRequestEntity.setOld_password(oldpassword);
                changePasswordRequestEntity.setAccess_token(accessToken);
                cidaas.changePassword(changePasswordRequestEntity, new Result<ChangePasswordResponseEntity>() {
                    @Override
                    public void success(ChangePasswordResponseEntity result) {
                        Intent intent=new Intent(ChangeOldPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ChangeOldPasswordActivity.this, "Password Changed SuccessFully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(ChangeOldPasswordActivity.this, "Password Changed Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(ChangeOldPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(ChangeOldPasswordActivity.this, "User info Failure", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }


}
