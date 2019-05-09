import { Component, OnInit } from '@angular/core';
import { ShipServiceService } from '../../services/ship-service.service';
import { DetailServiceService } from '../../services/detail-service.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-shipping-tab',
  templateUrl: './shipping-tab.component.html',
  styleUrls: ['./shipping-tab.component.css']
})
export class ShippingTabComponent implements OnInit {
  
  detailJSON = null; //returned JSON object
  data = null;
  ship = null;
  item = null;
  error = false;
  show_shipping = false;
  show_info = false;
  subscription: Subscription;

  constructor(private _shipService: ShipServiceService,
              private _detailService: DetailServiceService) {
    this.subscription = this._shipService.getShip().subscribe(
      data => {
        if(data) {
          //console.log("show shipping tab");
          console.log("shipping Success!" + data);
          this.show_shipping = true;
          this.ship = data[0]; 
        } 
        else {
          this.show_shipping = false;
          console.log("no ship");
          this.ship = null;
        }
      });

      this._detailService.getDetail().subscribe(
        data => {
          //console.log(data);
          if(data == "error") {
            this.error = true;
          }
          else {
            //console.log("ship get detail success");
            this.show_shipping = true;
            this.show_info = true;
            this.detailJSON = data;
            this.item = this.detailJSON.Item;
          } 
      });
  }

  ngOnInit() {
  }

}
