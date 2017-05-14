package fr.lille1_univ.car_tprest.jetty.model;

import org.json.JSONObject;

public class Credentials {
	private String login;
	private String password;
	
	public Credentials() {}
	
	public Credentials(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public static Credentials getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		return new Credentials(obj.getString("login"), obj.getString("password"));		
	}
}
