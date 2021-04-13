package CSED.BackEnd.Proxy;

import CSED.BackEnd.filter.Info;

import java.util.List;

public interface IProxy {
	
	public boolean signUp(User u) ;

	/**
	 * check if the userName and password are correct
	 * load the Mails if they are correct
	 * @param username
	 * @param password
	 * @return true if the userName and password are correct
	 * and false otherwise
	 */
	public Boolean signIn(String username ,String password);

	public void updateSetting(User user);

}
