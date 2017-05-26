package fr.lille1_univ.car_tprest.jetty.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import fr.lille1_univ.car_tprest.jetty.model.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.UpweeRegisteringCredentials;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableUser;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;
import fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd.UsersTable;

@Component
public class AuthenticationService extends UpweeService {
	
	public ArrayList<UpweeUser> availableUsers = UsersTable.getInstance();
	
	public AuthenticationService() {
		super("AuthenticationService");
	}
	
	
	public String authenticate(UpweeCredentials c) {
		//Test is login is one of the users in the database (email or username)
		String[] parameters = {"Login : " + c.getLogin(), "Password : " + c.getPassword()};
		l.ws(parameters);
		for (UpweeUser user : availableUsers) {
			if(user.getEmail().equals(c.getLogin()) || user.getUsername().equals(c.getLogin())) {
				//Test if the password is correct too. In this case, we send back the user object
				if(user.getPassword().equals(c.getPassword())) {
					return new JSONRenderableUser(user).renderJSON();
				} else {
					return new JSONRenderableMessage(UpweeMessage.INVALID_PASSWORD).renderJSON();
				}
			} 
		}
		//If we get here, then we did not find the user in the 'database';
		return new JSONRenderableMessage(UpweeMessage.INVALID_LOGIN).renderJSON();	
	}


	public String register(UpweeRegisteringCredentials c) {
		String[] parameters = {"Login : " + c.getLogin(), "Email : " + c.getEmail(),  "Password : " + c.getPassword()};
		l.ws(parameters);
		UpweeUser newUser = new UpweeUser(c.getLogin(), c.getEmail(), c.getPassword(), true);
		if(availableUsers.add(newUser)) {
			newUser.getHomeDir().mkdirs();
			return new JSONRenderableMessage(UpweeMessage.ACCOUNT_CREATED).renderJSON();
		} else {
			return new JSONRenderableMessage(UpweeMessage.UNABLE_ACCOUNT_CREATION).renderJSON();
		}
	}

}
