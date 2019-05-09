import { Component, OnInit } from '@angular/core';
import { DetailServiceService } from '../../services/detail-service.service';

@Component({
  selector: 'app-similar-tab',
  templateUrl: './similar-tab.component.html',
  styleUrls: ['./similar-tab.component.css']
})
export class SimilarTabComponent implements OnInit {

  similarJSON = null; //google returned JSON
  items; //array of similar items
  error = false;
  show_similar = false;
  noRecord = false;
  num: number;
  limit: number;
  by = "Default";
  order = "Ascending";

  constructor(private _detailService: DetailServiceService) { 
    
    this._detailService.getSimilar().subscribe(
      data => {
        //console.log(data);
        this.by = "Default";
        this.order = "Ascending";
        if(data == undefined) {
          console.log("no similar");
          this.noRecord = false;
        }
        else if(data == "error") {
          this.error = true;
          this.noRecord = false;
        }
        else {
          //console.log("show similar tab");
          console.log("similar Success!", data);
          this.noRecord = false;
          this.error = false;
          this.show_similar = true;
          this.similarJSON = data;
          this.num = this.similarJSON.getSimilarItemsResponse.itemRecommendations.item.length
          if(this.num == 0){
            this.noRecord = true;
          }
          else {
            this.items = this.similarJSON.getSimilarItemsResponse.itemRecommendations.item;
            this.limit = this.num > 5 ? 5 : this.num;
          }
        } 
    });
  }

  showMore() {
    this.limit = this.num;
  }

  showLess(){
    this.limit = 5;
  }

  byChange(by){
    this.by = by;
  }
  orderChange(order){
    this.order = order;
  }

  ngOnInit() {
  }

}
