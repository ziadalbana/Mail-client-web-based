package CSED.BackEnd.Files;

import CSED.BackEnd.Proxy.User;
import CSED.BackEnd.filter.Info;

import java.util.List;

public interface FileCompose {
    String keyPassword = "123456";

    public boolean check(String userName) throws Exception;
    public void createNewUser(User user) throws Exception;
    public boolean logIn(String userName , String password) throws Exception;
    public void deleteUser(String userName ,boolean completely)throws Exception;
    public List<Info> load(String userName);

}
