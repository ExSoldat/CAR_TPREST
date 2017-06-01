package fr.lille1_univ.car_tprest.jetty.model.json;

import java.io.File;
import java.util.ArrayList;

import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;

public class JSONRenderableFileList extends JSONRenderableList {
	
	public JSONRenderableFileList(JSONRenderableFile[] files) {
		super(files);
	}
	/*
	@Override
	public String renderJSON() {
		String r = "[";
		for (int i = 0; i<files.length; i++) {
			r += new JSONRenderableFile(files[i]).renderJSON();
			if(i!=files.length-1) {
				r+=", ";
			}
		}
		r+="]";
		return r;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
