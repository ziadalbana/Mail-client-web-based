package CSED.BackEnd.Files;

import CSED.BackEnd.Proxy.User;
import CSED.BackEnd.filter.Info;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;

public class file implements FileCompose {
    private String pathOfKey = "security.keystore";
    FilesUtilities file = new FilesUtilities();
    FileCompose userFolder;
    Cipher cipher ;

    {
        try {
            cipher = Cipher.getInstance("AES");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean check(String userName) throws Exception {
        if(!file.exist(pathOfKey)){
            Encryption.storeKey(Encryption.generate("AES"), keyPassword,pathOfKey);
        }
        List<String> lines = file.read(file.makPath("users","private.txt"));
        if(lines == null)  return false;
        for(int index=0;index<lines.size(); index += 2){
            String nameInFile = Encryption.decrypt(lines.get(index),Encryption.loadKey(pathOfKey, keyPassword),cipher);
            if(userName.equals(nameInFile))
                return true;
        }
        return false;
    }

    @Override
    public void createNewUser(User user) throws Exception {
        if(!file.exist(pathOfKey)){
            Encryption.storeKey(Encryption.generate("AES"),keyPassword,pathOfKey);
        }
        List<String> lines = new LinkedList<>();
        String name = Encryption.encrypt(user.getUserName(),Encryption.loadKey(pathOfKey,keyPassword),cipher);
        String pass = Encryption.encrypt(user.getPassword(),Encryption.loadKey(pathOfKey,keyPassword),cipher);
        lines.add(new String(name));
        lines.add(new String(pass));
        file.makdir("users");
        userFolder = new Directory();
        userFolder.createNewUser(user);
        file.write(file.makPath("users","private.txt"),lines,true);
    }

    public boolean logIn(String userName , String password) throws Exception {
        if(!file.exist(pathOfKey)){
            Encryption.storeKey(Encryption.generate("AES"), keyPassword,pathOfKey);
        }

        List<String> lines = file.read(file.makPath("users","private.txt"));
        for(int index=0; index<lines.size(); index += 2){
            String nameInFile = Encryption.decrypt(lines.get(index),Encryption.loadKey(pathOfKey, keyPassword),cipher);
            if(!userName.equals(nameInFile))
                continue;
            String passwordInFile = Encryption.decrypt(lines.get(index+1),Encryption.loadKey(pathOfKey, keyPassword),cipher);
            if (password.equals(passwordInFile))
                return true;
        }
        return false;
    }

    @Override
    public void deleteUser(String userName,boolean completely) throws Exception {
        if(!file.exist(pathOfKey)){
            Encryption.storeKey(Encryption.generate("AES"), keyPassword,pathOfKey);
        }
        List<String> lines = file.read(file.makPath("users","private.txt"));
        if(lines == null)  return;
        for(int index=0;index<lines.size(); index += 2){
            String nameInFile = Encryption.decrypt(lines.get(index),Encryption.loadKey(pathOfKey, keyPassword),cipher);
            if(userName.equals(nameInFile)) {
                lines.remove(index);
                lines.remove(index);
                userFolder = new Directory();
                userFolder.deleteUser(userName,completely);
                file.write(file.makPath("users","private.txt"),lines,false);
                break;
            }
        }
        return;
    }

    @Override
    public List<Info> load(String userName) {
        return file.readAllMails(userName);
    }

}
