import { Component, OnInit } from '@angular/core';
import { DetailServiceService } from '../../services/detail-service.service';

@Component({
  selector: 'app-info-tab',
  templateUrl: './info-tab.component.html',
  styleUrls: ['./info-tab.component.css']
})
export class InfoTabComponent implements OnInit {

  detailJSON = null; //returned JSON object
  item = null;
  pictures = null;
  error = false;
  show_info = false;
  morethanone = true;

  constructor(private _detailService: DetailServiceService) {
    this._detailService.getDetail().subscribe(
      data => {
        //console.log(data);
        if(data == "error") {
          this.error = true;
        }
        else {
          //console.log("show info tab");
          console.log("detail Success!", data);
          this.error = false;
          this.show_info = true;
          this.detailJSON = data;
          this.item = this.detailJSON.Item;
          this.pictures = this.item.PictureURL;
          if(this.pictures.length == 1) {
            this.morethanone=false;
          }
          else {
            this.morethanone=true;
          }
        } 
    });
  }

  open_new_tab(url){
    window.open(url);
  }

  ngOnInit() {
  }

}
