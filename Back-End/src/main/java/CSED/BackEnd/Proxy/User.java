package CSED.BackEnd.Proxy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class User {
	@JsonProperty private String username ;
	@JsonProperty private String password;
	@JsonProperty private String age;
	@JsonProperty private String phonenumber;
	@JsonProperty private String gender;

	
	public void setUserName(String user) {
		this.username = user;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public void setPhone(String phone) {
		this.phonenumber = phone;
	}
	
	public void setGender(String birth) {
		this.gender = birth;
	}
	
	public String getUserName() {
		return this.username ;
	}
	
	public String getPassword() {
		return this.password ;
	}
	
	public String getAge() {
		return this.age ;
	}
	
	public String getPhone() {
		return this.phonenumber ;
	}
	
	public String getGender() {
		return this.gender;
	}

	public List<String> getUserData(){
		List<String> list = new ArrayList<>(5);
		list.add(this.username);
		list.add(this.age);
		list.add(this.phonenumber);
		list.add(this.gender);

		return list;
	}


}
