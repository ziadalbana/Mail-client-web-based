package CSED.BackEnd.filter;

import java.util.ArrayList;
import java.util.List;

public class andFilter implements Ifilter {
    private Ifilter firstFilter;
    private Ifilter secondFilter;
    public andFilter(Ifilter firstFilter, Ifilter secondFilter){
        this.firstFilter=firstFilter;
        this.secondFilter=secondFilter;
    }
    @Override
    public List<Info> filter(List<Info> infos) {
        List<Info> filteredInfo=firstFilter.filter(infos);
        return secondFilter.filter(filteredInfo) ;
    }
}
