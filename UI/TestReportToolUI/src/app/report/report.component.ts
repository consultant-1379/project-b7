import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { CNA } from '../interfaces/cna';
import { ENMProject } from '../interfaces/enmproject';
import { JenkinsTestReport } from '../interfaces/jenkins';
import { CnaService } from '../services/cna.service';
import { EnmProjectService } from '../services/enm-project.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  displayedColumns: string[] = ['cxpNumber', 'projectName', 'testName', 'successRate', 'failureRate'];
  dataSource: MatTableDataSource<JenkinsTestReport>;
  filterForm = this.fb.group({
		enmProject: '',
		cnaRepo: ''
	});
  public cnaRepoList: CNA[] = [];
  public enmList: ENMProject[] = [];
  filteredCNAList: Observable<CNA[]> = new Observable<CNA[]>();
	filteredENMProject: Observable<ENMProject[]> = new Observable<ENMProject[]>();
  constructor(private fb: FormBuilder, private enmService: EnmProjectService, private cnaService: CnaService) {
    this.dataSource = new MatTableDataSource<JenkinsTestReport>();
  }

  ngOnInit(): void {
    this.cnaService.getCNAList().subscribe(result => {
		  this.cnaRepoList = result;
		  this.filteredCNAList = this.filterForm.controls.cnaRepo.valueChanges.pipe(
			startWith(''),
			map(value => this._filterCNA(value, this.cnaRepoList))
		  );
	  });
	  this.enmService.getENMProjectList().subscribe(result => {
		  this.enmList = result;
		  this.filteredENMProject = this.filterForm.controls.enmProject.valueChanges.pipe(
			startWith(''),
			map(value => this._filterENM(value, this.enmList))
		  );
	  });

	  this.filterForm.controls.endDate.valueChanges.pipe(map(value => {
      this.updatePage();
    }));
  }

  public updatePage() {
    this.enmService.getHighestFailureData(0.0, this.filterForm.value.enmProject, this.filterForm.value.enmProject).subscribe(result => {
      this.dataSource = new MatTableDataSource(result);
    });
  }
  formatLabel(value: number) {
    return value + ' %';
  }

  private _filterCNA(value: any, options: CNA[]): CNA[] {
		const filterValue = value.toLowerCase();
    this.filterForm.get('enmProject')?.setValue('');
		return options.filter(option => option.cnaName.toLowerCase().includes(filterValue));
	}

	private _filterENM(value: any, options: ENMProject[]): ENMProject[] {
		const filterValue = value.toLowerCase();
    this.updatePage();
    if(this.filterForm.get('cnaRepo')?.value == '') {
      return options.filter(option => option.cxpName.toLowerCase().includes(filterValue));
    }
		return options.filter(option => option.cxpName.toLowerCase().includes(filterValue) && option.cnaName == this.filterForm.get('cnaRepo')?.value);
	}
}
