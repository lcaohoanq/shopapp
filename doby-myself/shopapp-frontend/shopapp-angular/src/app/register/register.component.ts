import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
 //Khai bao cac bien tuong ung voi cac truong trong form dang ky
  phone: string;
  password: string;
  retypePassword: string;
  address: string;
  fullName: string;
  isAccepted: boolean;
  dob: Date;

  constructor() {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.address = '';
    this.fullName = '';
    this.isAccepted = false;
    this.dob = new Date();
    this.dob.setFullYear(this.dob.getFullYear() - 18); // Mac dinh ngay sinh la 18 tuoi
  }

  onPhoneChange(){
    console.log(`Phone: ${this.phone}`);
  }

  register(){
    const message = `
    Phone: ${this.phone},
    Password: ${this.password},
    Address: ${this.address},
    Full name: ${this.fullName},
    isAccepted: ${this.isAccepted}
    date of birth: ${this.dob}
    `;
    alert(message);
  }

}
