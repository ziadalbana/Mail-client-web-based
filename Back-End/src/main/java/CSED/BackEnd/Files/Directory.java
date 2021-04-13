package CSED.BackEnd.Files;

import CSED.BackEnd.Proxy.User;
import CSED.BackEnd.filter.Info;


import java.util.List;

public class Directory implements FileCompose{

    FilesUtilities fileOPS = new FilesUtilities();
    private List<FileCompose> children ;


    @Override
    public boolean check(String userName) {
        return false;
    }

    @Override
    public void createNewUser(User user) {
        fileOPS.makdir(fileOPS.makPath("users",user.getUserName(),"AllMails"));
        fileOPS.write(fileOPS.makPath("users",user.getUserName(),"index.txt"),user.getUserData(),false);
    }

    @Override
    public boolean logIn(String userName, String password) throws Exception {
        return false;
    }

    @Override
    public void deleteUser(String userName ,boolean completely) throws Exception {
        if(completely) fileOPS.delete(fileOPS.makPath("users",userName));
    }

    @Override
    public List<Info> load(String userName) {
        return null;
    }
}
