package CSED.BackEnd.builder;

import java.util.ArrayList;
import java.util.List;

public class Director {
	
    private Ibuilder ibuilder;
    public Director(Ibuilder ibuilder){
        this.ibuilder=ibuilder;
    }

    public Email getEmail(){
      return  this.ibuilder.getEmail();
    }

    public void compose(ArrayList<String> username, String subject, String body, int importance, String type, List<String> attachment,List<String> attachmentPaths, String date){
        this.ibuilder.buildUserName(username);
        this.ibuilder.buildSubject(subject);
        this.ibuilder.buildBody(body);
        this.ibuilder.buildImportance(importance);
        this.ibuilder.buildType(type);
        this.ibuilder.buildDate(date);
        this.ibuilder.buildAttachment(attachment);
        this.ibuilder.buildAttachmentPath(attachmentPaths);
    }
}
