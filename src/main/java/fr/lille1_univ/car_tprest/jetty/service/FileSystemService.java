package fr.lille1_univ.car_tprest.jetty.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		UpweeFile f = new UpweeFile(path);
		if(f.exists()) return new FileInputStream(f);
		else throw(new Exception("File not found"));
	}

	public String deleteFromPath(String path) {
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		UpweeFile f = new UpweeFile(path);
		
		if(f.exists()) {
			if(f.delete()) {
				return new JSONRenderableMessage(UpweeMessage.SUCCESSFULLY_DELETED).renderJSON();
			} else {
				return new JSONRenderableMessage(UpweeMessage.UNDELETABLE_DIRECTORY).renderJSON();
			}
		} else {
			return new JSONRenderableMessage(UpweeMessage.UNEXISTING_FILE).renderJSON();
		}	
	}
	
	public String uploadSingleFile(MultipartFile file, String dirPath) {
		if(!file.isEmpty()) {
			try {
				saveFiles(Arrays.asList(file), dirPath);
				//If no exception occurred, then the save was successful
				return new JSONRenderableMessage(UpweeMessage.SUCCESSFULLY_UPLOADED).renderJSON();
			} catch (IOException e) {
				e.printStackTrace();
				return new JSONRenderableMessage(UpweeMessage.IMBOSSIBLE_UPLOAD).renderJSON();
			}
		} else {
			return new JSONRenderableMessage(UpweeMessage.INVALID_FILE).renderJSON();
		}
	}
	
	//https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
	private void saveFiles(List<MultipartFile> files, String pathToDir) throws IOException {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				l.i("File was empty");
				continue; //next pls
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(Constants.WORK_DIRECTORY + "/" + pathToDir + file.getOriginalFilename());
			l.i("Saving file : " + Constants.WORK_DIRECTORY + "/" + pathToDir + file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}
}
