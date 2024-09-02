import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HomeComponent} from './home/home.component';
import {NgOptimizedImage} from "@angular/common";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {OrderComponent} from './order/order.component';
import {DetailProductComponent} from './detail-product/detail-product.component';
import {OrderCofirmComponent} from './order-cofirm/order-cofirm.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

// import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    OrderComponent,
    DetailProductComponent,
    OrderCofirmComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    NgOptimizedImage,
    HttpClientModule,
    RouterModule
  ],
  providers: [],
  // test man hinh chinh o day, comment cac component lai
  bootstrap: [
    //HomeComponent
    //OrderComponent
    //OrderCofirmComponent
    RegisterComponent
    //LoginComponent

    // DetailProductComponent
  ]
})
export class AppModule {
}
