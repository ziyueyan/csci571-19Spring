import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ShipServiceService {
  private _shipJSON = new Subject<any>();

  constructor() { }

  sendShip(ship) {
    //console.log("ship is send to service");
    this._shipJSON.next(ship);
  }

  getShip() : Observable<any>{
    //console.log("send to ship");
    return this._shipJSON.asObservable();
  }


}
