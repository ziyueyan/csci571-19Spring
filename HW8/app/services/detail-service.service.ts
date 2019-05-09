import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DetailServiceService {
  private _detail_url ='/detail';
  private _detailJSON = new Subject<any>();

  private _pohoto_url ='/photo';
  private _photoJSON = new Subject<any>();

  private _similar_url ='/similar';
  private _similarJSON = new Subject<any>();

  private detail_p = new Subject<any>();
  private photo_p = new Subject<any>();
  private similar_p = new Subject<any>();
  private selected = new Subject<any>();;
  btn = new Subject<any>();

  constructor(private _http: HttpClient) { }

  //for info-tab && seller-tab
  searchDetail(itemId) {
    this.detail_p.next("true");
    let params = new HttpParams().set("itemId", itemId);
    let response = this._http.get<any>(this._detail_url, {params: params});
    response.subscribe(
      data => {
        //xc
        this.selected.next(itemId);
        this.detail_p.next("false");
        this._detailJSON.next(data);
        this.btn.next("true");
      },
      error => {
        console.log(error);
        this._detailJSON.next("error");
      }
    );
  }

  getDetail(): Observable<any> {
    return this._detailJSON.asObservable();
  }
  getSelected() : Observable<any> {
    return this.selected.asObservable();
  }
  
  //for photo-tab
  searchPhoto(q: string) {
    this.photo_p.next("true");
    let params = new HttpParams().set("q", q);
    let response = this._http.get<any>(this._pohoto_url, {params: params});
    response.subscribe(
      data => {
        this.photo_p.next("false");
        //console.log("photo Success!", data);
        this._photoJSON.next(data);
      },
      error => {
        console.log(error);
        this._photoJSON.next("error");
      }
    );
  }

  getPhoto(): Observable<any> {
    return this._photoJSON.asObservable();
  }

  //for similar-tab
  searchSimilar(itemId) {
    this.similar_p.next("true");
    let params = new HttpParams().set("itemId", itemId);
    let response = this._http.get<any>(this._similar_url, {params: params});
    response.subscribe(
      data => {
        this.similar_p.next("false");
        //console.log("similar Success!", data);
        this._similarJSON.next(data);
      },
      error => {
        console.log(error);
        this._similarJSON.next("error");
      }
    );
  }

  getSimilar(): Observable<any> {
    return this._similarJSON.asObservable();
  }

  get_d_p(): Observable<any> {
    return this.detail_p.asObservable();
  }
  get_p_p(): Observable<any> {
    return this.photo_p.asObservable();
  }
  get_s_p(): Observable<any> {
    return this.similar_p.asObservable();
  }

  get_btn(): Observable<any> {
    return this.btn.asObservable();
  }





}
