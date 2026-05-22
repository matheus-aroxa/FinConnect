import { Injectable, signal } from '@angular/core';

export type ToastTone = 'success' | 'error' | 'info' | 'warning';

export interface Toast {
  id: string;
  tone: ToastTone;
  title: string;
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  readonly toasts = signal<Toast[]>([]);

  show(tone: ToastTone, title: string, message = '', duration = 4500): void {
    const id = this.createId();
    const toast: Toast = { id, tone, title, message };

    this.toasts.update((current) => [...current, toast]);

    if (duration > 0) {
      window.setTimeout(() => this.dismiss(id), duration);
    }
  }

  dismiss(id: string): void {
    this.toasts.update((current) => current.filter((toast) => toast.id !== id));
  }

  clear(): void {
    this.toasts.set([]);
  }

  private createId(): string {
    return `${Math.random().toString(36).slice(2, 8)}${Date.now().toString(36)}`;
  }
}
