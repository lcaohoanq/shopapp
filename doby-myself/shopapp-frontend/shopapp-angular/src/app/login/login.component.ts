import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {RegisterDTO} from "../dtos/user/register.dto";
import {LoginDTO} from "../dtos/user/login.dto";
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('loginForm') loginForm!: NgForm;

  phoneNumber: string = '0934162561'
  password: string = '123456'

  constructor(private router: Router, private userService: UserService) {

  }

  onPhoneNumberChange() {
    console.log(`Phone: ${this.phoneNumber}`);

    //if phone

  }

  login() {
    const message = `Phone: ${this.phoneNumber} + Password: ${this.password}`;


    const loginDTO: LoginDTO = {
      "phone_number": this.phoneNumber,
      "password": this.password
    }

    this.userService.login(loginDTO).subscribe({
      next: (response: any) => {
        console.log(response.body);
        debugger;
      },
      complete: () => {
        alert("Login success");
      },
      error: (error) => {
        //xu li loi khi call api that bai
        alert("Cannot login: " + error.error);
      }
    });
  }

  ngAfterViewInit(): void {
  }

}
