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
        localStorage.setItem('jwt', v.body.jwt);
        localStorage.setItem('userId', v.body.userId);
        //this.router.navigate(['/dashboard']);
        console.log(localStorage.getItem('userId'));
      }

    });
    form.reset();
  }

}
