package fr.lille1_univ.car_tprest.jetty.model.json;

import java.util.HashMap;
import java.util.Map;

import fr.lille1_univ.car_tprest.jetty.utils.Logger;

/**
* A Java class that is used to render JSON Complex objects
*
*/

public class JSONComplexObject {
	private Map<String, JSONRenderableObject> props = new HashMap<String, JSONRenderableObject>();
	public Logger l = new Logger("JSONComplexObject");
	
	/**
	*
	* Adding a prop to the object
	*
	*/
	public void addProp(String key, JSONRenderableObject value) {
		this.props.put(key, value);
		l.i("Adding prop : " + key + " - " + value.getValue());
	}
	
	/**
	* A function that is used to render the object with all his props
	*/
	public String renderComplexJSON() {
		int c = 1;
		String r = "{";
		for (Map.Entry<String, JSONRenderableObject> entry : props.entrySet()) {
			r += "\"" + entry.getKey() + "\" : ";
			r += entry.getValue().renderJSON();
			if(c<props.size())
				r+=", ";
			c++;
		}
		r += "}";
		return r;
	}	
}
