package fr.lille1_univ.car_tprest.jetty.model.parameters;

import org.json.JSONObject;

import fr.lille1_univ.car_tprest.jetty.utils.Logger;
	/**
	* A class that represents users credentials
	*/
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
	
	/**
	* A function that is used to get the credentials from a post request body
	*/
	public static UpweeCredentials getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		Logger l = new Logger("UpweeGetFromJSONString");
		l.i(json);
		return new UpweeCredentials(obj.getString("login"), obj.getString("password"));		
	}
}
