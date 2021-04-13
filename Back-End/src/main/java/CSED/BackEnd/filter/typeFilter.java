package CSED.BackEnd.filter;

import java.util.ArrayList;
import java.util.List;

public class typeFilter implements Ifilter {
    private String type;
    public typeFilter (String type){
        this.type=type;
    }
    @Override
    public List<Info> filter(List<Info> infos) {
        List<Info> filteredInfo=new ArrayList<>();
        for(Info info:infos){
            if(info.getType().equals(type)){
                filteredInfo.add(info);
            }
        }
        return filteredInfo ;
    }
}
