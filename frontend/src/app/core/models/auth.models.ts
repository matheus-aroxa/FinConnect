export interface SignInRequest {
  username: string;
  password: string;
}

export interface SignInResponse {
  jwt: string;
  refreshToken: string;
}

export interface SignUpRequest {
  fullName: string;
  cpf: string;
  email: string;
  password: string;
}

export interface SignUpAccountResponse {
  agency: string;
  accountNumber: string;
  balance: number;
  status: string;
}

export interface Session {
  accessToken: string;
  refreshToken: string;
  email: string;
  cpf: string;
}
