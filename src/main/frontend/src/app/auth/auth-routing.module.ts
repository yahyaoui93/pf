import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './auth.guard';
import {LoginComponent} from './login.component';
import {UnauthGuard} from './unauth.guard';

const routes: Routes = [
  {
    path: '',
    children: [
    ,
      {
        path: 'login',
        canActivate: [AuthGuard],
        component: LoginComponent,
        data: {
          title: 'Login'
        }
      },
      
      
      
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}
