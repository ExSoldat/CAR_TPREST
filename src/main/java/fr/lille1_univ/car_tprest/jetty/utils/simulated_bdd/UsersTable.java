package fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;

public class UsersTable extends ArrayList<UpweeUser>{
	
	static UsersTable instance = null;
	BufferedReader br;
	FileReader fr;
	
	//TODO correct so it does not create illimited files
	private UsersTable() {
		try {
			String s;
			fr = new FileReader("user_database.txt");
			br = new BufferedReader(fr);

			while ((s = br.readLine()) != null && !s.equals("")) {
				String[] stru = s.split(",");
			    this.add(new UpweeUser(stru[0], stru[1], stru[2], false));
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	public boolean addToDatabase(UpweeUser u) {
		//save file
		if(saveInFile(u))
			return super.add(u);
		else
			return false;
	}
	
	public boolean saveInFile(UpweeUser u) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("user_database.txt", true), "utf-8"));
			writer.write(u.getUsername() + "," + u.getEmail() + "," + u.getPassword() + "\n\r");
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
