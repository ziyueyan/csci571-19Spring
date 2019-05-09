import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { stringify } from '@angular/compiler/src/util';

@Injectable({
  providedIn: 'root'
})
export class WishServiceService {

  private _wishList = new Subject<any>();
  wishlistarray = [];
  constructor() { }

  addWish(id: string, photo: string, title: string, cost:string, shiping:string, seller:string, shippingInfo:object) {
    let wishItem = {
      id: id,
      photo: photo,
      title: title,
      cost: cost,
      shiping: shiping,
      seller: seller,
      shippingInfo: shippingInfo
    }
    localStorage.setItem(id,JSON.stringify(wishItem));
    this.wishListChange();
  }

  removeWish(id: string) {
    localStorage.removeItem(id);
    this.wishListChange();
  }

  wishListChange(){
    this.wishlistarray = new Array(localStorage.length);
    for(var i = 0; i < localStorage.length; i++) {
      this.wishlistarray[i] = JSON.parse (localStorage.getItem(localStorage.key(i)));
    }
    this._wishList.next(this.wishlistarray);
  }

  getWish() {
    return this._wishList.asObservable();
  }

  getWishArray() {
    this.wishListChange();
    return this.wishlistarray;
  }

  isWish(id: string) {
    if(localStorage.getItem(id)) {
      return true;
    }
    else {
      return false;
    }
  }
}
