import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UsermanagerService} from '../../services/usermanager.service';
import {catchError} from 'rxjs/operators';
import {BehaviorSubject, EMPTY} from 'rxjs';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerGroup: FormGroup;
  loginError = new BehaviorSubject<boolean>(false);
  loginErrorMessage = new BehaviorSubject<string>('');

  constructor(private fb: FormBuilder, private usermanagerService: UsermanagerService, private router: Router) {
    this.registerGroup = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  register() {
    this.usermanagerService.register(this.registerGroup.value.firstname, this.registerGroup.value.lastname,
      this.registerGroup.value.username, this.registerGroup.value.password).pipe(catchError((err: HttpErrorResponse) => {
      this.loginError.next(true);
      this.loginErrorMessage.next(err.error);
      return EMPTY;
    }))
      .subscribe(() => {
        this.loginError.next(false);
        this.loginErrorMessage.next('');
        this.router.navigate(['']);
      });
  }

}
