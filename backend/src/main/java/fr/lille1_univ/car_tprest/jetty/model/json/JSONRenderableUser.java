package fr.lille1_univ.car_tprest.jetty.model.json;

import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;

public class JSONRenderableUser extends JSONComplexObject implements JSONRenderableObject {
	
	public UpweeUser value;
	
	public JSONRenderableUser(UpweeUser value) {
		this.value = value;
	}
	//TOTOLAST LASTODO LASTTODO TODO ajouter l'id de l'utilisateur
	@Override
	public String renderJSON() {
		this.addProp("username", new JSONRenderableString(value.getUsername()));
		this.addProp("email", new JSONRenderableString(value.getEmail()));
		this.addProp("is_first_connection", new JSONRenderableBoolean(value.isFirstConnection()));
		return this.renderComplexJSON();
	}

	@Override
	public Object getValue() {
		return value;
	}

}
