import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ResultListComponent } from './result-list/result-list.component';
import { WishListComponent } from './wish-list/wish-list.component';
import { DetailPagesComponent } from './detail-pages/detail-pages.component';
import { InfoTabComponent } from './detail-pages/info-tab/info-tab.component';
import { PhotoTabComponent } from './detail-pages/photo-tab/photo-tab.component';
import { SellerTabComponent } from './detail-pages/seller-tab/seller-tab.component';
import { ShippingTabComponent } from './detail-pages/shipping-tab/shipping-tab.component';
import { SimilarTabComponent } from './detail-pages/similar-tab/similar-tab.component';

import { from } from 'rxjs';
const routes: Routes = [
  /*{path: '', redirectTo: 'detail', pathMatch: 'full' },
  {path: 'home', component: PageNotFoundComponent},
  {path: 'result', component: ResultListComponent, data: {animation : 'resultPage'}},
  {path: 'detail', component: DetailPagesComponent, data: {animation : 'detailPage'}},
  {path: 'wishlist', component: WishListComponent, data: {animation : 'wishPage'}},
  {path: '**', redirectTo: 'detail', pathMatch: 'full'}
  /*, children: [
      {path: 'info', component: InfoTabComponent},
      {path: 'photo', component: PhotoTabComponent},
      {path: 'shipping', component: ShippingTabComponent},
      {path: 'seller', component: SellerTabComponent},
      {path: 'similar', component: SimilarTabComponent}
    ]*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponemts = [ResultListComponent, 
                                  WishListComponent, 
                                  DetailPagesComponent, 
                                  InfoTabComponent,
                                  PhotoTabComponent,
                                  SellerTabComponent,
                                  ShippingTabComponent,
                                  SimilarTabComponent];
