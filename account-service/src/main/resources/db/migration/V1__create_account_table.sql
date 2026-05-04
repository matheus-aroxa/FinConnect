CREATE TABLE account (
    account_number VARCHAR(10) PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    agency VARCHAR(4) NOT NULL,
    balance NUMERIC(19, 4) NOT NULL DEFAULT 0.0000,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'BLOCKED', 'CLOSED')),
    CONSTRAINT uk_agency_account UNIQUE (agency, account_number)
);