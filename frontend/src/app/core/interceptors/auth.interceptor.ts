import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AUTH_FREE_PATHS, API_BASE_URL } from '../api.config';
import { ToastService } from '../../shared/toast/toast.service';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const toast = inject(ToastService);

  const isAuthFree = AUTH_FREE_PATHS.some((path) => req.url.includes(path));
  const isApiRequest = req.url.startsWith(API_BASE_URL) || req.url.startsWith('/api/');
  const token = auth.getAccessToken();

  let updatedReq = req;
  if (token && isApiRequest && !isAuthFree) {
    updatedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(updatedReq).pipe(
    catchError((error) => {
      if (error.status === 401 && !isAuthFree) {
        auth.logout();
        toast.show('warning', 'Sessao expirada', 'Faca login novamente.');
        void router.navigate(['/auth/login']);
      }

      return throwError(() => error);
    })
  );
};
