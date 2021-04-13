package CSED.BackEnd.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Info {
    private ArrayList<String> userName;
    private String subject;
    private String date;
    private int importance;
    private String type;
    //constructor
    public Info(ArrayList<String> userName,String subject, String date,String type,int importance) {
        this.userName=userName;
        this.subject=subject;
        this.date=date;
        this.type=type;
        this.importance=importance;

    }
    public ArrayList<String> getUserName() {
        return userName;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImportance() {
        return importance;
    }

    public List<String> getAttributes(){
        return new LinkedList<>(Arrays.asList(this.subject,this.date,this.type,String.valueOf(this.getImportance())));
    }

}
