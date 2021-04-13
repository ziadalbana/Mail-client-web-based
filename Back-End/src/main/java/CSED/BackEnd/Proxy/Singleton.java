package CSED.BackEnd.Proxy;

import CSED.BackEnd.filter.Info;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Singleton {
    private static Singleton instance = null;
    private List<Info> infos = new LinkedList<>();
    private Singleton(){
    }

    public static Singleton getInstance(){
        if(instance==null){
            instance = new Singleton();
        }
        return instance;
    }

    public  List<Info> getInfos(){
        return infos;
    }

    public void setInfos(List<Info> list){
        this.infos = new LinkedList<>(list);
        if(this.getInfos() == null)
            this.infos = new LinkedList<>();
    }

}
