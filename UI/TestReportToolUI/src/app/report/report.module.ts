import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReportComponent } from './report.component';
import { ReportRoutes } from './report.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartistModule } from 'ng-chartist';
import { BrowserModule } from '@angular/platform-browser';
import { LineChartComponent } from './Individual-chart/individual-chart.component';
import { BarChartComponent } from './Bar-chart/bar-chart.component';
import { NgxChartistModule } from 'ngx-chartist';

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    NgxChartistModule,
    ChartistModule,
    RouterModule.forChild(ReportRoutes)
  ],
  declarations: [ReportComponent, LineChartComponent,BarChartComponent]
})
export class ReportModule {}
