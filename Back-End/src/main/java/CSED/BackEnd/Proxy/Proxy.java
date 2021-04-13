package CSED.BackEnd.Proxy;

import CSED.BackEnd.Files.FileCompose;

import CSED.BackEnd.Files.file;

public class Proxy implements IProxy {
	FileCompose user;
	boolean hasAccess = false;

	@Override
	public boolean signUp(User u)  {
		if (u.getUserName().equals("") || u.getPassword().equals("")) { return false; }
		user = new file();
		boolean valid = false;
		data ops = new data();
		try {
			 valid = user.check(u.getUserName());
			if (valid) {
				return false;
			}else{
				return ops.signUp(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean signIn(String username, String password) {
		if (username.equals("") || password.equals("")) return false ;
		user= new file();
		data data = new data();
		try {
			hasAccess = user.logIn(username, password);
			if (hasAccess) {
				return data.signIn(username,password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateSetting(User user) {
		this.user = new file();
		try {
			this.user.deleteUser(user.getUserName(),false);
			data ops = new data();
			ops.signUp(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
