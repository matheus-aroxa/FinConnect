import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AccountInfoResponse } from '../../../../core/models/account.models';
import { Transaction, TransactionType } from '../../../../core/models/transaction.models';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { TransactionService } from '../../../../core/services/transaction.service';
import { formatCurrency, formatDateTime, getErrorMessage } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-overview',
  standalone: false,
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css',
})
export class OverviewComponent implements OnInit {
  accountInfo: AccountInfoResponse | null = null;
  transactions: Transaction[] = [];
  isLoading = false;
  isStatementLoading = false;

  readonly formatCurrency = formatCurrency;
  readonly formatDateTime = formatDateTime;

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService,
    private auth: AuthService,
    private router: Router,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    const cpf = this.auth.getCpf();
    if (!cpf) {
      this.toast.show('warning', 'CPF nao informado', 'Informe o CPF no login.');
      void this.router.navigate(['/auth/login']);
      return;
    }

    this.loadAccount(cpf);
    this.loadStatement(cpf);
  }

  typeLabel(type: TransactionType): string {
    switch (type) {
      case 'DEPOSIT':
        return 'Deposito';
      case 'WITHDRAW':
        return 'Saque';
      default:
        return 'Transferencia';
    }
  }

  private loadAccount(cpf: string): void {
    this.isLoading = true;

    this.accountService
      .getAccountInfo(cpf)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response) => {
          this.accountInfo = response;
        },
        error: (error) => {
          this.toast.show('error', 'Falha ao carregar conta', getErrorMessage(error));
        },
      });
  }

  private loadStatement(cpf: string): void {
    this.isStatementLoading = true;

    this.transactionService
      .getStatement(cpf)
      .pipe(finalize(() => (this.isStatementLoading = false)))
      .subscribe({
        next: (response) => {
          this.transactions = response.transactions.slice(0, 5);
        },
        error: (error) => {
          this.toast.show('error', 'Falha ao carregar extrato', getErrorMessage(error));
        },
      });
  }
}
