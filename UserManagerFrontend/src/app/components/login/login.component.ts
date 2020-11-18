import {Component} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {UsermanagerService} from '../../services/usermanager.service';
import {catchError} from 'rxjs/operators';
import {EMPTY} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);

  constructor(private usermanagerService: UsermanagerService,
              private router: Router) {
  }

  isBlank(): boolean {
    return this.usernameControl.invalid || this.passwordControl.invalid;
  }

  login() {
    this.usermanagerService.login(this.usernameControl.value, this.passwordControl.value)
      .pipe(catchError(_ => {
        this.usermanagerService.isLoggedIn.next(true);
        this.router.navigate(['/main']);
        return EMPTY;
      }))
      .subscribe(() => {
        this.usermanagerService.isLoggedIn.next(true);
        this.router.navigate(['/main']);
      });
  }

}
