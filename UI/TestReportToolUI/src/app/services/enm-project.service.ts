import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TestAlertReport } from '../interfaces/alerts-data';
import { ENMProject } from '../interfaces/enmproject';
import { JenkinsTestReport } from '../interfaces/jenkins';

@Injectable({
  providedIn: 'root'
})
export class EnmProjectService {

  constructor(private http: HttpClient) { }

  getENMProjectList(): Observable<ENMProject[]> {
    return this.http.get<ENMProject[]>(environment.api_url + '/enmProjectsList');
  }

  getSummaryReportData(startDate: number, endDate: number, product: string, cna: string): Observable<JenkinsTestReport[]> {
    let data = {'startDate': startDate, 'endDate': endDate, 'product': product, 'cna': cna};
    return this.http.get<JenkinsTestReport[]>(
      environment.api_url + '/getReport?startDate=' + startDate + '&endDate=' + endDate + '&product=' + encodeURIComponent(product) + '&cna=' + encodeURIComponent(cna)
    );
  }

  getHighestFailureData(threshold: number = 0.0, product: string, cna: string): Observable<JenkinsTestReport[]> {
    return this.http.get<JenkinsTestReport[]>(environment.api_url + '/getHighestFailureRate?threshold=' + threshold.toString() + '&product=' + encodeURIComponent(product) + '&cna=' + encodeURIComponent(cna));
  }
  getTestAlertData(noOfDays: number = 7): Observable<TestAlertReport[]> {
    return this.http.get<TestAlertReport[]>(environment.api_url + '/getTestAlerts?noOfDays=' + noOfDays)
  }
}
