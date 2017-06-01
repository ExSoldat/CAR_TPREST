package fr.lille1_univ.car_tprest.jetty.model.json;

	/**
	* A class used to render longs
	*/
public class JSONRenderableLong implements JSONRenderableObject {
	private long value;
	
	public JSONRenderableLong(long value) {
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
