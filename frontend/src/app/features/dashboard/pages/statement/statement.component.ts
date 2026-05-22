import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import {
  Transaction,
  TransactionStatus,
  TransactionType,
} from '../../../../core/models/transaction.models';
import { AuthService } from '../../../../core/services/auth.service';
import { TransactionService } from '../../../../core/services/transaction.service';
import { formatCurrency, formatDateTime, getErrorMessage } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-statement',
  standalone: false,
  templateUrl: './statement.component.html',
  styleUrl: './statement.component.css',
})
export class StatementComponent implements OnInit {
  transactions: Transaction[] = [];
  selected: Transaction | null = null;
  isLoading = false;

  readonly formatCurrency = formatCurrency;
  readonly formatDateTime = formatDateTime;

  constructor(
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

    this.isLoading = true;
    this.transactionService
      .getStatement(cpf)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response) => {
          this.transactions = response.transactions;
          this.selected = this.transactions[0] ?? null;
        },
        error: (error) => {
          this.toast.show('error', 'Falha ao carregar extrato', getErrorMessage(error));
        },
      });
  }

  select(transaction: Transaction): void {
    this.selected = transaction;
  }

  trackById(_index: number, transaction: Transaction): string {
    return transaction.id;
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

  statusLabel(status: TransactionStatus): string {
    return status === 'COMPLETED' ? 'Concluida' : 'Falhou';
  }
}
