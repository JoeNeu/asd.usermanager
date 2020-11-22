import {Component, OnDestroy, OnInit} from '@angular/core';
import {AutoLogoutService} from '../../services/auto-logout.service';
import {UsermanagerService} from '../../services/usermanager.service';
import {Router} from '@angular/router';
import {UserModel} from '../../models/user.model';
import {BehaviorSubject, EMPTY, Subject} from 'rxjs';
import {catchError, takeUntil} from 'rxjs/operators';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationComponent} from "../dialogue/confirmation/confirmation.component";


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  currUser: UserModel;
  private unsubscribe$ = new Subject();
  confirmed = new BehaviorSubject<boolean>(false);

  constructor(private autoLogoutService: AutoLogoutService,
              private usermanagerService: UsermanagerService,
              private router: Router,
              private dialog: MatDialog) {
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
    this.usermanagerService.deleteAccount().pipe(
      catchError(err => {
        console.error('Delete Account failed with message: ' + err.message)
        return EMPTY;
      }))
      .subscribe(() => {
        this.usermanagerService.isLoggedIn.next(false);
        this.usermanagerService.currUser.next(null);
        this.router.navigate(['']);
      });
  }

  confirmDelete() {
    this.dialog.open(ConfirmationComponent, {
      height: '380px',
      width: '400px',
      hasBackdrop: true
    }).afterClosed()
      .subscribe(result => {
        this.confirmed.next(result);
        if (this.confirmed.value) {
          this.deleteAccount();
        }
      });
  }

  passwordChange() {
    this.router.navigate(['/change-password'])
  }

  logout() {
    this.usermanagerService.isLoggedIn.next(false);
    this.router.navigate(['']);
  }
}
