import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Toast, ToastService } from './toast.service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast-container.component.html',
  styleUrl: './toast-container.component.css',
})
export class ToastContainerComponent {
  constructor(public toastService: ToastService) {}

  trackById(_index: number, toast: Toast): string {
    return toast.id;
  }
}
