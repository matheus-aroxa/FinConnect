export interface AccountInfoResponse {
  accountNumber: string;
  agency: string;
  balance: number;
}

export interface AccountResponse {
  cpf: string;
  agency: string;
  accountNumber: string;
  balance: number;
  status: string;
}

export interface CreditAccountRequest {
  cpf: string;
  amount: number;
}

export interface DebtFromAccountRequest {
  cpf: string;
  amount: number;
}
