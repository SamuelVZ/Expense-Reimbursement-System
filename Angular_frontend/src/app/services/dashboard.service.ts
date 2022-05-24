import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reimbursement } from '../models/Reimbursement';


@Injectable({
  providedIn: 'root'
})


export class DashboardService {
  URLm = 'http://localhost:8081';

  headers = new HttpHeaders();

  constructor(private httpClient: HttpClient) { }

  populateManagerTable(): Observable<Reimbursement[]>{
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('jwt')}`);
    return this.httpClient.get<Reimbursement[]>(`${this.URLm}/reimbursements`, { 'headers': this.headers });
  }

  populateUserTable(employeeId: string): Observable<Reimbursement[]>{
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('jwt')}`);
    return this.httpClient.get<Reimbursement[]>(`${this.URLm}/employees/${employeeId}/reimbursements`, { 'headers': this.headers });
  }

  updateStatus(id: number, statusId: number): Observable<any>{

    let params = new HttpParams().set('statusId', statusId);
    const options = {params: params, 'headers': this.headers};

    return this.httpClient.patch(`${this.URLm}/reimbursements/${id}`, null, options);
  }

}
