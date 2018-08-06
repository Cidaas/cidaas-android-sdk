package com.example.cidaasv2.Helper.pkce;

import android.util.Base64;

import com.example.cidaasv2.Helper.Genral.FileHelper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by widasrnarayanan on 6/2/18.
 */

public class OAuthChallengeGenerator {
    //Shared Instances
    public static OAuthChallengeGenerator shared;


    public static OAuthChallengeGenerator getShared()
    {
        if(shared==null)
        {
            shared=new OAuthChallengeGenerator();
        }
        return shared;
    }



    public  String codeChallengeMethod = "S256";
    public String getCodeChallenge(String codeVerifier) {
        String challenge=null;
        try {
            byte[] bytes=codeVerifier.getBytes("US-ASCII");
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            md.update(bytes,0,bytes.length);
            byte[] digest=md.digest();
            challenge = Base64.encodeToString(digest,Base64.URL_SAFE|Base64.NO_WRAP|Base64.NO_PADDING);
            return challenge;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return challenge;
    }
    //Code verifier
    public String getCodeVerifier()
    {
      try {
          SecureRandom secureRandom = new SecureRandom();
          byte[] code = new byte[32];
          secureRandom.nextBytes(code);
          String verifier = Base64.encodeToString(code, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
          return verifier;
      }
      catch (Exception e)
      {
          return null;
      }
    }
}
