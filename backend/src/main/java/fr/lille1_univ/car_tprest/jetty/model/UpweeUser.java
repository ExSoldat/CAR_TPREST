package fr.lille1_univ.car_tprest.jetty.model;

import java.io.File;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.utils.Constants;
import fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd.UsersTable;
	/**
	* A class that represents the app users
	*/
public class UpweeUser {
	private String username, password, email;
	public UpweeFile homeDir;
	private boolean firstConnection;

	public UpweeUser(String username, String email, String password, boolean isFirstConnection) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstConnection = true;
		this.homeDir = new UpweeFile(username.toLowerCase().replaceAll("\\s+",""));
	}
	
	public UpweeUser() {
	}

	public String getUsername() {
		return username;
	}

	public void setFirstConnection(boolean firstConnection) {
		this.firstConnection = firstConnection;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isFirstConnection() {
		return this.firstConnection;
	}
	
	public void setHomeDir(UpweeFile homedir) {
		this.homeDir = homedir;
	}
	
	public UpweeFile getHomeDir() {
		return this.homeDir;
	}
}
