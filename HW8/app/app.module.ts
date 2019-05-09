import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
//import { MatAutocompleteModule, MatInputModule } from '@angular/material';
//import { MatFormFieldModule } from '@angular/material/form-field'; 
//import { RouteReuseStrategy } from '@angular/router';
//import { CustomReuseStrategy } from './CustomReuseStrategy';
import { RoundProgressModule } from 'angular-svg-round-progressbar';

//import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SearchFormComponent } from './search-form/search-form.component';
import { HttpClientModule } from '@angular/common/http';
import { ResultListComponent } from './result-list/result-list.component';
import { WishListComponent } from './wish-list/wish-list.component';
import { DetailPagesComponent } from './detail-pages/detail-pages.component';
import { InfoTabComponent } from './detail-pages/info-tab/info-tab.component';
import { PhotoTabComponent } from './detail-pages/photo-tab/photo-tab.component';
import { ShippingTabComponent } from './detail-pages/shipping-tab/shipping-tab.component';
import { SellerTabComponent } from './detail-pages/seller-tab/seller-tab.component';
import { SimilarTabComponent } from './detail-pages/similar-tab/similar-tab.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { StringPipePipe } from './pipes/string-pipe.pipe';
import { DayPipePipe } from './pipes/day-pipe.pipe';
import { OrderPipePipe } from './pipes/order-pipe.pipe';
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatAutocompleteModule, MatInputModule } from '@angular/material/';
import { MatTooltipModule } from '@angular/material/tooltip'; 


@NgModule({

  declarations: [
    AppComponent,
    SearchFormComponent,
    ResultListComponent,
    WishListComponent,
    DetailPagesComponent,
    InfoTabComponent,
    PhotoTabComponent,
    ShippingTabComponent,
    SellerTabComponent,
    SimilarTabComponent,
    PageNotFoundComponent,
    StringPipePipe,
    DayPipePipe,
    OrderPipePipe
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    //AppRoutingModule,
    HttpClientModule,
    RoundProgressModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    MatInputModule,
    MatTooltipModule
  ],
  providers: [
    //{ provide: RouteReuseStrategy, useClass: CustomReuseStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
