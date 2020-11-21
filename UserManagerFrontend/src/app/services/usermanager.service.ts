import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {UserModel} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UsermanagerService {

  isLoggedIn = new BehaviorSubject<boolean>(false);
  currUser = new BehaviorSubject<UserModel>(new UserModel());
  backendUrl = 'http://localhost:8080/account';

  private readonly commonHttpHeaders;

  constructor(private http: HttpClient) {
    this.commonHttpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');
  }

  login(username: string, password: string): Observable<UserModel> {
    return this.http.post<UserModel>(this.backendUrl + '/login', {
      username, password
    });
  }

  deleteAccount() {
    return this.http.post(this.backendUrl + '/delete', {
      ...this.currUser.value
    });
  }

  changePassword(newPassword: string) {
    return this.http.post(this.backendUrl + '/password', {
      username: this.currUser.getValue().username,
      newPassword
    });
  }


  register(firstname: string, lastname: string, username: string, password: string) {
    return this.http.post(this.backendUrl, {
      firstname,
      lastname,
      username,
      password
    });
  }
}
