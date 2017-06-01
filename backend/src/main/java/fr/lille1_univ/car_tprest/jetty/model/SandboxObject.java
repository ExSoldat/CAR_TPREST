package fr.lille1_univ.car_tprest.jetty.model;

import fr.lille1_univ.car_tprest.jetty.model.json.JSONComplexObject;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableString;

public class SandboxObject extends JSONComplexObject {
	private JSONRenderableString st1;
	private JSONRenderableString st2;
	private JSONRenderableString st3;
	private JSONRenderableString result;

	public SandboxObject(String name, String string, String string2) {
		this.st1 = new JSONRenderableString(name);
		this.st2 = new JSONRenderableString(string);
		this.st3 = new JSONRenderableString(string2);
		this.result = new JSONRenderableString(st1.getValue().substring(0, 1) + st2.getValue().substring(0, 1) + st3.getValue().substring(0, 1));
		this.addProp("st1", st1);
		this.addProp("st2", st2);
		this.addProp("st3", st3);
		this.addProp("result", result);
		this.addProp("st2", new JSONRenderableString("wololo"));
	}
	
	public String getSt1() {
		return st1.getValue();
	}

	public String getSt2() {
		return st2.getValue();
	}

	public String getSt3() {
		return st3.getValue();
	}

	public String getResult() {
		return result.getValue();
	}
}
