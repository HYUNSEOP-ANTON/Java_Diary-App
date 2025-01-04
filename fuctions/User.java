package fuctions;

import java.io.Serializable;
import java.io.*;

public class User implements Serializable{

	private String name;
	private String userName;
	private String userPWD;
	
	private Note myPage;
	
	public User(String name, String userN, String userP) {
		this.name = name;
		this.userName = userN;
		this.userPWD = userP;
	}
	
	public String returnName() {
		return name;
	}
	
	public String returnID() {
		return userName;
	}
	
	public String returnPWD() {
		return userPWD;
	}
	
	public void showUserLog() {
		System.out.println(this.name +"/"+ this.userName +"/"+ this.userPWD);
	}
}