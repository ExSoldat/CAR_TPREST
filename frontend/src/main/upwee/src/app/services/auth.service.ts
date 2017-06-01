import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ApiUtils} from './api-utils';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthService {

  username = '';
  firstvisit = false;
  constructor(private http:Http,private apic : ApiUtils) { }

  /*
  * Calling api to authenticate an user
  */  
  public apiAuthenticate(credentials):Observable<any> {
    return this.http.post(this.apic.base_url + 'authenticate', 
        {login:credentials.login, 
        password:credentials.password})
    .map((response: Response) => {
      //Successful connection
        if(!response.json().code) {
          this.username = response.json().username;
          this.firstvisit = response.json().is_first_connection;
          return true;
        } else {
          return response.json().code;
        }
      });
  }

  /*
  * Calling api to register an user
  */  
  public apiRegister(credentials):Observable<any> {
    return this.http.post(this.apic.base_url + 'register', 
        {login:credentials.login, 
        email:credentials.email,
        password:credentials.password})
    .map((response: Response) => {
      //Successful connection
        if(response.json().code == 205) {
          return true;
        } else {
          return response.json().message;
        }
      });
  }

  public isAuthenticated() : boolean {
    return this.username != '';
  }

}
