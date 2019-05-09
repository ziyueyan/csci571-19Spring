import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { DetailServiceService } from '../services/detail-service.service';
import { WishServiceService  } from '../services/wish-service.service';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { ShipServiceService } from '../services/ship-service.service';
//import { Router, ActivatedRoute } from '@angular/router';
import { FadeAnimation } from '../animation/fadeIn'; 
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-detail-pages',
  templateUrl: './detail-pages.component.html',
  styleUrls: ['./detail-pages.component.css'],
  animations: [
    FadeAnimation
  ]
})
export class DetailPagesComponent implements OnInit {

  detailJSON = null; //returned JSON object
  Item = null;
  error = false; //if error returned
  showDetail = false;
  showProgess = false;
  showTitle = false;
  hideTab = true;
  active_tab = 1;
  d_p = true;
  p_p = true;
  s_p = true;
  ship = null;

  fb_href= "";
  fb_title = "";
  fb_price = ""
  private _fb_url;
  subscription: Subscription;

  @Output() list_change = new EventEmitter<any>();
  
  constructor(private _detailService: DetailServiceService,
              private _http: HttpClient,
              private _wishService: WishServiceService,
              private _shipService: ShipServiceService
              //private router: Router,
              //private route: ActivatedRoute,
              ) {
    this._detailService.get_d_p().subscribe(
      data=>{
        if(data == "true") {
          this.d_p = true;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);
        }
        else {
          this.d_p = false;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);   
        }
    });
    this._detailService.get_p_p().subscribe(
      data=>{
        if(data == "true") {
          this.p_p = true;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);
        }
        else {
          this.p_p = false;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);
        }
    });
    this._detailService.get_s_p().subscribe(
      data=>{
        if(data == "true") {
          this.s_p = true;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);
        }
        else {
          this.s_p = false;
          this.showProgess = this.d_p || this.p_p || this.s_p;
          this.showDetail = !this.showProgess;
          console.log("show Detail" + this.showDetail);
        }
    });

    this._detailService.getDetail().subscribe(
      data => {
        this.hideTab = false;
        //console.log(data);
        if(data == "error") {
          this.error = true;
          this.showTitle = false;
        }
        else {
          this.error = false;
          this.showTitle = true;
          this.detailJSON = data;
          this.Item = this.detailJSON.Item;
          this.fb_href = encodeURIComponent(" " + this.Item.ViewItemURLForNaturalSearch+ " ");
          this.fb_title = encodeURIComponent(" " + this.Item.Title + " ");
          this.fb_price = encodeURIComponent(" $" + this.Item.CurrentPrice.Value + " ");
          console.log(this.fb_href);
        } 
    });
    this.subscription = this._shipService.getShip().subscribe(
      data => {
        if(data) {
          //console.log("show shipping tab");
          console.log(data);
          this.ship = data[0]; 
        } 
        else {
          console.log("no ship");
          this.ship = null;
        }
      });
  }

  back_to_list(){
    //this.router.navigate(['../result'], {relativeTo: this.route});
    this.list_change.emit({changeto: "list"});
    this.setActive(1);
  }

  fb_share(){
    var url ="https://www.facebook.com/sharer/sharer.php?u="+this.fb_href+"&quote=Buy"+this.fb_title +"at" +this.fb_price +"from%20link%20below";
    window.open(url);
  }

  setActive(tab_id:number) {
    this.active_tab = tab_id;
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
