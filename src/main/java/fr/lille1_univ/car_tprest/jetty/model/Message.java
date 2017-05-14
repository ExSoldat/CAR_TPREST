package fr.lille1_univ.car_tprest.jetty.model;

public class Message {
	public int code;
	public String message;
	
	/*100-199, Informations*/
	/*200-299, OK*/
	public static final Message ACCESS_GRANTED = new Message(200, "Access granted");
	/*300-399, ?*/
	/*400-499, Authorization*/
	/*500-599, Invalid Parameters*/
	public static final Message INVALID_LOGIN = new Message(500, "Invalid login");
	public static final Message INVALID_PASSWORD = new Message(501, "Invalid password");
	/*600-699, ?*/
	/*600-699, ?*/
	/*700-799, ?*/
	/*800-899, ?*/
	/*900-998, ?*/
	/*999, Unknown*/
	public static final Message UNKNOWN_ERROR = new Message(999, "Unknown error");
	
	public Message(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
