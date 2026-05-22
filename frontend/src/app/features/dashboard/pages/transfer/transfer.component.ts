import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AuthService } from '../../../../core/services/auth.service';
import { normalizeCpf } from '../../../../core/utils/cpf';
import { getErrorMessage } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-transfer',
  standalone: false,
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css',
})
export class TransferComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  originCpf: string | null = null;
  isLoading = false;

  form = this.fb.nonNullable.group({
    destinationCpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
    amount: [0, [Validators.required, Validators.min(0.01)]],
  });

  constructor(
    private transactionService: TransactionService,
    private auth: AuthService,
    private router: Router,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    this.originCpf = this.auth.getCpf();
    if (!this.originCpf) {
      this.toast.show('warning', 'CPF nao informado', 'Informe o CPF no login.');
      void this.router.navigate(['/auth/login']);
    }
  }

  submit(): void {
    if (this.form.invalid || !this.originCpf) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();
    const destinationCpf = normalizeCpf(raw.destinationCpf);

    if (destinationCpf.length !== 11) {
      this.toast.show('warning', 'CPF invalido', 'Use 11 digitos.');
      return;
    }

    if (destinationCpf === this.originCpf) {
      this.toast.show('warning', 'CPF igual ao destino', 'Informe outra conta.');
      return;
    }

    this.isLoading = true;

    this.transactionService
      .transfer({
        origin: this.originCpf,
        destination: destinationCpf,
        amount: Number(raw.amount),
      })
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (message) => {
          if (message.toLowerCase().includes('failed')) {
            this.toast.show('error', 'Transferencia falhou', 'Verifique os dados.');
            return;
          }

          this.toast.show(
            'success',
            'Transferencia concluida',
            'Emails enviados para origem e destino.'
          );
          this.form.reset({ destinationCpf: '', amount: 0 });
        },
        error: (error) => {
          this.toast.show('error', 'Falha na transferencia', getErrorMessage(error));
        },
      });
  }
}
