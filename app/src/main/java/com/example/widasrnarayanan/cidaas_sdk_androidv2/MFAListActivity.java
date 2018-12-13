package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListDeviceEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseDataEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;

public class MFAListActivity extends AppCompatActivity {

    Cidaas cidaas;
    String sub,deviceID,trackid,consentName,consentVersion,RequestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfalist);

        cidaas=new Cidaas(this);
        Intent intent=getIntent();

        sub=intent.getStringExtra("sub");
        trackid=intent.getStringExtra("trackid");
        consentName=intent.getStringExtra("consentName");
        consentVersion=intent.getStringExtra("consentVersion");


        cidaas.getMFAList(sub, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {
                MFAListResponseDataEntity[] data=result.getData();
                for (MFAListResponseDataEntity mfaList:data
                        ) {

                    Toast.makeText(getApplicationContext(), "1" + mfaList.getVerificationType(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    public void email(View view){

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.MFA);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");



                cidaas.loginWithEmail(passwordlessEntity, new Result<InitiateEmailMFAResponseEntity>()
                {
                    @Override
                    public void success(InitiateEmailMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,EmailMFAActivity.class);
                        intent.putExtra("sub",sub);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
  }
    public void sms(View view){

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.MFA);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");

                cidaas.loginWithSMS(passwordlessEntity, new Result<InitiateSMSMFAResponseEntity>() {
                    @Override
                    public void success(InitiateSMSMFAResponseEntity result) {
                        Intent intent = new Intent(MFAListActivity.this, SMSMFAActivity.class);
                        intent.putExtra("statusId", result.getData().getStatusId());
                        intent.putExtra("deviceID", deviceID);
                        intent.putExtra("trackid", trackid);
                        intent.putExtra("consentName", consentName);
                        intent.putExtra("sub", sub);
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, "" + result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void ivr(View view){

            cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
             public void success(AuthRequestResponseEntity result) {
                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");

        cidaas.loginWithIVR(passwordlessEntity, new Result<InitiateIVRMFAResponseEntity>() {
            @Override
            public void success(InitiateIVRMFAResponseEntity result) {
                Intent intent=new Intent(MFAListActivity.this,IVRMFAActivity.class);
                intent.putExtra("statusId",result.getData().getStatusId());
                intent.putExtra("deviceID",deviceID);
                intent.putExtra("trackid",trackid);
                intent.putExtra("sub",sub);
                intent.putExtra("consentVersion",consentVersion);
                startActivity(intent);
                Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void failure(WebAuthError error) {
        Toast.makeText(MFAListActivity.this, "Faliure RequestID", Toast.LENGTH_SHORT).show();
    }
});
    }
    public void pattern(View view){

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {
                RequestId=result.getData().getRequestId();

                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");


                cidaas.loginWithEmail(passwordlessEntity, new Result<InitiateEmailMFAResponseEntity>()
                {
                    @Override
                    public void success(InitiateEmailMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,EmailMFAActivity.class);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        intent.putExtra("consentName",consentName);
                        intent.putExtra("sub",sub);
                        String code="";

                        cidaas.verifyEmail(code,result.getData().getStatusId(), new Result<LoginCredentialsResponseEntity>() {
                            @Override
                            public void success(LoginCredentialsResponseEntity result) {
                                Toast.makeText(MFAListActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(WebAuthError error) {

                            }
                        });
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, "RequestIdFailure", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void smartpush(View view){

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.MFA);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");


                cidaas.loginWithSmartPush(passwordlessEntity,new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,SuccessfulLogin.class);
                        intent.putExtra("sub",sub);
                        intent.putExtra("accessToken",result.getData().getAccess_token());
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getAccess_token(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
  }
    public void face(View view){
   /*     cidaas.getmfaList(sub, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {
                MFAListResponseDataEntity[] data=result.getData();
                PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();

                for (MFAListResponseDataEntity mfaList:data
                        ) {
                    if (mfaList.getVerificationType().equals("BACKUPCODE")) {
                        MFAListDeviceEntity[] mfaListDeviceEntities=mfaList.getDevices();
                        deviceID=mfaListDeviceEntities[0].getDeviceId();
                        physicalVerificationEntity.setPhysicalVerificationId(mfaList.get_id());
                        physicalVerificationEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                        physicalVerificationEntity.setSub(sub);
                        physicalVerificationEntity.setUserDeviceId(mfaListDeviceEntities[0].get_id());

                    }

                }

                cidaas.initiateBackupCodeMFA(physicalVerificationEntity, new Result<InitiateBackupCodeMFAResponseEntity>() {
                    @Override
                    public void success(InitiateBackupCodeMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,BackupCodeMFAActivity.class);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        intent.putExtra("consentName",consentName);
                        intent.putExtra("sub",sub);
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, "Falire MFA", Toast.LENGTH_SHORT).show();
            }
        });
   */ }
    public void fingerprint(View view){
   /*     cidaas.getmfaList(sub, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {
                MFAListResponseDataEntity[] data=result.getData();
                PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();

                for (MFAListResponseDataEntity mfaList:data
                        ) {
                    if(mfaList.getVerificationType().equals("EMAIL")) {

                        //Todo move to button Click

                        MFAListDeviceEntity[] mfaListDeviceEntities=mfaList.getDevices();
                        deviceID=mfaListDeviceEntities[0].getDeviceId();
                        physicalVerificationEntity.setPhysicalVerificationId(mfaList.get_id());
                        physicalVerificationEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                        physicalVerificationEntity.setSub(sub);
                        physicalVerificationEntity.setUserDeviceId(mfaListDeviceEntities[0].get_id());

                    }
                }


                cidaas.initiateEmailMFA(physicalVerificationEntity, new Result<InitiateEmailMFAResponseEntity>() {
                    @Override
                    public void success(InitiateEmailMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,EmailMFAActivity.class);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        intent.putExtra("consentName",consentName);
                        intent.putExtra("sub",sub);
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, "Falire MFA", Toast.LENGTH_SHORT).show();
            }
        });
   */ }
    public void totp(View view){
     /*   cidaas.getmfaList(sub, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {
                MFAListResponseDataEntity[] data=result.getData();
                PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();

                for (MFAListResponseDataEntity mfaList:data
                        ) {
                    if (mfaList.getVerificationType().equals("TOTP")) {
                        MFAListDeviceEntity[] mfaListDeviceEntities=mfaList.getDevices();
                        physicalVerificationEntity.setPhysicalVerificationId(mfaList.get_id());
                        physicalVerificationEntity.setUsageType("MULTIFACTOR_AUTHENTICATION");
                        physicalVerificationEntity.setSub(sub);
                        physicalVerificationEntity.setUserDeviceId(mfaListDeviceEntities[0].get_id());

                    }


                }


                cidaas.initiateTOTP(physicalVerificationEntity, new Result<InitiateTOTPMFAResponseEntity>() {
                    @Override
                    public void success(InitiateTOTPMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,TOTPMFA.class);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        intent.putExtra("consentName",consentName);
                        intent.putExtra("sub",sub);
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, "Falire MFA", Toast.LENGTH_SHORT).show();
            }
        });
  */  }
    public void voice(View view){
   /*     cidaas.getmfaList(sub, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {
                MFAListResponseDataEntity[] data=result.getData();
                PhysicalVerificationEntity physicalVerificationEntity=new PhysicalVerificationEntity();

                for (MFAListResponseDataEntity mfaList:data
                        ) {
                    if (mfaList.getVerificationType().equals("IVR")) {
                        MFAListDeviceEntity[] mfaListDeviceEntities=mfaList.getDevices();
                        deviceID=mfaListDeviceEntities[0].getDeviceId();
                        physicalVerificationEntity.setPhysicalVerificationId(mfaList.get_id());
                        physicalVerificationEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                        physicalVerificationEntity.setSub(sub);
                        physicalVerificationEntity.setUserDeviceId(mfaListDeviceEntities[0].get_id());

                    }

                }


                cidaas.initiateIVRMFA(physicalVerificationEntity, new Result<InitiateIVRMFAResponseEntity>() {
                    @Override
                    public void success(InitiateIVRMFAResponseEntity result) {
                        Intent intent=new Intent(MFAListActivity.this,IVRMFAActivity.class);
                        intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        intent.putExtra("consentName",consentName);
                        intent.putExtra("sub",sub);
                        startActivity(intent);
                        Toast.makeText(MFAListActivity.this, ""+result.getData().getStatusId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(MFAListActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(MFAListActivity.this, "Falire MFA", Toast.LENGTH_SHORT).show();
            }
        });
   */ }
    }
