package fr.lille1_univ.car_tprest.jetty.model.json;

import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;

public class JSONRenderableMessage extends JSONComplexObject implements JSONRenderableObject {

	private UpweeMessage value;
	
	public JSONRenderableMessage(UpweeMessage value) {
		this.value = value;
	}
	
	@Override
	public String renderJSON() {
		this.addProp("code", new JSONRenderableInt(value.getCode()));
		this.addProp("message", new JSONRenderableString(value.getMessage()));
		return this.renderComplexJSON();
	}

	@Override
	public Object getValue() {
		return value;
	}
}
