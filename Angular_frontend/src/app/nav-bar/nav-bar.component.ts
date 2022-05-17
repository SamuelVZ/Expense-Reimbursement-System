import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  logged!: boolean;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  ngDoCheck(): void {
    if (localStorage.getItem("jwt")) {
      this.logged = true;
    }
  }

  logout(): void {
    localStorage.removeItem("jwt");
    localStorage.removeItem("userId");
    localStorage.removeItem("user_role");
    localStorage.removeItem("username");
    this.logged = false;
    this.router.navigate(['/login'])
  }
}
