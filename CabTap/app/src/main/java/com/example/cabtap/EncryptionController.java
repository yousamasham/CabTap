package com.example.cabtap;

import static com.google.android.gms.common.util.Base64Utils.decode;
import static com.google.android.gms.common.util.Base64Utils.encode;

import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.ArrayList;
import java.util.Base64;

public class EncryptionController {
    private static SecretKey key;
    private Cipher cipher;
    private Cipher decryptCipher;

    // init function to generate key
    EncryptionController() throws NoSuchAlgorithmException{

        byte[] encoded = {
                (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07,
                (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
        };

        this.key = new SecretKeySpec(encoded, "AES");
    }

    // (LegalName, UserName, Password, PhoneNumber)
    private ArrayList<String> encryptProfileCredentials(ArrayList<String> profile) throws Exception{
        ArrayList<String> byteList = new ArrayList<String>();
        for(int pass = 0; pass < 2; pass++){
            byteList.add(pass,profile.get(pass));
        }
        for(int item = 2; item < profile.size(); item++){
            byte[] itemBytes = profile.get(item).getBytes();
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] encryptBytes = cipher.doFinal(itemBytes);
            byteList.add(item, encode(encryptBytes));
        }
        return byteList;
    }

    // (LegalName, UserName, Password, PhoneNumber)
    private ArrayList<String> decryptProfileCredentials(ArrayList<String> cipherText) throws Exception{
        ArrayList<String> decrypted = new ArrayList<String>();
        for(int pass = 0; pass < 2; pass++){
            decrypted.add(pass,cipherText.get(pass));
        }
        for (int item = 2; item < cipherText.size(); item++){
            byte[] itemBytes = decode(cipherText.get(item));
            decryptCipher = Cipher.getInstance("AES");
            byte[] iv = decryptCipher.getIV();
            decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decryptBytes = decryptCipher.doFinal(itemBytes);
            decrypted.add(item, new String(decryptBytes));
        }
        return decrypted;
    }

    protected ArrayList<String> getEncryption(ArrayList<String> profile) throws Exception {
        //extract necessary values (aka username) from profile when passing to database
        try{
            return encryptProfileCredentials(profile);
        }
        catch(Exception E){
            throw E;
        }
    }

    protected ArrayList<String> getDecryption(ArrayList<String> profile) throws Exception {
        //extract necessary values (aka username) from profile when passing to user
        try{
            return decryptProfileCredentials(profile);
        }
        catch(Exception E){
            throw E;
        }
    }

}