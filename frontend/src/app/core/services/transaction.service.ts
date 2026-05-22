import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../api.config';
import { StatementResponse, TransferRequest } from '../models/transaction.models';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  constructor(private http: HttpClient) {}

  transfer(request: TransferRequest): Observable<string> {
    return this.http.post(`${API_BASE_URL}/api/transactions/transfer`, request, {
      responseType: 'text',
    });
  }

  getStatement(cpf: string): Observable<StatementResponse> {
    return this.http.get<StatementResponse>(`${API_BASE_URL}/api/transactions/statement/${cpf}`);
  }
}
