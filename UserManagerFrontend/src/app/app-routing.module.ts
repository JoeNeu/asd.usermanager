import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ChangePasswordComponent} from './components/change-password/change-password.component';
import {IsLoggedInGuardService} from './services/is-logged-in-guard.service';
import {MainComponent} from './components/main/main.component';

const appRoutes: Routes = [
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full',
    canActivate: []
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: []
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    canActivate: [IsLoggedInGuardService]
  },
  {
    path: 'main',
    component: MainComponent,
    canActivate: [IsLoggedInGuardService]
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {enableTracing: false, useHash: true})
  ],
  exports: [
    RouterModule
  ],
})
export class AppRoutingModule {
}
