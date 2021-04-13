package CSED.BackEnd.Service;

import CSED.BackEnd.Files.FilesUtilities;
import CSED.BackEnd.Proxy.Singleton;
import CSED.BackEnd.builder.Email;
import CSED.BackEnd.filter.Info;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class emailOperation {
    private String userName;
    private String date;
    private ObjectMapper mapper = new ObjectMapper();
    private FilesUtilities files=new FilesUtilities();
    private boolean getUserName(){
        Service n=new Service();
        if(n.getUserName()==null){
            return false;
        }else {
            this.userName=n.getUserName();
        }
        System.out.println(userName);
        return true;
    }

    private String getDate() {
        return date;
    }
    private void setDate(String date) {
        this.date = date;
    }

    private ArrayList<String> getReceivers(Email email){
        return new ArrayList<>(email.getUsername());
    }
    private ArrayList<Email> setReceiversEmail(Email email){
        ArrayList<String> sender=new ArrayList<>();
        ArrayList<Email> emails=new ArrayList<>();
        sender.add(userName);
        int size=email.getUsername().size();
        for (int i=0;i<size;i++){
            email.setUsername(sender);
            email.setType("inbox");
            emails.add(email);
        }
        return emails;
    }
    private ArrayList<String> getInfoElements(Email email){
        ArrayList<String> result=new ArrayList<>();
        result.add(email.getSubject());
        result.add(email.getDate());
        result.add(email.getType());
        result.add(Integer.toString(email.getImportance()));
        return result;
    }
    private boolean handleSentEmail(Email email){
        ObjectMapper mapper = new ObjectMapper();
        FilesUtilities files=new FilesUtilities();
        String path= files.makPath("users",userName,"AllMails");
        files.writeLine(files.makPath(path,"index.txt"),email.getUsername(),true);
        files.writeLine(files.makPath(path,"index.txt"),getInfoElements(email),true);
        File file= new File(path,email.getDate());
        file.mkdirs();
        path=files.makPath(files.makPath("users",userName), "AllMails", email.getDate());
        file =new File(path,"email.json");
        email=attachmentsPath(email,userName);
        try {
            // Serialize Java object info JSON file.
            mapper.writeValue(file, email);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private boolean handleinboxEmails(Email email){
        ArrayList<String> recivers;
        ArrayList<Email> emails;
        recivers=getReceivers(email);
        emails=setReceiversEmail(email);
        ObjectMapper mapper = new ObjectMapper();
        FilesUtilities files=new FilesUtilities();
        for(int i=0;i<recivers.size();i++) {
            String path = files.makPath("users",recivers.get(i), "AllMails");
            files.writeLine(files.makPath(path,"index.txt"),emails.get(i).getUsername(),true);
            files.writeLine(files.makPath(path,"index.txt"),getInfoElements(emails.get(i)),true);
            File file = new File(path, email.getDate());
            file.mkdirs();
            path = files.makPath(files.makPath("users", recivers.get(i)), "AllMails", email.getDate());
            file = new File(path, "email.json");
            email=attachmentsPath(email,recivers.get(i));
            try {
                // Serialize Java object info JSON file.
                mapper.writeValue(file, emails.get(i));
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
    /**
     *
     * @param email email wanted ti sent
     * @return true if the methods finished correctly
     */
    public boolean sentEmail(Email email){
        if(!getUserName()) return false;
        setDate(email.getDate());
        if(!handleSentEmail(email))return false;
        if(!handleinboxEmails(email)) return false;
        return true;
    }

    public boolean draftEmail(Email email){
        if(!getUserName()) return false;
        setDate(email.getDate());
        String path= files.makPath("users",userName,"AllMails");
        files.writeLine(files.makPath(path,"index.txt"),email.getUsername(),true);
        files.writeLine(files.makPath(path,"index.txt"),getInfoElements(email),true);
        File file= new File(path,email.getDate());
        file.mkdirs();
        path=files.makPath(files.makPath("users",userName), "AllMails", email.getDate());
        file =new File(path,"email.json");
        try {
            // Serialize Java object info JSON file.
            mapper.writeValue(file, email);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private int search(List<Info> allmails,String date){
        int index=0;
        for(int i=0;i<allmails.size();i++){
            if(allmails.get(i).getDate().equals(date)){
                index=i;
                break;
            }
        }
        return index;
    }
    public boolean deleteEmail(String date) {
        if (!getUserName()) return false;
        List<Info> allMails=Singleton.getInstance().getInfos();
        int index=search(allMails,date);
        Info info=Singleton.getInstance().getInfos().remove(index);
        allMails=Singleton.getInstance().getInfos();
        String path;
        path= files.makPath(files.makPath("users",userName),"AllMails",date);
        Email email=files.getEmail(userName, date);
        email.setType("trash");
        info.setType("trash");
        File file =new File(path,"email.json");
        try {
            files.delete(path+"email.json");
            mapper.writeValue(file, email);
            path= files.makPath(files.makPath( "users",userName),"AllMails","index.txt");
            allMails.add(info);
            files.writeInfos(path,allMails);

        } catch (IOException e) {
            return false;
        }

        return true;
    }
    public boolean deleteDraft(String date){
        if(!getUserName()) return false;
        List<Info> allMails=Singleton.getInstance().getInfos();
        int index=search(allMails,date);
        Singleton.getInstance().getInfos().remove(index);
        allMails=Singleton.getInstance().getInfos();
        String path= files.makPath(files.makPath("users",userName),"AllMails",date);
        File file =new File(path,"email.json");
        delete(date);
        path= files.makPath(files.makPath( "users",userName),"AllMails","index.txt");
        files.writeInfos(path,allMails);

        return true;
    }
    private Email attachmentsPath(Email email,String userName){
        Email temp=email;
        int size;
        List<String> paths=new LinkedList<>();
        if(email.getAttachment()==null){
            size =0;
        }else{
            size=email.getAttachment().size();
        }
        for(int i=0;i<size;i++){
            String path= files.makPath(files.makPath( "users",userName,"AllMails"),date,"attachment");
            path=files.makPath(path,email.getAttachment().get(i));
            paths.add(path);
        }
        temp.setAttachmentPath(paths);
        return temp ;
    }
     private void delete(String date) {
        String path=files.makPath(files.makPath("users",userName,"AllMails"),date);
        try {
            files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean attachmentSet(List<MultipartFile> attachments){
        if(!getUserName()) return false;
        String path =files.makPath(files.makPath("users",userName,"AllMails"),getDate(),"attachment");
        files.makdir(path);
        for(int i=0;i<attachments.size();i++){
            try {
                path =files.makPath(files.makPath("users",userName,"AllMails"),getDate(),"attachment");
                path= files.makPath(path,attachments.get(i).getOriginalFilename());
                File f=new File(path);
                attachments.get(i).transferTo(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
