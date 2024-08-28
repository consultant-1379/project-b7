import { Component } from '@angular/core';
import {
    IBarChartOptions,
    IChartistAnimationOptions,
    IChartistData
  } from 'chartist';
  import { ChartEvent, ChartType } from 'ng-chartist';
import { IChartistSettingsType } from 'ngx-chartist';
   
  @Component({
    selector: 'app-bar-chart',
    templateUrl: './bar-chart.html'
  })
  export class BarChartComponent {
    chartOpts: IChartistSettingsType

    constructor() {
      this.chartOpts = {​​
        type:'Bar',
        data: {​​
        labels: ['01/02', '02/02', '03/02', '04/02', '05/02', '06/02', 
  
             '07/02', '08/02', '09/02', '10/02', '11/02', '12/02', '13/02', '14/02'],
  
        series: [[12, 9, 7, 8, 5, 9, 4, 8, 1, 3, 5, 6, 1, 7], [2, 1, 3.5, 9, 7, 8, 5, 9, 4, 8, 1, 3, 7, 3], [1, 3, 4, 5, 6]]
        }​​,
        options: {​​
        chartPadding: {​​ right: 40 }​​, 
        fullWidth: true,
        
        height: 300 
        }​​ 
      }​​
    }
    
    // V Belonged to the tutorial
    type: ChartType = 'Bar';
    data: IChartistData = {
      labels: [
        'Jan',
        'Feb',
        'Mar',
        'Apr',
        'May',
        'Jun',
        'Jul',
        'Aug',
        'Sep',
        'Oct',
        'Nov',
        'Dec'
      ],
      series: [
        [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
        [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
      ]
    };
   
    options: IBarChartOptions = {
      axisX: {
        showGrid: false
      },
      height: 300
    };
    events: ChartEvent = {
      draw: (data: { type: string; element: { animate: (arg0: { y2: IChartistAnimationOptions; }) => void; }; y1: any; y2: any; }) => {
        if (data.type === 'bar') {
          data.element.animate({
            y2: <IChartistAnimationOptions>{
              dur: '0.5s',
              from: data.y1,
              to: data.y2,
              easing: 'easeOutQuad'
            }
          });
        }
      }
    };
  }