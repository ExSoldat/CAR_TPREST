package fr.lille1_univ.car_tprest.jetty.model.json;

import java.io.File;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;

public class JSONRenderableFileList extends JSONRenderableList {
	/**
	* A function that is used to render a files list
	*/
	public JSONRenderableFileList(JSONRenderableFile[] files) {
		super(files);
	}
}
