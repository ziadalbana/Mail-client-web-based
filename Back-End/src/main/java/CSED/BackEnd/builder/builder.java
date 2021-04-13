package CSED.BackEnd.builder;

import java.util.ArrayList;
import java.util.List;

public class builder implements Ibuilder {
    private Email email;
    public builder(){
        this.email=new Email();
    }
    @Override
    public void buildUserName(ArrayList<String> username) {
        email.setUsername(username);
    }

    @Override
    public void buildSubject(String subject) {
     email.setSubject(subject);
    }

    @Override
    public void buildBody(String body) {
        email.setBody(body);
    }

    @Override
    public void buildType(String type) {
        email.setType(type);
    }

    @Override
    public void buildDate(String date) {
     email.setDate(date);
    }

    @Override
    public void buildImportance(int importance) {
        email.setImportance(importance);
    }

    @Override
    public void buildAttachment(List<String> attachemt) {
     email.setAttachment(attachemt);
    }
    @Override
    public void buildAttachmentPath(List<String> paths){email.setAttachmentPath(paths);}
    public Email getEmail(){
        return this.email;
    }
}
