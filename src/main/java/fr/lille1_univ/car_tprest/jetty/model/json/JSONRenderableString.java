package fr.lille1_univ.car_tprest.jetty.model.json;

public class JSONRenderableString implements JSONRenderableObject {

	String value;
	
	public JSONRenderableString(String value) {
		this.value = value;
	}
	
	@Override
	public String renderJSON() {
		return "\"" + this.value + "\"";
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
