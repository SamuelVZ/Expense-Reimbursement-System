import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private loginService: LoginService,
    ) { }

  ngOnInit(): void {
  }


  login(form: NgForm){

    const username = form.value.username;
    const password = form.value.password;

    this.loginService.login(username, password).subscribe({

      next: (v) => {
        localStorage.setItem('jwt', v.headers.get('Token'));
        localStorage.setItem('userId', v.body.id);
        localStorage.setItem('user_role', v.body.role);
        localStorage.setItem('username', v.body.username);
        //this.router.navigate(['/dashboard']);
        console.log(localStorage.getItem('jwt'));
        console.log(localStorage.getItem('userId'));
        console.log(localStorage.getItem('user_role'));
        console.log(localStorage.getItem('username'));
        //console.log(v);
      }

    });
    form.reset();
  }

}
