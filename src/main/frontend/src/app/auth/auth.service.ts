/**
 * Created by poss on 07/03/2018.
 */
import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/map';
import {User} from "../models/user";

@Injectable()
export class AuthService {

  user:User;
  token:string = '';
  isAuthenticated:boolean = false;
  isAuthenticatedChange: BehaviorSubject<any> = new BehaviorSubject({
    user:this.user,
    isAuthenticated:this.isAuthenticated
  });
  private baseUrl: string = 'http://localhost:5678/users/';

  constructor(private http:HttpClient) {
    this.isAuthenticatedChange.subscribe((data) => {
      if(this.isAuthenticated != data.isAuthenticated){
        this.isAuthenticated=data.isAuthenticated;
      }
    });
    if (localStorage.getItem('currentUser')) {
      this.user = this.getUserFromSession()
      this.token = this.getTokenFromSession()
      this.isAuthenticatedChange.next({
        user:this.user,
        isAuthenticated:true
      });
      this.getDataByToken()
    }else {
      this.isAuthenticatedChange.next({
        user:this.user,
        isAuthenticated:false
      });
    }
  }

  signIn({email,password}): Observable<any> {
    return this.http.post<any>(this.baseUrl+"login",{
      email:email,
      password
    })
      .map(res=>{
        this.user = res.user;
        this.token = res.token;
        this.setSession();
        this.isAuthenticatedChange.next({
          user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }

  signup(newUser:User): Observable<any> {
    return this.http.post<any>(this.baseUrl+"signup",newUser)
      .map(res=>{
        this.user = res.user;
        this.token = res.token;
        this.setSession();
        this.isAuthenticatedChange.next({
          user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }

  refreshToken(): Observable<any> {
    return this.http.get<any>(this.baseUrl+"refresh")
      .map(res => {
        this.user = res.user;
        this.token = res.token;
        this.setSession();
        this.isAuthenticatedChange.next({
        user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }

  getDataByToken(): Observable<User> {
    return this.http.get<User>(this.baseUrl+"me")
      .map(res => {
        this.user = res;
        this.setSession();
        this.isAuthenticatedChange.next({
          user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }
/*
  updateDataByToken(newUser:User): Observable<User> {
    return this.http.put<User>(this.baseUrl,{
      firstname: newUser.,
      lastname: newUser.lastname
    })
      .map(res=>{
        this.user = res;
        this.setSession();
        this.isAuthenticatedChange.next({
          user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }*/

  updatePasswordByToken(password :string): Observable<any> {
    console.log(this.token)
    return this.http.patch<any>(this.baseUrl,{
      password
    })
      .map(res=>{
        this.user = res.user;
        this.token = res.token;
        console.log(this.token)
        this.setSession();
        this.isAuthenticatedChange.next({
          user:this.user,
          isAuthenticated:true
        });
        return res;
      })
  }





  logOut(){
    this.token = null;
    this.user = null;
    this.isAuthenticatedChange.next({
      user:this.user,
      isAuthenticated:false
    });
    localStorage.removeItem('currentUser');
  }


  getUserFromSession():User{
    return JSON.parse(localStorage.getItem('currentUser')).user;
  }

  getTokenFromSession():string{
    return JSON.parse(localStorage.getItem('currentUser')).token
  }

  private setSession() {
    localStorage.setItem('currentUser', JSON.stringify({
      user: this.user,
      token: this.token
    }));
  }

}
