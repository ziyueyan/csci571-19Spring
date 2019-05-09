import { Component, OnInit } from '@angular/core';
import { DetailServiceService } from '../../services/detail-service.service';
import { FormData } from '../../search-form/formData';

@Component({
  selector: 'app-photo-tab',
  templateUrl: './photo-tab.component.html',
  styleUrls: ['./photo-tab.component.css']
})
export class PhotoTabComponent implements OnInit {

  detailJSON = null;
  photoJSON = null; //google returned JSON
  photos = null;
  error = false;
  show_photo = false;
  no_photo = false;
  num = 0;

  constructor(private _detailService: DetailServiceService) {
    this._detailService.getDetail().subscribe(
      data => {
        //console.log(data);
        if(!data) {
          console.log("no data");
        }
        console.log("photo Success!", data);
        if(data == "error") {
          this.error = true;
        }
        else {
          this.error = false;
          this.show_photo = true;
          this.detailJSON = data;
          this.searchPhoto(this.detailJSON.Item.Title);
        } 
    });

    this._detailService.getPhoto().subscribe(
      data => {
        //console.log(data);
        if(data == "error") {
          this.error = true;
        }
        else {
          this.error = false;
          this.photoJSON = data;
          if(this.photoJSON.searchInformation.totalResults == '0'){
            this.no_photo = true;
          }
          else {
            this.no_photo = false;
            this.show_photo = true;
            this.photos = this.photoJSON.items;
            this.num = this.photos.length;
            //console.log(this.photos[0].link);
          }
        } 
    });

  }

  searchPhoto(q: string) {
    console.log(q);
    this._detailService.searchPhoto(q);
  }

  open_new_tab(url){
    window.open(url);
  }

  ngOnInit() {

  }

}
