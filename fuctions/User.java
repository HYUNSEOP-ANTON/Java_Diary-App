package fuctions;

import java.io.Serializable;
import java.util.*;
import java.io.*;
import java.time.*;

public class User implements Serializable{

	private String name;
	private String userName;
	private String userPWD;
	
	private Vector<Note> notes;
	private LocalDate recentLogin;
	
	public User(String name, String userN, String userP) {
		this.name = name;
		this.userName = userN;
		this.userPWD = userP;
		
		this.recentLogin = LocalDate.now();
		
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
	
	public LocalDate returnDate(int index) {
		
		return notes.elementAt(index).returnWhen();
	}
	public LocalDate returnRecent() {
		return recentLogin;
	}
	
	public Vector<Note> returnNotes(){
		return notes;
	}
	public boolean checkToday() {
		if(notes != null) {
			if(notes.get(notes.size()-1).returnWhen() == recentLogin)
				return true;
		}
		return false;
	}
	
	public void showUserLog() {
		System.out.println(this.name +"/"+ this.userName +"/"+ this.userPWD);
	}
}