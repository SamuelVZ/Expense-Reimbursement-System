import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reimbursement } from '../models/Reimbursement';


@Injectable({
  providedIn: 'root'
})




export class DashboardService {
  URLm = 'http://localhost:8081/reimbursements';

  headers = new HttpHeaders()
  .set('Authorization', `Bearer ${localStorage.getItem('jwt')}`);

  constructor(private httpClient: HttpClient) { }

  populateManagerTable(): Observable<Reimbursement[]>{
    return this.httpClient.get<Reimbursement[]>(this.URLm, { 'headers': this.headers });
  }


  updateStatus(id: number, statusId: number): Observable<any>{

    let params = new HttpParams().set('statusId', statusId);
    const options = {params: params, 'headers': this.headers};

    return this.httpClient.patch(`${this.URLm}/${id}`, null, options);
  }

}
