import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../api.config';
import {
  AccountInfoResponse,
  AccountResponse,
  CreditAccountRequest,
  DebtFromAccountRequest,
} from '../models/account.models';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) {}

  getAccountInfo(cpf: string): Observable<AccountInfoResponse> {
    return this.http.get<AccountInfoResponse>(`${API_BASE_URL}/api/accounts/${cpf}`);
  }

  creditAccount(request: CreditAccountRequest): Observable<AccountResponse> {
    return this.http.post<AccountResponse>(`${API_BASE_URL}/api/accounts/credit`, request);
  }

  debitAccount(request: DebtFromAccountRequest): Observable<AccountResponse> {
    return this.http.post<AccountResponse>(`${API_BASE_URL}/api/accounts/debt`, request);
  }
}
