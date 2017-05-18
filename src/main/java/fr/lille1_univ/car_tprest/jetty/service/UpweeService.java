package fr.lille1_univ.car_tprest.jetty.service;

import fr.lille1_univ.car_tprest.jetty.utils.Logger;

public abstract class UpweeService {
	protected static String TAG;
	protected Logger l;
	protected String[] parameters;
	
	public UpweeService(String tag) {
		this.TAG = tag;
		l = new Logger(TAG);
	}
}
