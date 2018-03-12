import { Injectable } from '@angular/core';
import {Http, RequestMethod, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TacheService {  requestOptions;
  headers = new Headers();
  private baseUrl: string = 'http://localhost:5678/task';
  constructor(private http: Http){}
  addData(data): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Post,
      url: this.baseUrl,
      headers: this.headers,
      body: data
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res;
      })
      .catch(this.handleError);
  };
  getDataById(id): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Get,
      url: this.baseUrl+id,
      headers: this.headers
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res.json();
      })
      .catch(this.handleError);
  };
  getAllData(): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Get,
      url: this.baseUrl,
      headers: this.headers
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res.json();
      })
      .catch(this.handleError);
  };
  getAllDataPage(page, offset, filter): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Get,
      url: this.baseUrl+'datatable/'+'?offset='+(page - 1)*offset+'&limit='+offset+'&filter='+filter,
      headers: this.headers
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res.json();
      })
      .catch(this.handleError);
  };

  updateById(id, data): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Put,
      url: this.baseUrl+id,
      headers: this.headers,
      body: data
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res.json();
      })
      .catch(this.handleError);
  };
  deleteDataById(id): Observable<any>{
    this.headers = new Headers();
    if(JSON.parse(localStorage.getItem('currentUser'))){      this.headers.append("x-auth", JSON.parse(localStorage.getItem('currentUser')).token);    }
    this.requestOptions = new RequestOptions({
      method: RequestMethod.Delete,
      url: this.baseUrl+id,
      headers: this.headers
    })
    return this.http.request(new Request(this.requestOptions))
      .map((res:Response) => {
        return res.json();
      })
      .catch(this.handleError);
  };

  handleError(error: any) {
    console.error('server error:', error);
    if (error instanceof Response) {
      let errMessage = '';
      try {
        errMessage = error.json().error;
      } catch(err) {
        errMessage = error.statusText;
      }
      return Observable.throw(errMessage);
    }
    return Observable.throw(error);
  }
}


