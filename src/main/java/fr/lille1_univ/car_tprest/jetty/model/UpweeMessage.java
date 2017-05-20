package fr.lille1_univ.car_tprest.jetty.model;

public class UpweeMessage {
	public int code;
	public String message;
	
	/*100-199, Informations*/
	/*200-299, OK*/
	public static final UpweeMessage ACCESS_GRANTED = new UpweeMessage(200, "Access granted");
	public static final UpweeMessage EMPTY_HOME = new UpweeMessage(201, "Empty home dir");
	public static final UpweeMessage SUCCESSFULLY_DELETED = new UpweeMessage(202, "The file was successfully deleted");
	public static final UpweeMessage SUCCESSFULLY_UPLOADED = new UpweeMessage(203, "The file was successfully uploaded");
	/*300-399, Authorization*/
	/*400-499, Error */
	public static final UpweeMessage UNKNOWN_USER = new UpweeMessage(400, "User not found");
	public static final UpweeMessage UNEXISTING_FILE = new UpweeMessage(401, "File not found");
	public static final UpweeMessage UNDELETABLE_DIRECTORY = new UpweeMessage(402, "Unable to delete the directory, please check that it is empty");
	public static final UpweeMessage IMBOSSIBLE_UPLOAD = new UpweeMessage(403, "Unable to upload a file in this directory. Please try again.");
	public static final UpweeMessage INVALID_FILE = new UpweeMessage(404, "Invalid file. Please try with another");
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
