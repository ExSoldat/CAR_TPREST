package fr.lille1_univ.car_tprest.jetty.model.json;

public class JSONRenderableInt implements JSONRenderableObject {

	private int value;
	
	public JSONRenderableInt(int value) {
		this.value=value;
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
