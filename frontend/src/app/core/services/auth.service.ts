import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../api.config';
import {
  Session,
  SignInRequest,
  SignInResponse,
  SignUpAccountResponse,
  SignUpRequest,
} from '../models/auth.models';

const STORAGE_KEY = 'finconnect.session';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  readonly session = signal<Session | null>(this.readSession());

  constructor(private http: HttpClient) {}

  signIn(request: SignInRequest, cpf: string): Observable<SignInResponse> {
    return this.http
      .post<SignInResponse>(`${API_BASE_URL}/api/auth/signin`, request)
      .pipe(
        tap((response) => {
          this.saveSession({
            accessToken: response.jwt,
            refreshToken: response.refreshToken,
            email: request.username,
            cpf,
          });
        })
      );
  }

  signUp(request: SignUpRequest): Observable<SignUpAccountResponse> {
    return this.http.post<SignUpAccountResponse>(`${API_BASE_URL}/api/auth/signup`, request);
  }

  logout(): void {
    this.clearSession();
  }

  isAuthenticated(): boolean {
    const token = this.getAccessToken();
    if (!token) {
      return false;
    }

    return !this.isTokenExpired(token);
  }

  isTokenValid(): boolean {
    const token = this.getAccessToken();
    if (!token) {
      return false;
    }

    return !this.isTokenExpired(token);
  }

  hasSession(): boolean {
    return this.session() !== null;
  }

  getAccessToken(): string | null {
    return this.session()?.accessToken ?? null;
  }

  getCpf(): string | null {
    return this.session()?.cpf ?? null;
  }

  updateCpf(cpf: string): void {
    const current = this.session();
    if (!current) {
      return;
    }

    this.saveSession({
      ...current,
      cpf,
    });
  }

  private readSession(): Session | null {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) {
      return null;
    }

    try {
      return JSON.parse(raw) as Session;
    } catch {
      return null;
    }
  }

  private saveSession(session: Session): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
    this.session.set(session);
  }

  private clearSession(): void {
    localStorage.removeItem(STORAGE_KEY);
    this.session.set(null);
  }

  private isTokenExpired(token: string): boolean {
    const payload = this.parseJwt(token);
    if (!payload?.exp) {
      return true;
    }

    return Date.now() >= payload.exp * 1000;
  }

  private parseJwt(token: string): { exp?: number } | null {
    const parts = token.split('.');
    if (parts.length < 2) {
      return null;
    }

    const payload = parts[1].replace(/-/g, '+').replace(/_/g, '/');
    const padded = payload.padEnd(payload.length + (4 - (payload.length % 4)) % 4, '=');

    try {
      return JSON.parse(atob(padded)) as { exp?: number };
    } catch {
      return null;
    }
  }
}
