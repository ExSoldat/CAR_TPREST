package fr.lille1_univ.car_tprest.jetty.model.parameters;

import org.json.JSONObject;

import fr.lille1_univ.car_tprest.jetty.utils.Logger;

public class UpweeMoveParams {
	private String file;
	private String target;
	
	public UpweeMoveParams(String file, String target) {
		this.file = file;
		this.target = target;
	}
	
	public String getFile() {
		return file;
	}
	
	public String getTarget() {
		return (target == null || target.equals("")) ? target : target+"/";
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public static UpweeMoveParams getFromJsonString(String json) {
		JSONObject obj = new JSONObject(json);
		Logger l = new Logger("UpweeMoveParams GetFromJSONString");
		l.i(json);
		return new UpweeMoveParams(obj.getString("file"), obj.getString("target"));		
	}
}
