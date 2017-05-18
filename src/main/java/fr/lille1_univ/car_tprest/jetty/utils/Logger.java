package fr.lille1_univ.car_tprest.jetty.utils;

/***
 * A class that helps developpers to log things.
 * @author Mathieu
 *
 */
public class Logger {
	private String tag = "Undefined";
	public Logger(String classTag) {
		this.tag = classTag;
	}
	
	/**
	 * A function that sends an error message
	 * @param errorMessage the message
	 */
	public void e(String errorMessage) {
		_w("ERROR", errorMessage);
	}
	
	/**
	 * A function that sends an information message
	 * @param infoMessage the message
	 */
	public void i(String infoMessage) {
		_w("INFO", infoMessage);
	}
	
	/**
	 * A function that sends a warning message
	 * @param warningMessage the message
	 */
	public void w(String warningMessage) {
		_w("WARNING", warningMessage);
	}
	
	/**
	 * A function used to log a weservice call
	 * @param parameters the parameters we want to log
	 */
	public void ws(String[] parameters) {
		String callParameters = "";
		int i = 0;
		do {
			callParameters+=parameters[i] + "; ";
			i++;
		} while (i<parameters.length);
		_w("CALL", "with parameters : " + callParameters);
	}
	
	/***
	 * A function that outputs a message and its type
	 * @param type the type of the message
	 * @param message the message
	 */
	private void _w(String type, String message) {
		System.out.println(tag + '-' + type.toUpperCase() + " : " + message);
	}
}
