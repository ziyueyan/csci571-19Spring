import { Component, OnInit } from '@angular/core';
import { DetailServiceService } from '../../services/detail-service.service';
import {RoundProgressModule} from 'angular-svg-round-progressbar';

@Component({
  selector: 'app-seller-tab',
  templateUrl: './seller-tab.component.html',
  styleUrls: ['./seller-tab.component.css']
})
export class SellerTabComponent implements OnInit {

  detailJSON = null; //returned JSON object
  item = null;
  seller = null;
  error = false;
  show_seller = false;
  title = "";

  constructor(private _detailService: DetailServiceService) {
    this._detailService.getDetail().subscribe(
      data => {
        if(data == "error") {
          this.error = true;
        }
        else {
          console.log("seller Success!", data);
          this.error = false;
          this.show_seller = true;
          console.log(this.show_seller);
          this.detailJSON = data;
          this.item = this.detailJSON.Item;
          this.seller = this.item.Seller;
          this.title = this.seller.UserID.replace(/\s/g, "");
        } 
    });
  }
  
  ngOnInit() {
    //console.log("seller init");
    /*this.detailJSON = this._detailService.returnJSON();
    if (!this.detailJSON) {
      console.log('zdjls');
      console.log(this.detailJSON);
      this.item = this.detailJSON.Item;
      this.seller = this.item.Seller;
      //this.show_seller = true;
    }*/
  }

}
