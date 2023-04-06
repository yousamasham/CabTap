package com.example.cabtap;

import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.util.ArrayList;
import java.util.Base64;

public class EncryptionController {
    private SecretKey key;
    private Cipher cipher;
    private Cipher decryptCipher;

    // init function to generate key
    EncryptionController() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        this.key = keyGenerator.generateKey();
    }

    // (LegalName, UserName, Password, PhoneNumber)
    private ArrayList<Object> encryptProfileCredentials(ArrayList<String> profile) throws Exception{
        ArrayList<Object> byteList = new ArrayList<Object>();
        for(int pass = 0; pass < 2; pass++){
            byteList.add(pass,profile.get(pass));
        }
        for(int item = 2; item < profile.size(); item++){
            byte[] itemBytes = profile.get(item).getBytes();
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] encryptBytes = cipher.doFinal(itemBytes);
            byteList.add(item, encryptBytes);
        }
        return byteList;
    }

    // (LegalName, UserName, Password, PhoneNumber)
    private ArrayList<Object> decryptProfileCredentials(ArrayList<Object> cipherText) throws Exception{
        ArrayList<Object> decrypted = new ArrayList<Object>();
        for(int pass = 0; pass < 2; pass++){
            decrypted.add(pass,cipherText.get(pass));
        }
        for (int item = 2; item < cipherText.size(); item++){
            byte[] itemBytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                itemBytes = Base64.getDecoder().decode((byte[]) cipherText.get(item));
            }
            decryptCipher = Cipher.getInstance("AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, cipher.getIV());
            decryptCipher.init(Cipher.DECRYPT_MODE, this.key, spec);
            byte[] decryptBytes = decryptCipher.doFinal(itemBytes);
            decrypted.add(item, decryptBytes);
        }
        return decrypted;
    }

    protected ArrayList<Object> getEncryption(ArrayList<String> profile) throws Exception {
        //extract necessary values (aka username) from profile when passing to database
        try{
            return encryptProfileCredentials(profile);
        }
        catch(Exception E){
            throw E;
        }
    }

    protected ArrayList<Object> getDecryption(ArrayList<Object> profile) throws Exception {
        //extract necessary values (aka username) from profile when passing to user
        try{
            return decryptProfileCredentials(profile);
        }
        catch(Exception E){
            throw E;
        }
    }

}
