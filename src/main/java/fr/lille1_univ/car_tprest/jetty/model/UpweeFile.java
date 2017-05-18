package fr.lille1_univ.car_tprest.jetty.model;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableFile;
import fr.lille1_univ.car_tprest.jetty.utils.Constants;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

public class UpweeFile extends File {
	
	public UpweeUser owner;
	public ArrayList<UpweeUser> readers;	
	public UpweeFile[] files;	
	public JSONRenderableFile[] renderableFiles;	
	public String upweePath;
	//TODO get the file type like "image, text, video, sound, etc"
	public String extension = "";
	Logger l = new Logger("UpweeFile");

	public UpweeFile(String pathname) {
		super(Constants.WORK_DIRECTORY + "/"  + pathname);
		l.i("creating file : " + this.getPath() + " absolute : " + this.getAbsolutePath());
		
		//Un peu mal fait j'aurais besoin d'enregiser les propriétaires et les lecteurs en base
		//TODO simuler ce fonctionnement ? (Par un fichier texte ou whatever)
		this.owner = null;
		this.readers = null; //Pour le moment, need fausse bdd pour s'en servir
		this.upweePath = Constants.WORK_DIRECTORY + "/"  + pathname;
		
		int i = pathname.lastIndexOf('.');
		if (i >= 0) {
		    this.extension = pathname.substring(i+1);
		}
	}

	 
	@Override
	@Deprecated
	/***
	 * @deprecated
	 */
	public UpweeFile[] listFiles() {
		// TODO Auto-generated method stub
		File[] files = super.listFiles();
		this.files = new UpweeFile[files.length];
		
		for(int i=0;i<files.length;i++) {
			this.files[i] = new UpweeFile(files[i].getPath().replaceAll(Constants.WORK_DIRECTORY + "/", ""));
		}
		return this.files;
	}
	
	public String getType() {
		return this.extension;
	}
	
	public String getUpweePath() {
		return this.upweePath;
	}

	public JSONRenderableFile[] listRenderableFiles() {
		File[] files = super.listFiles();
		this.renderableFiles = new JSONRenderableFile[files.length];
		
		for(int i=0;i<files.length;i++) {
			String properpath = files[i].getPath().replaceAll(Constants.WORK_DIRECTORY, "");
			l.i("proper path : " + properpath);
			l.i("complete path : " + files[i].getPath());
			this.renderableFiles[i] = new JSONRenderableFile(new UpweeFile(properpath));
		}
		return this.renderableFiles;
	}

}
