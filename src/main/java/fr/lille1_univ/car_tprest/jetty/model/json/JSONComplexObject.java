package fr.lille1_univ.car_tprest.jetty.model.json;

import java.util.HashMap;
import java.util.Map;

import fr.lille1_univ.car_tprest.jetty.utils.Logger;

public class JSONComplexObject {
	//Utilisation d'Arrays : créer des ojest etendant list et dans la valeur de la map ajouter une chaine de caractèr
	private Map<String, JSONRenderableObject> props = new HashMap<String, JSONRenderableObject>();
	public Logger l = new Logger("JSONComplexObject");
	
	public void addProp(String key, JSONRenderableObject value) {
		this.props.put(key, value);
		l.i("Adding prop : " + key + " - " + value.getValue());
	}
	
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
