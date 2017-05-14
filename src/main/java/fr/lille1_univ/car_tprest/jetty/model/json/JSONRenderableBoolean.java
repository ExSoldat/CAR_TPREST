package fr.lille1_univ.car_tprest.jetty.model.json;

public class JSONRenderableBoolean implements JSONRenderableObject {
	private boolean value;
	
	public JSONRenderableBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public String renderJSON() {
		return ""+value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
