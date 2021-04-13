package CSED.BackEnd.filter;

import java.util.ArrayList;
import java.util.List;

public class subjectFilter implements Ifilter {
    private String subject;
    public subjectFilter(String subject){
        this.subject=subject;
    }
    @Override
    public List<Info> filter(List<Info> infos) {
        List<Info> filteredInfo=new ArrayList<>();
        for(Info info:infos){
            if(info.getSubject().contains(subject)){
                filteredInfo.add(info);
            }
        }
        return filteredInfo ;
    }
}
