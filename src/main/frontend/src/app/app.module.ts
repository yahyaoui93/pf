import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppmenuComponent } from './appmenu/appmenu.component';
import { AppfooterComponent } from './appfooter/appfooter.component';
import { AppsettingComponent } from './appsetting/appsetting.component';
import { AppheaderComponent } from './appheader/appheader.component';
import { AuthComponent } from './auth/auth.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./auth/auth.service";
import {LoginComponent} from "./auth/login.component";
import { TacheComponent } from './taches/tache/tache.component';
import {TacheService} from './services/tache.service'

@NgModule({
  declarations: [
    AppComponent,
    AppmenuComponent,
    AppfooterComponent,
    AppsettingComponent,
    AppheaderComponent,
    AuthComponent,
    LoginComponent,
    TacheComponent


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,

  ],
  providers: [
    AuthService,
    TacheService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
