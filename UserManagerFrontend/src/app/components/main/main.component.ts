import {Component, OnInit} from '@angular/core';
import {AutoLogoutService} from '../../services/auto-logout.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  constructor(private autoLogoutService: AutoLogoutService) {
  }


  ngOnInit() {
    this.autoLogoutService.start();
  }
}
