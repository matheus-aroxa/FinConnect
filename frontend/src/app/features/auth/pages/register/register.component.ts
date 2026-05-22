import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service';
import { SignUpAccountResponse } from '../../../../core/models/auth.models';
import { normalizeCpf } from '../../../../core/utils/cpf';
import { getErrorMessage, formatCurrency } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  isLoading = false;
  accountCreated: SignUpAccountResponse | null = null;
  readonly formatCurrency = formatCurrency;

  form = this.fb.nonNullable.group({
    fullName: ['', [Validators.required, Validators.minLength(3)]],
    cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  constructor(private auth: AuthService, private toast: ToastService) {}

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.show('warning', 'Campos invalidos', 'Revise os dados.');
      return;
    }

    const raw = this.form.getRawValue();
    const cpf = normalizeCpf(raw.cpf);

    if (cpf.length !== 11) {
      this.toast.show('warning', 'CPF invalido', 'Use 11 digitos.');
      return;
    }

    this.form.patchValue({ cpf });
    this.isLoading = true;

    this.auth
      .signUp({
        fullName: raw.fullName,
        cpf,
        email: raw.email,
        password: raw.password,
      })
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response) => {
          this.accountCreated = response;
          this.toast.show('success', 'Conta criada', 'Agora faca login para acessar.');
        },
        error: (error) => {
          this.toast.show('error', 'Falha ao criar conta', getErrorMessage(error, 'Tente novamente.'));
        },
      });
  }
}
