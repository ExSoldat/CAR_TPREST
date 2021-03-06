package fr.lille1_univ.car_tprest.jetty.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.UpweeRegisteringCredentials;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableUser;
import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;
import fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd.UsersTable;

/**
	* A class that is used to answer to authentication requests
	*/
@Component
public class AuthenticationService extends UpweeService {
	
	//Getting the mocked database
	public UsersTable availableUsers = UsersTable.getInstance();
	
	public AuthenticationService() {
		super("AuthenticationService");
	}
	
	/**
	* A function that is used to authenticate a user if he gives correct credentials
	*/
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

	/**
	* A function that is used to register a new user
	*/
	public String register(UpweeRegisteringCredentials c) {
		String[] parameters = {"Login : " + c.getLogin(), "Email : " + c.getEmail(),  "Password : " + c.getPassword()};
		l.ws(parameters);
		UpweeUser newUser = new UpweeUser(c.getLogin(), c.getEmail(), c.getPassword(), true);
		//We create his homedir and add him to the mocked database
		if(availableUsers.addToDatabase(newUser)) {
			newUser.getHomeDir().mkdirs();
			return new JSONRenderableMessage(UpweeMessage.ACCOUNT_CREATED).renderJSON();
		} else {
			return new JSONRenderableMessage(UpweeMessage.UNABLE_ACCOUNT_CREATION).renderJSON();
		}
	}

}
