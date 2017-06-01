import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router,
         NavigationExtras } from '@angular/router';
import { MdSnackBar } from '@angular/material';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerCredentials = { login: '', email:'', password: ''};
  loading : boolean = false;
  constructor(private auth : AuthService, private router : Router,private snackbar: MdSnackBar) { }

  ngOnInit() {
  }

  register() {
  	if(this.registerCredentials.login == '' 
  		|| this.registerCredentials.email == ''
  		|| this.registerCredentials.password == '') {
		this.showError('Please fill all fields to be able to register to Upwee');
  	} else {
	  	this.loading = true;
	  	this.auth.apiRegister(this.registerCredentials).subscribe(result => {
	  		this.loading = false;
	  		if(result === true) {
	  			this.gotologin();
	  			this.showError('Your account has successfully been created');
	  		} else {
	  			this.showError(result);
	  		}
	  	},
      error => {
        this.loading = false;
        this.showError("We were unable to reach the server. Please try again");
      });
	  }
  }

   showError(message : string) {
    this.snackbar.open(message, '', {duration : 5000});
  };

  gotologin() {
    this.router.navigate(['login']);
  }

  submit(event) {
    if(event.keyCode == 13) 
      this.register();
  }

}
