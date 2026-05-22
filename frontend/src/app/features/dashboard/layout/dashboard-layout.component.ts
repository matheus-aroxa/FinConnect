import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-dashboard-layout',
  standalone: false,
  templateUrl: './dashboard-layout.component.html',
  styleUrl: './dashboard-layout.component.css',
})
export class DashboardLayoutComponent {
  private readonly auth = inject(AuthService);

  get session() {
    return this.auth.session;
  }

  constructor(private router: Router, private toast: ToastService) {}

  logout(): void {
    this.auth.logout();
    this.toast.show('info', 'Sessao encerrada', 'Ate logo.');
    void this.router.navigate(['/auth/login']);
  }
}
