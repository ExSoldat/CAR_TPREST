package fr.lille1_univ.car_tprest.jetty.model.json;

import java.nio.file.Files;
import java.text.SimpleDateFormat;

import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;
import fr.lille1_univ.car_tprest.jetty.model.UpweeFileList;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;

public class JSONRenderableFile extends JSONComplexObject implements JSONRenderableObject {
	
	private UpweeFile value;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	
	public JSONRenderableFile(UpweeFile value) {
		this.value = value;
	}

	@Override
	//LASTOTODO
	public String renderJSON() {
		if(value.exists()) {
			this.addProp("file_name", new JSONRenderableString(value.getName()));
			this.addProp("last_modified", new JSONRenderableString(formatter.format(value.lastModified())));
			this.addProp("size", new JSONRenderableLong(value.length()));
			this.addProp("type", new JSONRenderableString(value.getType()));
			l.i("value is directory : " + value.isDirectory());
			if(value.isDirectory()) {
				this.addProp("files", new JSONRenderableFileList(value.listRenderableFiles()));//TODO Change it, override listFiles function instead of casting it
				this.addProp("icon", new JSONRenderableString("folder"));
			} else {
				if(UpweeFile.isImage(value.getType())) {
					this.addProp("icon", new JSONRenderableString("panorama"));
				} else if (UpweeFile.isSound(value.getType())) {
					this.addProp("icon", new JSONRenderableString("volume_up"));
				} else if (UpweeFile.isVideo(value.getType())) {
					this.addProp("icon", new JSONRenderableString("movie"));
				} else {
					this.addProp("icon", new JSONRenderableString("insert_drive_file"));
				}
				
			}
				//TODO find a generic way of doing it
			return this.renderComplexJSON();
		}
		
		return "null";
	}

	@Override
	public Object getValue() {
		return value;
	}
}
