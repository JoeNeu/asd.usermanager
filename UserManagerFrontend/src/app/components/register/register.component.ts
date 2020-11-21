import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UsermanagerService} from '../../services/usermanager.service';
import {catchError} from 'rxjs/operators';
import {EMPTY} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerGroup: FormGroup;

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
      this.registerGroup.value.username, this.registerGroup.value.password).pipe(catchError(_ => {
      return EMPTY;
    }))
      .subscribe(() => {
        this.router.navigate(['']);
      });
  }

}
