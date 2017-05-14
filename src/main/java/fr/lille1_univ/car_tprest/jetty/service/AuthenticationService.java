package fr.lille1_univ.car_tprest.jetty.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import fr.lille1_univ.car_tprest.jetty.model.Credentials;
import fr.lille1_univ.car_tprest.jetty.model.Message;
import fr.lille1_univ.car_tprest.jetty.model.User;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableUser;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

@Component
public class AuthenticationService {
	
	public ArrayList<User> availableUsers = new ArrayList<User>();
	public Logger l = new Logger("AuthenticationService");
	
	public AuthenticationService() {
		availableUsers.add(new User("freshprince", "noctisluciscaelum@insomnia.com", "azerty", false));
		availableUsers.add(new User("freshprincess", "lunafreyanoxfleuret@tenebrae.com", "azerty", true));
	}
	
	public String authenticate(Credentials c) {
		l.i("Starting autnehtication");
		//Test is login is one of the users in the database (email or username)
		for (User user : availableUsers) {
			if(user.getEmail().equals(c.getLogin()) || user.getUsername().equals(c.getLogin())) {
				//Test if the password is correct too. In this case, we send back the user object
				if(user.getPassword().equals(c.getPassword())) {
					return new JSONRenderableUser(user).renderJSON();
				} else {
					return new JSONRenderableMessage(Message.INVALID_PASSWORD).renderJSON();
				}
			} 
		}
		//If we get here, then we did not find the user in the 'database';
		return new JSONRenderableMessage(Message.INVALID_LOGIN).renderJSON();	
	}

}
