package CSED.BackEnd.builder;

import java.util.ArrayList;
import java.util.List;

public interface Ibuilder {
    public void buildUserName(ArrayList<String> username);
    public void buildSubject(String subject);
    public void buildBody(String body);
    public void buildType(String type);
    public void buildDate(String date);
    public void buildImportance(int importance);
    public void buildAttachment(List<String> attachemt);
    public void buildAttachmentPath(List<String> paths);
    public Email getEmail();
}
