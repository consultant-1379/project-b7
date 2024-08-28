import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CNA } from '../interfaces/cna';

@Injectable({
  providedIn: 'root'
})
export class CnaService {

  constructor(private http: HttpClient) { }

  getCNAList(): Observable<CNA[]> {
    return this.http.get<CNA[]>(environment.api_url + '/getCnaNames');
  }
}
