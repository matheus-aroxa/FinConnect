import { Routes } from '@angular/router';
import { DashboardLayoutComponent } from './layout/dashboard-layout.component';
import { DepositComponent } from './pages/deposit/deposit.component';
import { OverviewComponent } from './pages/overview/overview.component';
import { StatementComponent } from './pages/statement/statement.component';
import { TransferComponent } from './pages/transfer/transfer.component';
import { WithdrawComponent } from './pages/withdraw/withdraw.component';

export const dashboardRoutes: Routes = [
  {
    path: '',
    component: DashboardLayoutComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'overview' },
      { path: 'overview', component: OverviewComponent },
      { path: 'transfer', component: TransferComponent },
      { path: 'deposit', component: DepositComponent },
      { path: 'withdraw', component: WithdrawComponent },
      { path: 'statement', component: StatementComponent },
    ],
  },
];
