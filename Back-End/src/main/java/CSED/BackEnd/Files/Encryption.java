package CSED.BackEnd.Files;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.util.Base64;

public class Encryption {

    public static void storeKey(SecretKey ketToStore ,String password ,String path) throws Exception {
        File file = new File(path);
        KeyStore store = KeyStore.getInstance("JCEKS");
        if(!file.exists())
            store.load(null,null);
        store.setKeyEntry("keyAlias",ketToStore,password.toCharArray(),null);
        OutputStream writer = new FileOutputStream(file);
        store.store(writer,password.toCharArray());
    }

    public static SecretKey loadKey(String path ,String password){
        try{
            KeyStore store = KeyStore.getInstance("JCEKS");
            InputStream read = new FileInputStream(path);
            store.load(read,password.toCharArray());
            SecretKey key = (SecretKey) store.getKey("keyAlias",password.toCharArray());
            return key;
        }catch (Exception e){}
        return null;
    }

    public static SecretKey generate(String type){
        try{
            KeyGenerator generator = KeyGenerator.getInstance(type);
            SecretKey key = generator.generateKey();
            return key;
        }catch (Exception e){}
        return null;
    }

    public static String encrypt(String data , SecretKey key , Cipher cipher){
        try{
            byte[] txt = data.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] encrypted = cipher.doFinal(txt);
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){}
        return null;
    }

    public static String decrypt(String data ,SecretKey key ,Cipher cipher){
        try{
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] txt = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(txt);
        }catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        return null;
    }
}
