import {Component} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {UsermanagerService} from "../../services/usermanager.service";
import {Router} from "@angular/router";
import {catchError} from "rxjs/operators";
import {EMPTY} from "rxjs";
import {UserModel} from "../../models/user.model";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {

  passwordControl = new FormControl('', [Validators.required]);
  repeatPasswordControl = new FormControl('', [Validators.required]);

  constructor(private userManagerService: UsermanagerService,
              private router: Router) {
  }

  isBlank(): boolean {
    return this.passwordControl.invalid || this.repeatPasswordControl.invalid || this.passwordsNotEqual();
  }

  passwordsNotEqual() {
    return this.passwordControl.value !== this.repeatPasswordControl.value;
  }

  changePassword() {
    this.userManagerService.changePassword(this.passwordControl.value).pipe(
      catchError(err => {
        console.error('Change Password failed with message: ' + err.message);
        return EMPTY;
      }))
      .subscribe(_ => {
        this.router.navigate(['/main']);
      });
  }
}
