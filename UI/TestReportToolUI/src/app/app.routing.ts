import { Routes } from '@angular/router';
import { AlertsComponent } from './alerts/alerts.component';
import { NumberStatsComponent } from './dashboard/dashboard-components/number-stats/number-stats.component';

import { FullComponent } from './layouts/full/full.component';

export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
      },
      {
        path: '',
        loadChildren:
          () => import('./material-component/material.module').then(m => m.MaterialComponentsModule)
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule)
      },
      {
        path: 'reports',
        loadChildren: () => import('./report/report.module').then(m => m.ReportModule)
      },
      {
        path: 'alerts',
        component: AlertsComponent
      }

    ]
  }
];
