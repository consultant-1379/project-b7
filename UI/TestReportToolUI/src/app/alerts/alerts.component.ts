import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AlertsData, TestAlertReport } from '../interfaces/alerts-data';
import { CNA } from '../interfaces/cna';
import { ENMProject } from '../interfaces/enmproject';
import { CnaService } from '../services/cna.service';
import { EnmProjectService } from '../services/enm-project.service';

@Component({
  selector: 'app-alerts',
  templateUrl: './alerts.component.html',
  styleUrls: ['./alerts.component.css']
})
export class AlertsComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['cnaName', 'projectName', 'cxpNumber', 'buildNumber', 'buildReleaseType', 'buildDateTimestamp'];
  dataSource: MatTableDataSource<TestAlertReport>;
  numDays: number = 7;

  constructor(private fb: FormBuilder, private enmService: EnmProjectService, private cnaService: CnaService) {
    this.dataSource = new MatTableDataSource<TestAlertReport>();
  }

  ngOnInit(): void {
    this.updatePage();
  }

  getFormattedTableDate(epochMilliSecTime: number): string {
    let d = new Date(0);
    d.setUTCSeconds(epochMilliSecTime);
    let daysAgo = (new Date().getTime() - d.getTime()) / (1000 * 3600 * 24);
    return  String(d.getUTCDate() + '/' + (d.getUTCMonth() + 1))
  }

  public updatePage() {
    this.enmService.getTestAlertData(this.numDays).subscribe(result => {
      result.sort((n1, n2) => Number(n2.buildDateTimestamp) - Number(n1.buildDateTimestamp));
        this.dataSource = new MatTableDataSource(result);
    });
  }
  ngAfterViewInit() {
  }

}
