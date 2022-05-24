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
    window.localStorage.clear();
    const username = form.value.username;
    const password = form.value.password;

    this.loginService.login(username, password).subscribe({

      next: (v) => {
        localStorage.setItem('jwt', v.headers.get('Token'));
        localStorage.setItem('userId', v.body.id);
        localStorage.setItem('user_role', v.body.role);
        localStorage.setItem('username', v.body.username);
        //console.log(localStorage.getItem('jwt'));
        //console.log(v);

        if(localStorage.getItem('user_role') == 'manager'){
          this.router.navigate(['/manager-dashboard']);
        } else if(localStorage.getItem('user_role') == 'employee'){
          this.router.navigate(['/user-dashboard']);
        }
      }

    });
    form.reset();
  }

}
