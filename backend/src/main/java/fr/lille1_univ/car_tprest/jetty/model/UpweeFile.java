package fr.lille1_univ.car_tprest.jetty.model;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableFile;
import fr.lille1_univ.car_tprest.jetty.utils.Constants;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

/**
	* A class that represents a file
	*/
public class UpweeFile extends File {
	
	public UpweeUser owner; 
	public ArrayList<UpweeUser> readers; //Unused
	public UpweeFile[] files;	
	public JSONRenderableFile[] renderableFiles;	
	public String upweePath;
	public String extension = ""; // TODO : replace by a type like "image/movie/music/file"
	public String mimetype = ""; //TODO ?
	Logger l = new Logger("UpweeFile");

	public UpweeFile(String pathname) {
		super(Constants.WORK_DIRECTORY + "/"  + pathname);
		l.i("creating file : " + this.getPath() + " absolute : " + this.getAbsolutePath());
		
		this.owner = null;
		this.readers = null; //Because unused
		this.upweePath = Constants.WORK_DIRECTORY + "/"  + pathname;
		
		int i = pathname.lastIndexOf('.');
		if (i >= 0) {
		    this.extension = pathname.substring(i+1);
		} else {
			if(this.isFile()) {
				this.extension = "txt"; //Avoid to get files without extension to be treated as folders
			} else {
				this.extension = "folder";
			}
		}
	}

	 
	@Override
	@Deprecated
	/***
	 * An usnused function
	 * see listRenderableFiles
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
	/**
	* A function that is used to get a representation of the files list to answer to the rest service
	* Last minute update : Added an ugly double loop to firstly send folders then files
	*/
	public JSONRenderableFile[] listRenderableFiles() {
		File[] files = super.listFiles();
		this.renderableFiles = new JSONRenderableFile[files.length];
		int ri = 0;
		for(int i=0;i<files.length;i++) {
			String properpath = files[i].getPath().replaceAll(Constants.WORK_DIRECTORY, "");
			l.i("proper path : " + properpath);
			l.i("complete path : " + files[i].getPath());
			if(files[i].isDirectory()) {
				l.i("is directory");
				this.renderableFiles[ri] = new JSONRenderableFile(new UpweeFile(properpath));
				ri++;
			}
		}
		
		for(int i=0;i<files.length;i++) {
			String properpath = files[i].getPath().replaceAll(Constants.WORK_DIRECTORY, "");
			l.i("proper path : " + properpath);
			l.i("complete path : " + files[i].getPath());
			if(files[i].isFile()) {
				l.i("is file");
				this.renderableFiles[ri] = new JSONRenderableFile(new UpweeFile(properpath));
				ri++;
			}
		}
		return this.renderableFiles;
	}
	
	/**
	* Static functions used to get file type, used exclusively to get the icon we should show in the front end
	*/
	public static boolean isImage(String extension) {
		return extension.equals("jpeg") || extension.equals("jpg") || extension.equals("gif") || extension.equals("bmp") 
			||	extension.equals("svg") || extension.equals("png");
	}

	public static boolean isSound(String extension) {
		return extension.equals("aac") || extension.equals("aiff") || extension.equals("flac") || extension.equals("mp3") 
				||	extension.equals("wma") || extension.equals("wav") || extension.equals("ogg") || extension.equals("m4p");
	}

	public static boolean isVideo(String extension) {
		return extension.equals("mkv") || extension.equals("flv") || extension.equals("avi") || extension.equals("mp4") 
				||	extension.equals("amv");
	}
}
