package fr.lille1_univ.car_tprest.jetty.model;

import org.json.JSONObject;

import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

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
	
	public static UpweeRegisteringCredentials getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		Logger l = new Logger("UpweeGetFromJSONString");
		l.i(json);
		return new UpweeRegisteringCredentials(obj.getString("login"), obj.getString("email"), obj.getString("password"));		
	}
}
