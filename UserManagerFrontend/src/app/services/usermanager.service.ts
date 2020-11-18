import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsermanagerService {

  isLoggedIn = new BehaviorSubject<boolean>(false);
  backendUrl = 'http://localhost:8080';

  private readonly commonHttpHeaders;

  constructor(private http: HttpClient) {
    this.commonHttpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(this.backendUrl + '/login', {
      username, password
    });
  }

  logout() {
    return this.http.post(this.backendUrl + '/logout', {
      // account details
    });
  }

  deleteAccount() {
    return this.http.post(this.backendUrl + '/delete', {
      // account details
    });
  }

  changePassword() {
    return this.http.post(this.backendUrl + '/password', {
      // account details + password
    });
  }


}
