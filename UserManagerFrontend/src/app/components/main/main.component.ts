import {Component, OnInit} from '@angular/core';
import {AutoLogoutService} from '../../services/auto-logout.service';
import {UsermanagerService} from '../../services/usermanager.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  constructor(private autoLogoutService: AutoLogoutService, private usermanagerService: UsermanagerService,
              private router: Router) {
  }


  ngOnInit() {
    this.autoLogoutService.start();
  }

  deleteAccount() {
    this.usermanagerService.deleteAccount();
  }

  logout() {
    this.usermanagerService.isLoggedIn.next(false);
    this.router.navigate(['']);
  }
}
