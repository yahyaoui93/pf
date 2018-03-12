/**
 * Created by poss on 07/03/2018.
 */
import { Injectable } from '@angular/core';
import {Router, CanActivate} from '@angular/router';
import {AuthService} from "./auth.service";
import {Subscription} from "rxjs/Subscription";

@Injectable()
export class AuthGuard implements CanActivate {
  sub: Subscription;
  constructor(private as: AuthService, private router: Router/*, private toastr: ToastrService*/) { }

  canActivate(): boolean {
    let NotAuthenticated = true;
    this.sub = this.as.isAuthenticatedChange.subscribe(
      data=>{
        if(!data.isAuthenticated){
          NotAuthenticated = true
        }else{
          //this.toastr.error('Access Denied!');
          this.router.navigate([''], { skipLocationChange: true });
          NotAuthenticated = false;
          this.sub.unsubscribe();
        }
      },
      err=>{

      },
      ()=>{
        this.sub.unsubscribe();
      })
    //sub.unsubscribe();
    return NotAuthenticated;
  }


}
