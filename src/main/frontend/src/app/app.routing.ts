import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AppComponent} from "./app.component";

const routes: Routes = [{
  path: '',
  component: AppComponent,
  //canActivate: [UnauthGuard],
  data: {
    title: 'Home'
  },
  /*children: [
      {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
      {
        path: '',
        loadChildren: './auth/auth.module#AuthModule'
      },
      {
        path: 'dashboard',
        component: DashboardComponent
      },

    ]*/
  },
  /*{
    path: 'login',
    //canActivate: [AuthGuard],
    component: LoginComponent,
  }*/
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
