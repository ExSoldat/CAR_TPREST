package fr.lille1_univ.car_tprest.jetty.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;
import fr.lille1_univ.car_tprest.jetty.model.UpweeFileList;
import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableFile;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableUser;
import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeMoveParams;
import fr.lille1_univ.car_tprest.jetty.utils.Constants;
import fr.lille1_univ.car_tprest.jetty.utils.simulated_bdd.UsersTable;

@Component
	/**
	* A function that is used to answer to calls concerning the files
	*/
public class FileSystemService extends UpweeService {

	public FileSystemService() {
		super("FileSystemService");
	}
	
	/***
	 * A deprecated function that was used to get the files list for a given user
	 * Unused because REST is working with resources urls
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

	/**
	* A function that is used to send a json corresponding to a list of files from a given path
	*/
	public String getListFromPath(String path) {
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		
		UpweeFile f = new UpweeFile(path);
		if(!f.exists())
			return new JSONRenderableMessage(UpweeMessage.UNEXISTING_FILE).renderJSON();
		l.i("returning" + f.getName());
		return new JSONRenderableFile(f).renderJSON();
	}

	/**
	* A function that is used to get a specific file and the corresponding inputstream
	*/
	public InputStream getFile(String path) throws Exception {
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		UpweeFile f = new UpweeFile(path);
		if(f.exists()) return new FileInputStream(f);
		else throw(new Exception("File not found"));
	}

	/**
	* A function that is used to delete a file knowing its path
	*/
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
	
	/**
	* A function that is used to upload a single file n a specifc path
	*/
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
	
	/**
	* A function that is used to save files in a list
	* inspired from https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
	*/
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

	/**
	* A function that is used to create a folder in a specific path
	*/
	public String createFolder(String path) {
		String[]parameters = {"path :" + path};
		l.ws(parameters);
		UpweeFile f = new UpweeFile(path);
		
		if(f.mkdirs()) {
			return new JSONRenderableMessage(UpweeMessage.FOLDER_CREATED).renderJSON();
		} else {
			return new JSONRenderableMessage(UpweeMessage.IMPOSSIBLE_CREATION).renderJSON();
		}
	}

	/**
	* A function that is used to move a file inside of another path
	*/
	public String move(UpweeMoveParams params, String path) {
		String[]parameters = {"path :" + path, "file : " + params.getFile(), "target : " + params.getTarget()};
		l.ws(parameters);
		UpweeFile f = new UpweeFile(path+'/'+params.getFile());
		UpweeFile t;
		//We check if the file is sended into another file in the same tree level or if it goes in a file that is closer to the root
		if(params.getTarget() == null || params.getTarget().equals(""))
			t = new UpweeFile(path+'/'+f.getName());
		else
			t = new UpweeFile(path+'/'+params.getTarget()+params.getFile());
		
		try {
			Path newPath = Files.move(f.toPath(), t.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return new JSONRenderableMessage(UpweeMessage.MOVE_SUCCESS).renderJSON();
		} catch (IOException e) {
			return new JSONRenderableMessage(UpweeMessage.MOVE_FAILURE).renderJSON();
		}
	}
}
