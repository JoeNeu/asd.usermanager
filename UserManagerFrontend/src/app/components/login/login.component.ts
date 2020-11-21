import {Component} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {UsermanagerService} from '../../services/usermanager.service';
import {catchError} from 'rxjs/operators';
import {BehaviorSubject, EMPTY} from 'rxjs';
import {Router} from '@angular/router';
import {UserModel} from '../../models/user.model';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);

  loginError = new BehaviorSubject<boolean>(false);
  loginErrorMessage = new BehaviorSubject<string>('');

  constructor(private usermanagerService: UsermanagerService,
              private router: Router) {
  }

  isBlank(): boolean {
    return this.usernameControl.invalid || this.passwordControl.invalid;
  }

  login() {
    this.usermanagerService.login(this.usernameControl.value, this.passwordControl.value)
      .pipe(catchError((err: HttpErrorResponse) => {
        this.loginError.next(true);
        this.loginErrorMessage.next(err.error);
        this.usermanagerService.isLoggedIn.next(false);
        this.router.navigate(['']);
        return EMPTY;
      }))
      .subscribe((model: UserModel) => {
        this.loginError.next(false);
        this.loginErrorMessage.next('');
        this.usermanagerService.isLoggedIn.next(true);
        this.usermanagerService.currUser.next(model);
        this.router.navigate(['/main']);
      });
  }

}
