import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Reimbursement } from '../models/Reimbursement';
import { DashboardService } from '../services/dashboard.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  welcome!: string;
  reimbursements!: Observable<Reimbursement[]>;
  statusSelected!: number;
  employeeId!: string;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.welcome = 'Welcome back ' + localStorage.getItem('username');
    this.employeeId = `${localStorage.getItem('userId')}`;
    this.populateTable();
  }

  populateTable(): void{
    console.log(localStorage);
    this.reimbursements = this.dashboardService.populateUserTable(`${this.employeeId}`);
  }

  fillImage(id: number): string{
    return 'http://localhost:8081/reimbursements/' + id + '/image';
  }

  selectedOption(event: any){
    this.statusSelected = event.target.value;
  }

  updateStatus(id: number){
    this.dashboardService.updateStatus(id, this.statusSelected).subscribe(
      next => {
        this.populateTable();
      });
  }
}
