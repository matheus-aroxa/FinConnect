import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AccountResponse } from '../../../../core/models/account.models';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { formatCurrency, getErrorMessage } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-withdraw',
  standalone: false,
  templateUrl: './withdraw.component.html',
  styleUrl: './withdraw.component.css',
})
export class WithdrawComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  isLoading = false;
  result: AccountResponse | null = null;
  readonly formatCurrency = formatCurrency;

  form = this.fb.nonNullable.group({
    amount: [0, [Validators.required, Validators.min(0.01)]],
  });

  constructor(
    private accountService: AccountService,
    private auth: AuthService,
    private router: Router,
    private toast: ToastService,
  ) {}

  ngOnInit(): void {
    const cpf = this.auth.getCpf();
    if (!cpf) {
      this.toast.show('warning', 'CPF nao informado', 'Informe o CPF no login.');
      void this.router.navigate(['/auth/login']);
    }
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const cpf = this.auth.getCpf();
    if (!cpf) {
      this.toast.show('warning', 'CPF nao informado', 'Informe o CPF no login.');
      return;
    }

    this.isLoading = true;

    this.accountService
      .debitAccount({ cpf, amount: Number(this.form.getRawValue().amount) })
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response) => {
          this.result = response;
          this.toast.show('success', 'Saque concluido', 'Saldo atualizado.');
        },
        error: (error) => {
          this.toast.show('error', 'Falha no saque', getErrorMessage(error));
        },
      });
  }
}
