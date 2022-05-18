import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reimbursement } from '../models/reimbursement';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  URLm = 'http://34.83.66.148:1000/reimbursements';


  constructor(private httpClient: HttpClient) { }


  // populateManagerTable(): Observable<Reimbursement>{
  //   return
  // }

}
