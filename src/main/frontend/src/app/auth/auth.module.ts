import { NgModule } from '@angular/core';
import {Ng2MDFValidationMessagesModule} from '../validator/module';

import {AuthRoutingModule} from './auth-routing.module';
import {LoginComponent} from './login.component';

import {AuthGuard} from './auth.guard';
import {UnauthGuard} from './unauth.guard';

import {NgxPaginationModule} from 'ngx-pagination';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { Ng2GoogleRecaptchaModule }  from 'ng2-google-recaptcha';

@NgModule({
  imports: [
   
    AuthRoutingModule,
    Ng2MDFValidationMessagesModule,
    NgxPaginationModule,
    NgbModule,
    Ng2GoogleRecaptchaModule
  ],
  providers: [
    AuthGuard,
    UnauthGuard,
  ],
  declarations: [
    LoginComponent,
  ]
})
export class AuthModule { }
