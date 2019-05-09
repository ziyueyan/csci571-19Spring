import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { FormData } from '../search-form/formData';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class SearchServiceService {

  private _search_url ='/search';
  private _resultJSON = new Subject<any>();

  private _serach_p = new Subject<any>();
  constructor(private _http: HttpClient) { }

  search(form){
    this._serach_p.next("true");
    var categoryId = '-1';
    var zipcode = form.herezipcode;
    if(form.category === 'Art') categoryId = '550'; 
    else if(form.category === 'Baby') categoryId = '2984'; 
    else if(form.category === 'Books') categoryId = '267'; 
    else if(form.category === 'Clothing' || form.category === 'Shoes & Accessories') categoryId = '11450';
    else if(form.category === 'Computers/Tablets & Networking') categoryId = '58058'; 
    else if(form.category === 'Health & Beauty') categoryId = '26395'; 
    else if(form.category === 'Music') categoryId = '11233'; 
    else if(form.category === 'Video Games & Consoles') categoryId = '1249';
    if(form.locations === 'zip') zipcode = form.zipcode;
    
    let params = new HttpParams()
      .set("keyword", form.keyword)
      .set("category", categoryId)
      .set("zipcode",zipcode)
      .set("miles", form.miles || '10')
      .set("localpickup", form.localpickup)
      .set("freeshipping", form.freeshipping)
      .set("new", form.new)
      .set("used", form.used)
      .set("unspecified", form.unspecified);

    let response = this._http.get<any>(this._search_url, {params: params});
    response.subscribe(
      data => {
        console.log("Search Success!", data);
        this._serach_p.next("false");
        this._resultJSON.next(data);
      },
      error => {
        this._serach_p.next("false");
        this._resultJSON.next("error");
      }
    );
    
  }

  getResult(): Observable<any> {
    return this._resultJSON.asObservable();
  }

  get_p(): Observable<any> {
    return this._serach_p.asObservable();
  }

  getzip(){
    return this._http.get("http://ip-api.com/json");
  }

}
