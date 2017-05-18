package fr.lille1_univ.car_tprest.jetty.model;

import org.json.JSONObject;

public class UpweeCredentials {
	private String login;
	private String password;
	
	public UpweeCredentials() {}
	
	public UpweeCredentials(String login, String password) {
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
	
	public static UpweeCredentials getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		return new UpweeCredentials(obj.getString("login"), obj.getString("password"));		
	}
}
