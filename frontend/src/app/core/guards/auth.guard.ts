import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ToastService } from '../../shared/toast/toast.service';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (_route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const toast = inject(ToastService);

  if (auth.isAuthenticated()) {
    return true;
  }

  if (auth.hasSession()) {
    toast.show('warning', 'Sessao expirada', 'Faca login novamente.');
  }

  auth.logout();
  void router.navigate(['/auth/login'], { queryParams: { redirectTo: state.url } });
  return false;
};
