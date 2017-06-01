import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ApiUtils} from './api-utils';
import {Http, Response, RequestOptions, URLSearchParams } from '@angular/http';
import {AuthService} from './auth.service'
import 'rxjs/add/operator/map';

@Injectable()
export class FileService {

	constructor(private auth : AuthService, private http:Http,private apic : ApiUtils) { }

	/*
	* Calling api to list files from a path
	*/  
	public apiListFiles(relativePath : string):Observable<any> {
		var url : string = encodeURI(this.apic.base_url + 'files/' + this.auth.username + relativePath);
		console.log('calling : ' + url);
		return this.http.get(url)
		.map((response: Response) => {
			//return response.json();
			if(response.json().code === 401) 
				return false;
			else 
				return response.json();
		});
	}

	/*
	* Calling api to list files from a path
	*/  
	public apiDownloadFile(relativePath : string, filename : string):string {
		var url :string = encodeURI(this.apic.base_url + 'files/download/' + this.auth.username + relativePath + '?file='+filename);
		console.log('calling : ' + url);
		return url;
	}

	/*
	* Calling api to upload a file in path
	*/  
	public apiUploadFile(relativePath : string, f : File) :Observable<any> {
		var url :string = encodeURI(this.apic.base_url + 'files/upload/' + this.auth.username + relativePath);
		console.log('calling : ' + url);

		let formData:FormData = new FormData();
    	formData.append('file', f, f.name);
    	//this.http.post()
    	//this.http.post(url, formData)
    	//	.map(res => res.json())
    	return this.http.post(url, formData)
	    .map((response: Response) => {
	      //Successful connection
	      	if(response.json().code == 203) {
	      		return true
	      	} else {
	      		return response.json().message;
	      	}
	    });
	}

	/*
	* Calling api to create a folder path
	*/  
	public apiCreateFolder(relativePath : string, foldername : string) :Observable<any> {
		var url :string = encodeURI(this.apic.base_url + 'files/create/' + this.auth.username + relativePath + foldername);
		console.log('calling : ' + url);

    	return this.http.post(url, {})
	    .map((response: Response) => {
	      	if(response.json().code == 204) {
	      		return true
	      	} else {
	      		return response.json().message;
	      	}
	    });
	}

	/*
	* Calling api to delete a file in path
	*/ 
	public apiDeleteFile(path : string, filename : string) :Observable<any> {
		var url :string = encodeURI(this.apic.base_url + 'files/delete/' + this.auth.username + path + '?file=' + filename);
		console.log('calling : ' + url);
    	return this.http.delete(url)
	    .map((response: Response) => {
	      //Successful connection
	      	if(response.json().code == 202) {
	      		return true
	      	} else {
	      		return response.json().message;
	      	}
	    });
	}

	//TODO
	public apiMoveFile(mfile : string, mtarget : string,  path : string, upward : boolean = true) :Observable<any> {
		var url :string = encodeURI(this.apic.base_url + 'files/move/' + this.auth.username + path);
		var data = {file : mfile, target : mtarget};
		console.log('calling : ' + url + " with data : ");
		console.log(data);
		return this.http.post(url, data)
	    .map((response: Response) => {
	      //Successful connection
	      	if(response.json().code == 206) {
	      		return true
	      	} else {
	      		return response.json().message;
	      	}
	    });
	}
}
