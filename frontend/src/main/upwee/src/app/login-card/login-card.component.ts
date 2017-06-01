import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router,
         NavigationExtras } from '@angular/router';
import { MdSnackBar } from '@angular/material';



@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {
  loading = false;
  registerCredentials = { login: '', password: ''};
  usernameColor = 'primary'
  passwordColor = 'primary'

  constructor(private auth : AuthService, private router : Router,private snackbar: MdSnackBar) { }

  ngOnInit() {
  }

  login() {
  	this.loading = true;
  	this.auth.apiAuthenticate(this.registerCredentials).subscribe(result => {
  	  this.loading = false;
      if (result === true) {
      	console.log(this.auth.username + " logged in");
      
      this.usernameColor = 'primary'
  		this.passwordColor = 'primary'
  		let redirect = this.auth.username ? '/files/'+this.auth.username : '/';
  		let navigationExtras: NavigationExtras = {
          preserveQueryParams: true,
          preserveFragment: true
        };
        console.log(redirect);
        this.router.navigate([redirect]);
        //this.router.navigate(['/detail', this.selectedHero.id]);
      } else {
      	//TODO focus on invalid field and enable registering false screen
        if(result == 500) {
        	//invalid username
        	this.usernameColor = 'warn';
        	this.passwordColor = 'warn';
          this.showError("Invalid username");
        } else if (result == 501) {
        	//invalid password
        	this.usernameColor = 'primary';
  			  this.passwordColor = 'warn';
          this.showError("Invalid password");
        } else {
        	//unknown error
        	this.showError("An unknown error occured. Please try again");
        }
      }
    },
      error => {
        this.loading = false;
        this.showError("We were unable to reach the server. Please try again");
      });
  }

  gotoregister() {
    this.router.navigate(['register']);
  }

  showError(message : string) {
    this.snackbar.open(message, '', {duration : 5000});
  };

  submit(event) {
    if(event.keyCode == 13) 
      this.login();
  }
}
