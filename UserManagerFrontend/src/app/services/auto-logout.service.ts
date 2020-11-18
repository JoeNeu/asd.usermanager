import {Injectable} from '@angular/core';
import {UsermanagerService} from './usermanager.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AutoLogoutService {

  private logoutInterval = 1;
  private checkInterval = 2000;


  get lastAction() {
    return +localStorage.getItem('lastAction');
  }

  set lastAction(value) {
    localStorage.setItem('lastAction', value.toString());
  }

  constructor(private usermanagerService: UsermanagerService, private router: Router) {
  }


  start() {
    this.reset();
    this.check();
    this.initListener();
    this.initInterval();
  }


  initListener() {
    document.body.addEventListener('click', () => this.reset());
  }

  initInterval() {
    setInterval(() => {
      if (this.usermanagerService.isLoggedIn.value) {
        this.check();
      }
    }, this.checkInterval);
  }

  reset() {
    this.lastAction = Date.now();
  }

  check() {
    const now = Date.now();
    const timeleft = this.lastAction + this.logoutInterval * 60 * 1000;
    const diff = timeleft - now;
    const isTimeout = diff < 0;

    if (isTimeout && this.usermanagerService.isLoggedIn.value) {
      console.log(`Sie wurden automatisch nach ${this.logoutInterval} Minuten Inaktivität ausgeloggt.`);
      this.usermanagerService.isLoggedIn.next(false);
      this.router.navigate(['']);
    }
  }

}
