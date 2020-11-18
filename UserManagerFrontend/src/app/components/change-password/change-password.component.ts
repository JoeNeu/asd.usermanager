import {Component} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {

  passwordControl = new FormControl('', [Validators.required]);
  repeatPasswordControl = new FormControl('', [Validators.required]);

  constructor() {
  }

  isBlank(): boolean {
    return this.passwordControl.invalid || this.repeatPasswordControl.invalid || this.passwordsNotEqual();
  }

  passwordsNotEqual() {
    return this.passwordControl.value !== this.repeatPasswordControl.value;
  }

  changePassword() {
    // do something
  }

}
