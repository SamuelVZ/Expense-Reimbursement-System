import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {

  welcome!: string;

  constructor() { }

  ngOnInit(): void {
    this.welcome = 'Welcome back ' + localStorage.getItem('username');
    this.populateTable();

  }



  populateTable(): void{

  }
}
