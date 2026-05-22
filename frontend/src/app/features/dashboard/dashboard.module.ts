import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { dashboardRoutes } from './dashboard.routes';
import { DashboardLayoutComponent } from './layout/dashboard-layout.component';
import { OverviewComponent } from './pages/overview/overview.component';
import { TransferComponent } from './pages/transfer/transfer.component';
import { DepositComponent } from './pages/deposit/deposit.component';
import { WithdrawComponent } from './pages/withdraw/withdraw.component';
import { StatementComponent } from './pages/statement/statement.component';

@NgModule({
  declarations: [
    DashboardLayoutComponent,
    OverviewComponent,
    TransferComponent,
    DepositComponent,
    WithdrawComponent,
    StatementComponent,
  ],
  imports: [CommonModule, ReactiveFormsModule, RouterModule.forChild(dashboardRoutes)],
})
export class DashboardModule {}
