import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {EMPTY, Observable, of} from 'rxjs';
import {UsermanagerService} from './usermanager.service';
import {first, switchMap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class IsLoggedInGuardService implements CanActivate {

  constructor(private usermanagerService: UsermanagerService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {

    return this.usermanagerService.isLoggedIn
      .pipe(first(),
        switchMap(isLoggedIn => {
          if (!isLoggedIn) {
            this.router.navigate(['']);
            return EMPTY;
          } else {
            return of(true);
          }
        }));
  }

}
