import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { DetailServiceService } from '../services/detail-service.service';
import { ShipServiceService } from '../services/ship-service.service';
import { WishServiceService  } from '../services/wish-service.service';
import { SearchServiceService } from 'app/services/search-service.service';

@Component({
  selector: 'app-wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.css']
})
export class WishListComponent implements OnInit {

  wishJSON = [];
  showWish = false;
  noRecord = true;
  no_detail = true;
  total = 0;
  selected = "";

  @Output() list_change = new EventEmitter<any>();

  constructor(  private _detailService: DetailServiceService,
                private _shipService: ShipServiceService,
                private _wishService: WishServiceService,
                private _searchService: SearchServiceService ) {
    this._searchService.getResult().subscribe(
      data => {
       this.no_detail= true;
    });
    this._detailService.getSelected().subscribe(
      data => {
        if(data) {
          this.selected = data;
          this.no_detail = false;
        }
      }
    );

    this._detailService.get_btn().subscribe(
      data=> {
        if(data == "true") {
          this.no_detail = false;
        }
      }
    );
    this._wishService.getWish().subscribe(
      data => {
        //console.log(data);
        if(data) {
          this.wishJSON = data;
          console.log(this.wishJSON);
          if(this.wishJSON.length != 0) {
            this.noRecord = false;
            this.showWish = true;
            this.total = 0;
            for(var i = 0; i < this.wishJSON.length; i++) {
              this.total += parseFloat(this.wishJSON[i].cost);
            }
          }
          else {
            this.noRecord = true;
          }
        }
        else {
          console.log("no wish");
        } 
    });
    this.wishJSON = this._wishService.getWishArray();
    if(this.wishJSON.length != 0) {
      this.noRecord = false;
      this.showWish = true;
      this.total = 0;
      for(var i = 0; i < this.wishJSON.length; i++) {
        this.total += parseFloat(this.wishJSON[i].cost);
      }
    }
  }

  searchDetail(itemId: string, ship) {
    console.log(itemId);
    //this.router.navigate(['/detail'], {relativeTo: this.route});
    //this.no_detail = false;
    //this.selected = itemId;
    this._detailService.searchDetail(itemId);
    this._detailService.searchSimilar(itemId);
    this._shipService.sendShip(ship);
    this.list_change.emit({changeto: "detail"});
  }

  go_to_detail() {
    if(!this.no_detail){
      //this.router.navigate(['/detail'], {relativeTo: this.route});
      this.list_change.emit({changeto: "detail"});
    }
  }

  removeWish(id: string){
    this._wishService.removeWish(id);
  }
  
  checkWish(id: string) {
    return this._wishService.isWish(id);
  }

  ngOnInit() {
  }

}
