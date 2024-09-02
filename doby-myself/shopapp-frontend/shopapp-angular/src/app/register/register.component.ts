import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
//dung de goi request
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements AfterViewInit {

  //init mot cai form de reference qua ben register component
  @ViewChild('registerForm') registerForm!: NgForm;

  //Khai bao cac bien tuong ung voi cac truong trong form dang ky
  phone: string;
  password: string;
  retypePassword: string;
  address: string;
  fullName: string;
  isAccepted: boolean;
  dob: Date;

  constructor(private router: Router, private http: HttpClient) {
    this.phone = '0552234124';
    this.password = '12345';
    this.retypePassword = '12345';
    this.address = 'Da Nang City';
    this.fullName = 'Nguyen Ky Vy';
    this.isAccepted = true;
    this.dob = new Date();
    this.dob.setFullYear(this.dob.getFullYear() - 18); // Mac dinh ngay sinh la 18 tuoi
  }

  onPhoneChange() {
    console.log(`Phone: ${this.phone}`);

    //if phone

  }

  register() {
    const message = `
    Phone: ${this.phone},
    Password: ${this.password},
    Address: ${this.address},
    Full name: ${this.fullName},
    isAccepted: ${this.isAccepted}
    date of birth: ${this.dob}
    `;

    const apiUrl = 'http://localhost:8080/api/v1/users/register'

    const registerData = {
      "fullname": this.fullName,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dob,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
    }

    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    this.http.post(apiUrl, registerData, {headers: headers}).subscribe(
      {
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
      }

    );
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
