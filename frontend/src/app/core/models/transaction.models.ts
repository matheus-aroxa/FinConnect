export type TransactionType = 'TRANSFER' | 'DEPOSIT' | 'WITHDRAW';
export type TransactionStatus = 'COMPLETED' | 'FAILED';

export interface Transaction {
  id: string;
  originCpf: string;
  destinationCpf: string;
  amount: number;
  createdAt: string;
  transactionType: TransactionType;
  transactionStatus: TransactionStatus;
}

export interface StatementResponse {
  transactions: Transaction[];
}

export interface TransferRequest {
  origin: string;
  destination: string;
  amount: number;
}
