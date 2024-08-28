import { Component, AfterViewInit, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { NgxChartistService} from 'ngx-chartist';
import { IChartistSettingsType} from 'ngx-chartist';
import { EnmProjectService } from '../services/enm-project.service';
import { ENMProject } from '../interfaces/enmproject';
import { CNA } from '../interfaces/cna';
import { CnaService } from '../services/cna.service';
import { stringify } from '@angular/compiler/src/util';
import { JenkinsTestReport } from '../interfaces/jenkins';
import { removeSummaryDuplicates } from '@angular/compiler';
import { GraphPoint } from '../interfaces/graph';
import { GraphMap } from '../classes/map';

@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit, OnInit {
	summaryGraphGroup = this.fb.group({
		startDate: new Date(new Date().setDate(new Date().getDate()-14)),
		endDate: new Date(),
		enmProject: '',
		cnaRepo: ''
	});
	public cnaRepoList: CNA[] = [];
  public enmList: ENMProject[] = [];
  public testsPassed: number = 0;
  public testsFailed: number = 0;


	filteredCNAList: Observable<CNA[]> = new Observable<CNA[]>();
	filteredENMProject: Observable<ENMProject[]> = new Observable<ENMProject[]>();
	chartOpts: IChartistSettingsType = {
    data: {
    labels: [],
    series: []
    },
    options: {}
  };

	ngOnInit() {
    this.cnaService.getCNAList().subscribe(result => {
		  this.cnaRepoList = result;
		  this.filteredCNAList = this.summaryGraphGroup.controls.cnaRepo.valueChanges.pipe(
			startWith(''),
			map(value => this._filterCNA(value, this.cnaRepoList))
		  );
	  });
	  this.enmService.getENMProjectList().subscribe(result => {
		  this.enmList = result;
		  this.filteredENMProject = this.summaryGraphGroup.controls.enmProject.valueChanges.pipe(
			startWith(''),
			map(value => this._filterENM(value, this.enmList))
		  );
	  });

	  this.summaryGraphGroup.controls.endDate.valueChanges.pipe(map(value => {
      this.updateGraph();
    }));
	}

	constructor(private fb: FormBuilder, private enmService: EnmProjectService, private cnaService: CnaService,
    private chartistService: NgxChartistService) {
		const today = new Date();
		const month = today.getMonth();
		const year = today.getFullYear();


    this.updateGraph();
	}
	ngAfterViewInit() { }

  private _filterCNA(value: any, options: CNA[]): CNA[] {
		const filterValue = value.toLowerCase();
    this.summaryGraphGroup.get('enmProject')?.setValue('');
		return options.filter(option => option.cnaName.toLowerCase().includes(filterValue));
	}

	private _filterENM(value: any, options: ENMProject[]): ENMProject[] {
		const filterValue = value.toLowerCase();
    if(this.summaryGraphGroup.get('cnaRepo')?.value == '') {
      return options.filter(option => option.cxpName.toLowerCase().includes(filterValue));
    }
		return options.filter(option => option.cxpName.toLowerCase().includes(filterValue) && option.cnaName == this.summaryGraphGroup.get('cnaRepo')?.value);
	}

  public dateChanged(endDateVal: string) {
    if(endDateVal) {
      this.updateGraph();
    }
  }
	public updateGraph() {
    let startDate = new Date(this.summaryGraphGroup.get('startDate')?.value);
    let endDate = new Date(this.summaryGraphGroup.get('endDate')?.value);
    let data: JenkinsTestReport[] = [];
    let labelList: string[] = this.generateGraphLabels(this.getDatesBetween(startDate, endDate));
    let seriesMap = new GraphMap();
    let totalTestSeries: number[] = [];
    labelList.forEach(item => {
      seriesMap.add(item, {totalTests: 0, testsPassed: 0, testsFailed: 0, testsSkipped: 0});
      totalTestSeries.push(seriesMap.get(item).totalTests);
    });




    this.enmService.getSummaryReportData(startDate.getTime(), endDate.getTime(), this.summaryGraphGroup.value.enmProject, this.summaryGraphGroup.value.cnaRepo).subscribe(result => {
      result.sort((n1, n2) => Number(n2.buildDateTimestamp) - Number(n1.buildDateTimestamp));
      this.testsPassed = 0;
      this.testsFailed = 0;
      result.forEach(dataPiece => {
        let total: number = dataPiece.testsPassed + dataPiece.testsFailed + dataPiece.testsSkipped;
        this.testsPassed +=dataPiece.testsPassed;
        this.testsFailed +=dataPiece.testsFailed;
        seriesMap.update(this.millisecondsToPlainDateString(Number(dataPiece.buildDateTimestamp)), {totalTests: total, testsPassed: dataPiece.testsPassed, testsFailed: dataPiece.testsFailed, testsSkipped: dataPiece.testsSkipped});
      });
      totalTestSeries = [];


      this.chartOpts = {
        data: {
        labels: labelList,
        series: [
          {name: 'total', data: labelList.map(value => seriesMap.get(value).totalTests)},
          {name: 'passed', data: labelList.map(value => seriesMap.get(value).testsPassed)},
          {name: 'fail', data: labelList.map(value => seriesMap.get(value).testsFailed)}
          ]
        },
        options: {
          fullWidth: true,
          series: {
            'total': {
              lineSmooth: this.chartistService.getInterpolation()['simple']()
            },
            'passed': {
              lineSmooth: this.chartistService.getInterpolation()['simple'](),

            },
            'failed': {
              showPoint: false
            }
          }
        },
        events: {
          draw: function (data) {
            if (data.type === 'line' || data.type === 'area') {
              data.element.animate({
                d: {
                  begin: 2000 * data.index,
                  dur: 2000,
                  from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                  to: data.path.clone().stringify()
                }
              });
            }
          }
        }
      };
    })
	}

  generateGraphLabels(dates: Date[]): string[] {
    return dates.map(val => {
      return String(val.getUTCDate() + '/' + (val.getUTCMonth() + 1))
    });
  }

  millisecondsToPlainDateString(milliseconds: number): string {
    let d = new Date(0);
    d.setUTCSeconds(milliseconds);
    return  String(d.getUTCDate() + '/' + (d.getUTCMonth() + 1))
  }
  getDatesBetween(start: Date, end: Date): Date[] {
      let dates: Date[] = []
      //to avoid modifying the original date
      const theDate = new Date(start)
      while (theDate < end) {
        dates = [...dates, new Date(theDate)]
        theDate.setDate(theDate.getDate() + 1)
      }
      dates = [...dates, end]
      return dates
  }

}
