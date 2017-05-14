package fr.lille1_univ.car_tprest.jetty.model;

public class User {
	private String username, password, email;
	private boolean firstConnection;

	public User(String username, String email, String password, boolean isFirstConnection) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstConnection = true;
	}
	
	public User() {
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
}
