import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Reimbursement } from '../models/Reimbursement';
import { DashboardService } from '../services/dashboard.service';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {

  welcome!: string;
  reimbursements!: Observable<Reimbursement[]>;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.welcome = 'Welcome back ' + localStorage.getItem('username');
    this.populateTable();

  }

  populateTable(): void{
    this.reimbursements = this.dashboardService.populateManagerTable();
  }

  fillImage(id: number): string{
    return 'http://localhost:8081/reimbursements/' + id + '/image';
  }
}
