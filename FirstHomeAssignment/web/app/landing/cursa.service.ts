import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/Rx';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
 
@Injectable()
export class CursaService {
    private curseUrl = "http://localhost:8080/curse/curses/";

    constructor(private http: Http) {
    }

    getAllCursa(): Promise<Cursa[]> {
        return Promise.resolve(this.http.get(this.curseUrl).map((response) => {
            return response.json()
        })
        .toPromise());
    }

    deleteCursa(id): Promise<void> {
        return Promise.resolve(this.http.delete(this.curseUrl+id).map((response) => {
            //return response;
        })
        .toPromise());
    }

    addCursa(body: string): Promise<Cursa> {
        console.log(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return Promise.resolve(this.http.post(this.curseUrl, body, options).map((response : Response) => {
            return response.json();
        })
        .toPromise());
    }

    editCursa(id, body): Promise<Cursa> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return Promise.resolve(this.http.put(this.curseUrl+id, body, options).map((response : Response) => {
            return response.json();
        })
        .toPromise());
    }
}