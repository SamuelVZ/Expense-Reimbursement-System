import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
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
  acept: string = ".png, .jpg, .jpeg";

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

  submitReimbursement(form: NgForm){

    const formData = new FormData;
    formData.append('amount',form.value.amount);
    formData.append('description',form.value.description);
    formData.append('typeId',form.value.typeId);
    formData.append('image',form.value.image);

    this.dashboardService.submitReimbursement(formData, this.employeeId).subscribe({

      next: () => {
        this.populateTable();
      }

    });
     form.reset();
  }

}
