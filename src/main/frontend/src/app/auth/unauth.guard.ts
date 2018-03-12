/**
 * Created by poss on 07/03/2018.
 */
import { Injectable } from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, ActivatedRoute, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";
import {Subscription} from "rxjs/Subscription";

@Injectable()
export class UnauthGuard  implements CanActivate {
  sub: Subscription;
  constructor(private as: AuthService, private router: Router/*, private toastr: ToastrService*/) {}

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): boolean {

    let Authenticated = true;
    //let roles = route.data["roles"] as Array<string>;

    this.sub = this.as.isAuthenticatedChange.subscribe(
      data=>{
        if(data.isAuthenticated){
          Authenticated = true;
          /*if(!(roles && (roles.indexOf(data.user.role) > -1))){
           Authenticated = false;
           this.router.navigate(['login']);
           //sub.unsubscribe();
           return Authenticated
           }*/
          return Authenticated
        }else {
          Authenticated = false;
          this.router.navigate(['login']);
          this.sub.unsubscribe();
          return Authenticated
        }
      }
    );
    return Authenticated;
  }
}

