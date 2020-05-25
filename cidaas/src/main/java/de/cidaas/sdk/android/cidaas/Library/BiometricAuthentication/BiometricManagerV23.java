package de.cidaas.sdk.android.cidaas.Library.BiometricAuthentication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cidaasv2.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;


@TargetApi(Build.VERSION_CODES.M)
public class BiometricManagerV23 {

    private static final String KEY_NAME = UUID.randomUUID().toString();

    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManagerCompat.CryptoObject cryptoObject;
    private FingerprintManager.CryptoObject cryptoObjectManger;


    protected Context context;

    protected String title;
    protected String subtitle;
    protected String description;
    protected String negativeButtonText;


    private TextView itemTitle, itemDescription, itemSubtitle, itemStatus;
    Button btnCancel;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    CancellationSignal cancellationSignal;
   /* public void displayBiometricPromptV23(final BiometricCallback biometricCallback) {
        generateKey();

        if(initCipher()) {

            cancellationSignal=new CancellationSignal();
            cryptoObject = new FingerprintManagerCompat.CryptoObject(cipher);
            final FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);

            fingerprintManagerCompat.authenticate(cryptoObject, 0, cancellationSignal,
                    new FingerprintManagerCompat.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            super.onAuthenticationError(errMsgId, errString);

                            if(errMsgId!=5) {
                                updateStatus(String.valueOf(errString));

                            }
                            biometricCallback.onAuthenticationError(errMsgId, errString);
                        }

                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            super.onAuthenticationHelp(helpMsgId, helpString);
                            updateStatus(String.valueOf(helpString));
                            biometricCallback.onAuthenticationHelp(helpMsgId, helpString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            dismissDialog();
                            biometricCallback.onAuthenticationSuccessful();
                        }


                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            updateStatus(context.getString(R.string.biometric_failed));
                            biometricCallback.onAuthenticationFailed();
                        }
                    }, null);

            displayBiometricDialog(biometricCallback);
        }
    }*/


    public void displayBiometricPromptV23Manger(final BiometricCallback biometricCallback) {
        generateKey();

        if (initCipher()) {

            cancellationSignal = new CancellationSignal();
            cryptoObjectManger = new FingerprintManager.CryptoObject(cipher);

            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

            fingerprintManager.authenticate(cryptoObjectManger, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == 5) {
                        // biometricCallback.onAuthenticationCancelled();
                        //  return;
                    } else {
                        updateStatus(String.valueOf(errString));
                        return;
                    }
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    super.onAuthenticationHelp(helpCode, helpString);
                    updateStatus(String.valueOf(helpString));
                    biometricCallback.onAuthenticationHelp(helpCode, helpString);
                    return;
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    dismissDialog();
                    biometricCallback.onAuthenticationSuccessful();
                    return;
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    updateStatus(context.getString(R.string.biometric_failed));
                    biometricCallback.onAuthenticationFailed();
                    return;
                }
            }, null);

            displayBiometricDialog(biometricCallback);
        }
    }

    private void displayBiometricDialog(final BiometricCallback biometricCallback) {

        displayBiometricAlertDialog(biometricCallback);
    }


    private void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

   /* private void updateStatus(String status) {
        if(biometricDialogV23 != null) {
            biometricDialogV23.updateStatus(status);
        }
    }*/


    private void displayBiometricAlertDialog(final BiometricCallback biometricCallback) {

        builder = new AlertDialog.Builder(context);

        intitaliseElements(biometricCallback);

        builder.setCancelable(false);
        alertDialog = builder.create();

        alertDialog.show();

    }

    private void intitaliseElements(final BiometricCallback biometricCallback) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.view_bottom_sheet_2, null);

        btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAlertDialog();

                if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
                    cancellationSignal.cancel();
                    biometricCallback.onAuthenticationCancelled();
                    return;
                }
             /*
                return;*/
            }
        });


        itemTitle = dialogView.findViewById(R.id.item_title);
        itemStatus = dialogView.findViewById(R.id.item_status);
        itemSubtitle = dialogView.findViewById(R.id.item_subtitle);
        itemDescription = dialogView.findViewById(R.id.item_description);

        assingnValues();

        if (builder != null) {
            builder.setView(dialogView);
        }


    }

    private void assingnValues() {
        setTitle(title);
        setSubtitle(subtitle);
        setDescription(description);
        setButtonText(negativeButtonText);

    }

    public void setTitle(String title) {
        itemTitle.setText(title);
    }

    public void updateStatus(String status) {

        itemStatus.setText(status);
    }

    public void setSubtitle(String subtitle) {
        itemSubtitle.setText(subtitle);
    }

    public void setDescription(String description) {
        itemDescription.setText(description);
    }

    public void setButtonText(String negativeButtonText) {
        btnCancel.setText(negativeButtonText);
    }


    private void dismissAlertDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


    private void generateKey() {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
        }
    }


    private boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;


        } catch (KeyPermanentlyInvalidatedException e) {
            return false;

        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {

            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
