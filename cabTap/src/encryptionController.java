import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.lang.model.type.ArrayType;

import java.util.ArrayList;
import java.util.Base64;

public class encryptionController {
    private SecretKey key;
    private Cipher cipher;
    private Cipher decryptCipher;

    // init function to generate key
    encryptionController() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        key = keyGenerator.generateKey();
    }

    // (LegalName, UserName, Password, PhoneNumber)
    protected ArrayList<Object> encryptProfileCredentials(ArrayList<String> profile) throws Exception{
        ArrayList<Object> byteList = new ArrayList<Object>();
        for(int pass = 0; pass < 2; pass++){
            byteList.set(pass,profile.get(pass));
        }
        for(int item = 2; item < profile.size(); item++){
            byte[] itemBytes = profile.get(item).getBytes();
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptBytes = cipher.doFinal(itemBytes);
            byteList.set(item, encryptBytes);
        }
        return byteList;
    }

    protected ArrayList decryptProfileCredentials(ArrayList cipherText) throws Exception{
        for (int item = 2; item < cipherText.size(); item++){
            byte[] itemBytes = decode(cipherText);
            decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, Cipher.getIV());
            decryptCipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] decryptBytes = decryptCipher.doFinal(itemBytes);
            return new String(decryptBytes);
            
        }

    }

    protected void sendEncryption(){
        
    }

}
