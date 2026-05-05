CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'DEPOSIT', 'WITHDRAW');
CREATE TYPE transaction_status AS ENUM ('COMPLETED', 'FAILED');

CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    origin_cpf VARCHAR(11) NOT NULL,
    destination_cpf VARCHAR(11) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    tx_type transaction_type NOT NULL,
    tx_status transaction_status NOT NULL
);