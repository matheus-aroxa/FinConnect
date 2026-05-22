import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service';
import { normalizeCpf } from '../../../../core/utils/cpf';
import { getErrorMessage } from '../../../../core/utils/format';
import { ToastService } from '../../../../shared/toast/toast.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  isLoading = false;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
    cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
  });

  constructor(private auth: AuthService, private router: Router, private toast: ToastService) {}

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
      .signIn({ username: raw.email, password: raw.password }, cpf)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: () => {
          this.toast.show('success', 'Login realizado', 'Bem-vindo ao FinConnect.');
          void this.router.navigate(['/app']);
        },
        error: (error) => {
          this.toast.show('error', 'Falha no login', getErrorMessage(error, 'Confira seus dados.'));
        },
      });
  }
}
