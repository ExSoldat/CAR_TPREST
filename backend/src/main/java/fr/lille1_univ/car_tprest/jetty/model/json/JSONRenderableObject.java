package fr.lille1_univ.car_tprest.jetty.model.json;

	/**
	* An interface representing renderable objects
	*/
public interface JSONRenderableObject {
	public String renderJSON();
	//Unused
	public Object getValue();
}
