package fr.lille1_univ.car_tprest.jetty.model;

import org.json.JSONObject;

import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

	/**
	* A class that is used to reresent the credentials used to register
	*/
public class UpweeRegisteringCredentials extends UpweeCredentials {
	private String email;
	
	UpweeRegisteringCredentials(String username, String email, String password) {
		super(username, password);
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	* A function to get the credentials from json body
	*/
	public static UpweeRegisteringCredentials getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		Logger l = new Logger("UpweeGetFromJSONString");
		l.i(json);
		return new UpweeRegisteringCredentials(obj.getString("login"), obj.getString("email"), obj.getString("password"));		
	}
}
