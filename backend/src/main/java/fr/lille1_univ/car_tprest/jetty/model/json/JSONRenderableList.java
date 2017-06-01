package fr.lille1_univ.car_tprest.jetty.model.json;

import java.util.ArrayList;

	/**
	* A class used to render objects lists
	*/
public class JSONRenderableList extends JSONComplexObject implements JSONRenderableObject {
	
	ArrayList<JSONRenderableObject> values;

	public JSONRenderableList(JSONRenderableObject[] list) {
		values = new ArrayList<JSONRenderableObject>();
		for (int i = 0; i<list.length; i++) {
			values.add(list[i]);
		}
	}
	
	@Override
	public String renderJSON() {
		String r = "[";
		for (int i = 0; i<values.size(); i++) {
			r += values.get(i).renderJSON();
			if(i!=values.size()-1) {
				r+=", ";
			}
		}
		r+="]";
		return r;
	}

	@Override
	public Object getValue() {
		return values;
	}

}