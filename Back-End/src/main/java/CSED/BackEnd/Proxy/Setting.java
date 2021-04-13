package CSED.BackEnd.Proxy;

public class Setting {
	
	private User us ;
	
	public Setting(User u) {
		this.us = u ;
	}
	
   public User setUsername(String user) {
	   this.us.setUserName(user);
	   return us;
   }
   
   public User setpassword(String pass) {
	   this.us.setPassword(pass);
	   return us;
   }
   
   public User setbirthDay(String birth) {
	   this.us.setGender(birth);
	   return us;
   }
   
   public User setphone(String p) {
	   this.us.setPhone(p);
	   return us;
   }
   
   public User setAge(String age) {
	   this.us.setAge(age);
	   return us;
   }
   


}
