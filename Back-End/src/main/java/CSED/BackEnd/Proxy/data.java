package CSED.BackEnd.Proxy;

import CSED.BackEnd.Files.FileCompose;
import CSED.BackEnd.Files.file;
import CSED.BackEnd.filter.Info;

import java.util.ArrayList;
import java.util.List;

public class data implements IProxy {
	FileCompose user;
	@Override
	public boolean signUp(User u) {
		user = new file();
		try {
			user.createNewUser(u);
			return true ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	}

	@Override
	public Boolean signIn(String username, String password) {
		Singleton userMails = Singleton.getInstance();
		user = new file();
		List<Info> list =  user.load(username);
		if(list==null)
			list = new ArrayList<>();
		userMails.setInfos(list);
		return true ;
	}

	@Override
	public void updateSetting(User user) {

	}

}
