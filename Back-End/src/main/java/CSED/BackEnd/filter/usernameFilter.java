package CSED.BackEnd.filter;

import java.util.ArrayList;
import java.util.List;

public class usernameFilter implements Ifilter {
    private String username;
    public usernameFilter(String username){
        this.username=username;
    }
    @Override
    public List<Info> filter(List<Info> infos) {
        List<Info> filteredInfo=new ArrayList<>();
        for(Info info:infos){
            for(int i=0;i<info.getUserName().size();i++) {
                if(info.getUserName().get(i).equals(username)) {
                    filteredInfo.add(info);
                    break;
                }
            }
        }
        return filteredInfo ;
    }

}
