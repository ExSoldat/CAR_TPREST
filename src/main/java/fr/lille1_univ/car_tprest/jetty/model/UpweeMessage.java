package fr.lille1_univ.car_tprest.jetty.model;

public class UpweeMessage {
	public int code;
	public String message;
	
	/*100-199, Informations*/
	/*200-299, OK*/
	public static final UpweeMessage ACCESS_GRANTED = new UpweeMessage(200, "Access granted");
	public static final UpweeMessage EMPTY_HOME = new UpweeMessage(201, "Empty home dir");
	/*300-399, Authorization*/
	/*400-499, Not found*/
	public static final UpweeMessage UNKNOWN_USER = new UpweeMessage(400, "User not found");
	public static final UpweeMessage UNEXISTING_FILE = new UpweeMessage(401, "File not found");
	/*500-599, Invalid Parameters*/
	public static final UpweeMessage INVALID_LOGIN = new UpweeMessage(500, "Invalid login");
	public static final UpweeMessage INVALID_PASSWORD = new UpweeMessage(501, "Invalid password");
	/*600-699, ?*/
	/*600-699, ?*/
	/*700-799, ?*/
	/*800-899, ?*/
	/*900-998, ?*/
	/*999, Unknown*/
	public static final UpweeMessage UNKNOWN_ERROR = new UpweeMessage(999, "Unknown error");
	
	public UpweeMessage(int code, String message) {
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
