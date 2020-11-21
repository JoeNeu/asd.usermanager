import {Component, OnDestroy, OnInit} from '@angular/core';
import {AutoLogoutService} from '../../services/auto-logout.service';
import {UsermanagerService} from '../../services/usermanager.service';
import {Router} from '@angular/router';
import {UserModel} from '../../models/user.model';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  currUser: UserModel;
  private unsubscribe$ = new Subject();

  constructor(private autoLogoutService: AutoLogoutService, private usermanagerService: UsermanagerService,
              private router: Router) {
  }


  ngOnInit() {
    this.autoLogoutService.start();
    this.usermanagerService.currUser.pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((user: UserModel) => {
      this.currUser = user;
    });
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  deleteAccount() {
    this.usermanagerService.deleteAccount();
  }

  logout() {
    this.usermanagerService.isLoggedIn.next(false);
    this.router.navigate(['']);
  }
}
