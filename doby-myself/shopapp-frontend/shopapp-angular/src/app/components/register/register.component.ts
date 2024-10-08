import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
//dung de goi request
import {HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {RegisterDTO} from "../../dtos/user/register.dto";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements AfterViewInit {

  //init mot cai form de reference qua ben register component
  @ViewChild('registerForm') registerForm!: NgForm;

  //Khai bao cac bien tuong ung voi cac truong trong form dang ky
  phoneNumber: string;
  password: string;
  retypePassword: string;
  address: string;
  fullName: string;
  isAccepted: boolean;
  dob: Date;

  constructor(private router: Router, private userService: UserService) {
    this.phoneNumber = '';
    this.password = '';
    this.retypePassword = '';
    this.address = '';
    this.fullName = '';
    this.isAccepted = false;
    this.dob = new Date();
    this.dob.setFullYear(this.dob.getFullYear() - 18); // Mac dinh ngay sinh la 18 tuoi
  }

  onPhoneNumberChange() {
    console.log(`Phone: ${this.phoneNumber}`);

    //if phone

  }

  register() {
    const message = `
    Phone: ${this.phoneNumber},
    Password: ${this.password},
    Address: ${this.address},
    Full name: ${this.fullName},
    isAccepted: ${this.isAccepted}
    date of birth: ${this.dob}
    `;



    const registerDTO: RegisterDTO = {
      "fullname": this.fullName,
      "phone_number": this.phoneNumber,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dob,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
    }

    this.userService.register(registerDTO).subscribe({
      next: (response: any)=>{
        //xu li key qua tra ve khi user dang ky thanh cong
        if(response && response.status === 200 || response.status === 201){
          this.router.navigate(['/login']);
        }else{
          //xu li key qua tra ve khi user dang ky that bai, neu can
        }
      },
      complete: ()=>{
        alert("Register success");
      },
      error: (error)=>{
        //xu li loi khi call api that bai
        alert("Cannot register: " + error.error);
      }
    });
  }

  checkPasswordMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch': true});
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  checkAge() {
    if (this.dob) {

      console.log(this.dob);

      const today = new Date();
      const birthDate = new Date(this.dob);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }

      console.log("age: " + age);

      if (age < 18) {
        this.registerForm.form.controls['dob'].setErrors({'invalidAge': true});
      } else {
        this.registerForm.form.controls['dob'].setErrors(null);
      }

    }
  }

  ngAfterViewInit(): void {
  }

}
