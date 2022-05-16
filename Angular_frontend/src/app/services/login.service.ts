import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url: string = 'http://localhost:8081';

  constructor(private httpClient: HttpClient) { }


  login(username: string, password: string): Observable<any> {

      return this.httpClient.post<any>(
        this.url + '/login',
        {
          username: username,
          password: password
        },
        {observe: 'response'}
      );
  }
}



