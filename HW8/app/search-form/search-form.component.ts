import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { FormData } from './formData';
import { SearchServiceService } from '../services/search-service.service';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { slideAnimation } from '../animation/animations';
import { slideAnimation2 } from '../animation/animations2';
import * as $ from 'jquery';

//import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css'],
  animations: [
    slideAnimation,
    slideAnimation2
  ]
  
})

export class SearchFormComponent implements OnInit {

  @ViewChild('searchForm') SearchForm;
  public submittest = "";
  public form;
  public active_list = 1;
  public zipcode = "";
  showResult = false;
  showWish = false;
  showDetail = false;
  showZip = false;
  show='list';
  clear = false;
  options=[];
  zipJSON = null;
  allowZip = false;

  private _zip_url ='/zip';

  constructor(
        //private router: Router,private route: ActivatedRoute, 
        private _searchService: SearchServiceService,
        private _http: HttpClient) { 
      this.form = {
        keyword: '',
        category: 'All Categories',
        new: false,
        used: false,
        unspecified: false,
        localpickup: false,
        freeshipping: false,
        miles: '',
        locations: 'here',
        herezipcode: '',
        zipcode: ''
      }
  }

  get_zip() {
    this._searchService.getzip().subscribe(
      data => {
        console.log("zipcode:" + data['zip']),
        this.form.herezipcode = data['zip'];
        this.zipcode = this.form.herezipcode;
        this.allowZip = true;
    });
  }

  ngOnInit() {
    this.get_zip();
  }

  form_Submit(){
    //console.log(this.form);
    this.submittest = "submitted";
    this._searchService.search(this.form);
    this.show_Result();
    this.setActive(1);
    //this.router.navigate(['/result'], {relativeTo: this.route});
  }

  change_to_Detail(event) {
    // list => detail
    this.clear = false;
    this.show = event.changeto;
  }

  change_to_List(event) {
    this.clear = false;
    this.show = event.changeto;
  }

  show_Result() {
    this.clear = false;
    this.show = 'list';
    this.showResult = true;
    this.showWish = false;
  }

  show_Wish() {
    this.clear = false;
    this.show = 'list';
    this.showWish = true;
    this.showResult = false;
  }

  setActive(list_id:number) {
    this.active_list = list_id;
    this.show = 'list';
  }

  clear_Result(){
    //this.router.navigate(['/home'], {relativeTo: this.route});
    console.log("resting data");
    $('#search_form').trigger("reset");
    this.clear = true;
    this.form.herezipcode = this.zipcode;
    console.log(this.zipcode);
    this.setActive(1);
  }

  autoZip(zip){
    let params = new HttpParams().set("zip", zip);
    let response = this._http.get<any>(this._zip_url, {params: params});
    response.subscribe(
      data => {
        console.log("Zip Success!", data);
        this.options = data;
        this.showZip = true;
      },
      error => {
        console.log("Zip error", error);
        this.showZip = false;
      }
    );
  }

}

