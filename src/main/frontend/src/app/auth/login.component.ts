import {Component, OnInit,} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ValidationExtensions} from '../validator/validation.extensions';
import {AuthService} from "./auth.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  myForm: FormGroup;
  submitted: boolean = false;
  constructor(private us: AuthService, private router: Router/* private toastr: ToastrService*/) {
    this.myForm = new FormGroup({
      'email': new FormControl('', [ValidationExtensions.required(), ValidationExtensions.email()]),
      'password': new FormControl('', [ValidationExtensions.required(), ValidationExtensions.minLength(8)])
    })
  }

  ngOnInit() {}

  submit(){
    this.submitted = true;
    if(this.myForm.valid) {
      this.us.signIn(this.myForm.value).subscribe(
        data=>{
          this.router.navigate(['/']);
        },
        err=>{
          console.log(err)
        }
      )
    }
  }

}
