package Entities;

import java.util.List;
import java.util.Map;

public class User {

	private String email;
	private String token;
	private String password;
	private Map<String, String> cribs;
	

	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setPassword(String pass) {
		password = pass;
	}
	
	public  Map<String, String> getCribs() {
		return cribs;
	}
	
	public void setCribs( Map<String, String> cribs) {
		this.cribs = cribs;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
