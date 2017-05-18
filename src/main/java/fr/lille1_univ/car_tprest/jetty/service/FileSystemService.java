package fr.lille1_univ.car_tprest.jetty.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import fr.lille1_univ.car_tprest.jetty.model.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;
import fr.lille1_univ.car_tprest.jetty.model.UpweeFileList;
import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableFile;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableUser;
import fr.lille1_univ.car_tprest.jetty.utils.Constants;
import fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd.UsersTable;

@Component
public class FileSystemService extends UpweeService {

	public FileSystemService() {
		super("FileSystemService");
	}
	
	/***
	 * @deprecated
	 * @param userId the users id
	 * @return a list of files for the user
	 */
	@Deprecated
	public String getListForUser(int userId) {
		String[] parameters = {"User id : " + userId};
		l.ws(parameters);
		
		UpweeUser user = (UsersTable.getInstance()).get(userId);
		if(user == null)
			return new JSONRenderableMessage(UpweeMessage.UNKNOWN_USER).renderJSON();
		
		//Add if epty 
		user.getHomeDir().mkdirs();
		return new JSONRenderableFile(user.getHomeDir()).renderJSON();
	}

	public String getListFromPath(String path) {
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		
		UpweeFile f = new UpweeFile(path);
		if(!f.exists())
			return new JSONRenderableMessage(UpweeMessage.UNEXISTING_FILE).renderJSON();
		l.i("returning" + f.getName());
		return new JSONRenderableFile(f).renderJSON();
	}

	public InputStream getFile(String path) throws Exception {
		UpweeFile f = new UpweeFile(path);
		if(f.exists()) return new FileInputStream(f);
		else throw(new Exception("File not found"));
	}

}
