import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { SearchServiceService } from '../services/search-service.service';
import { PageServiceService } from '../services/page-service.service';
import { DetailServiceService } from '../services/detail-service.service';
//import { Router, ActivatedRoute } from '@angular/router';
import { ShipServiceService } from '../services/ship-service.service';
import { WishServiceService  } from '../services/wish-service.service';


@Component({
  selector: 'app-result-list',
  templateUrl: './result-list.component.html',
  styleUrls: ['./result-list.component.css']
})

export class ResultListComponent implements OnInit {

  resultJSON = null; //returned JSON object
  searchResult = null;
  items = null; //item lists
  pager: any = {};
  pagedItems: any[];
  selected = "";

  error = false; //if error returned
  noRecord = false;  //if searchResult.count == 0
  showResult = false;
  showProgess = false;
  public no_detail= true;

  @Output() list_change = new EventEmitter<any>();
  constructor(//private router: Router,
             // private route: ActivatedRoute,
              private _searchService: SearchServiceService, 
              private _pageService: PageServiceService,
              private _detailService: DetailServiceService,
              private _shipService: ShipServiceService,
              private _wishService: WishServiceService ) {
    this._detailService.getSelected().subscribe(
      data => {
        if(data) {
          this.selected = data;
          this.no_detail = false;
        }
      }
    );

    this._detailService.get_btn().subscribe(
      data => {
        if(data == "true") {
          this.no_detail = false;
        }
      }
    );

    this._searchService.get_p().subscribe(
      data => {
        if(data == "true") {
          this.showProgess = true;
          this.showResult = false;
        }
      }
    );
    this._searchService.getResult().subscribe(
      data => {
        //console.log(data);
        if(data == "error") {
          this.showProgess = false;
          this.error = true;
          this.no_detail= true;
          this.noRecord = false;
          this.showResult = false;
        }
        else {
          this.resultJSON = data;
          this.showProgess = false;
          this.searchResult = this.resultJSON.findItemsAdvancedResponse[0].searchResult[0];
          this.selected = "";
          if(this.searchResult["@count"] == "0") {
            this.no_detail= true;
            this.noRecord = true;
            this.error = false;
            this.showResult = false;
          }
          else {
            this.error = false; 
            this.noRecord = false; 
            this.showResult = true;
            this.no_detail = true;
            this.items = this.searchResult.item;
            this.setPage(1);
          }
        } 
    });
  }

  setPage(page: number) {
    this.pager = this._pageService.getPager(this.items.length, page, 10);
    this.pagedItems = this.items.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }

  searchDetail(itemId: string, ship) {
    console.log(itemId);
    //this.selected = itemId;
    //this.router.navigate(['/detail'], {relativeTo: this.route});
    this.no_detail = false;
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

  addWish(id: string, photo: string, title: string, cost:string, shiping:string, seller:string,shippingInfo:object) {
    this._wishService.addWish(id, photo, title, cost, shiping, seller, shippingInfo);
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
