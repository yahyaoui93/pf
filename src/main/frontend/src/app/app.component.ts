import { Component } from '@angular/core';

import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  constructor(private as:AuthService){
    as.signIn({email:'admin@user.com',password:'12345678'}).subscribe(
      data=>{
        console.log(data)
      },
      error2 => {
        console.log(error2)
      }
    )
  }
}
