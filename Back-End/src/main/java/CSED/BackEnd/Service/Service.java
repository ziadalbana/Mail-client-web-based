 package CSED.BackEnd.Service;

import CSED.BackEnd.Files.FileCompose;
import CSED.BackEnd.Files.FilesUtilities;
import CSED.BackEnd.Files.file;
import CSED.BackEnd.Proxy.*;
import CSED.BackEnd.Search.Search;
import CSED.BackEnd.Sort.ISort;
import CSED.BackEnd.Sort.sortFactory;
import CSED.BackEnd.builder.Attachment;
import CSED.BackEnd.builder.Email;
import CSED.BackEnd.filter.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Service {
    private static String userName ;
    FileCompose file = new file();
    IProxy proxy =new Proxy();

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public boolean SignIn(String userName , String password){
        boolean login = proxy.signIn(userName , password);
        if(login) this.userName = userName;
        return login;
    }

    public boolean check(String userName){
        try {
            return file.check(userName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean SignUp(User user){
        return proxy.signUp(user);
    }

    public void deleteUser(){
        if(userName == null) return;
        try {
            file.deleteUser(userName,true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public List<Object> getEmailsType(String filter){
        Ifilter f=new typeFilter(filter);
        return new LinkedList<>(f.filter(Singleton.getInstance().getInfos()));
    }
    public List<Object> getEmailFiltered(String filter,String choose,String word){
        Ifilter f1;
        Ifilter f2;
        List<Object> list=null;
        if(choose==null||word==null) return null;
        if(choose.equals("username")){
            f1=new typeFilter(filter);
            f2=new usernameFilter(word.replaceAll(" ",""));
            Ifilter f3=new andFilter(f1,f2);
            list = new LinkedList<>(f3.filter(Singleton.getInstance().getInfos()));
        }else if(choose.equals("subject")){
            f1=new typeFilter(filter);
            f2=new subjectFilter(word.replaceAll(" ",""));
            Ifilter f3=new andFilter(f1,f2);
            list = new LinkedList<>(f3.filter(Singleton.getInstance().getInfos()));
        }
        return list;
    }
    public List<Object> getEmailSorted(String filter, String type){
        List<Object> list;
        Ifilter f=new typeFilter(filter);
        List<Info> filteredlist=f.filter(Singleton.getInstance().getInfos());
        ISort sort=new sortFactory().getSortMethod(type);
        list = new LinkedList<>(sort.sort(filteredlist));
        return list;
    }
    public void load(){
        Singleton.getInstance().setInfos(file.load(this.userName));
    }

    public void reset(){
        Singleton.getInstance().setInfos(new LinkedList<>());
        userName = null;
    }
    public Email getEmail(String date){
        FilesUtilities ops = new FilesUtilities();
        return ops.getEmail(userName,date);
    }
    public List<Object> search(String words){
        Search search = new Search();
        return new LinkedList<>(search.search(words));
    }
    public List<Object> getUserData(){
        FilesUtilities ops = new FilesUtilities();
        return new LinkedList<>(ops.read(ops.makPath("users",userName,"index.txt")));
    }

    public void updateSetting(User user){
        proxy.updateSetting(user);
    }

    public void addContact(String userName){
        FilesUtilities ops = new FilesUtilities();
        ops.writeContact(ops.makPath("users",this.userName,"cantacts.txt"), userName);
    }
    public List<Object> getContacts(){
        FilesUtilities ops = new FilesUtilities();
        List<String> contacts = ops.read(ops.makPath("users",this.userName,"cantacts.txt"));
        if(contacts == null) return new LinkedList<>();
        return new LinkedList<>(contacts);
    }
    public void deleteContact(String userName){
        FilesUtilities ops = new FilesUtilities();
        List<String> contacts = ops.read(ops.makPath("users",this.userName,"cantacts.txt"));
        contacts.remove(userName);
        ops.write(ops.makPath("users",userName,"cantacts.txt"),contacts,false);
    }
    public Attachment getByteArray(String name,String path){
        byte[] bytes=new byte[0];
        try {
             bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
      Attachment n=new Attachment();
        n.setMyfile(name);
        n.setFile(bytes);
      return n ;
    }
}

