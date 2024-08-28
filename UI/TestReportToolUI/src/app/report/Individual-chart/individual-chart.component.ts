import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-line-chart',
  templateUrl: './individual-chart.html',
  styleUrls: [ './individual-chart.css' ]
})
export class LineChartComponent  {
  chartOptions = {
    responsive: true,
    borederWidth:1
  };

  chartData = [
    { data: [330, 600, 260, 700], borderWidth:1 , lineThickness:1, label: 'Current', fill:false },
    { data: [45, 67, 800, 500], label: 'Selected', fill:false }
  ];

  chartLabels = ['January', 'February', 'Mars', 'April'];

  constructor() { }

  ngOnInit() {
    console.log(this.chartData[0]);
  }
}
