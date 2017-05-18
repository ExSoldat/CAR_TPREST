package fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd;

import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;

public class UsersTable extends ArrayList<UpweeUser>{
	
	static UsersTable instance = null;
	
	private UsersTable() {
		this.add(new UpweeUser("freshprince", "noctisluciscaelum@insomnia.com", "azerty", false));
		this.add(new UpweeUser("freshprincess", "lunafreyanoxfleuret@tenebrae.com", "azerty", true));
	}
	
	public static UsersTable getInstance() {
		if(instance == null) 
			instance = new UsersTable();
		return instance;
	}
	
	@Override
	public UpweeUser get(int index) {
		return super.get(index-1);
	}
}
