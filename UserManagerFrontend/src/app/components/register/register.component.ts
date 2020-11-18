import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerGroup: FormGroup;

  constructor(private fb: FormBuilder) {
    this.registerGroup = this.fb.group({
      vorname: ['', Validators.required],
      nachname: ['', Validators.required],
      username: ['', Validators.required],
      passwort: ['', Validators.required]
    });
  }

  register() {
    // do something
  }

}
