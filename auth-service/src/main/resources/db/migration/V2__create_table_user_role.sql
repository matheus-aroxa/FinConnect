CREATE TABLE user_roles (
    user_cpf VARCHAR(11) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles FOREIGN KEY (user_cpf) REFERENCES users (cpf) ON DELETE CASCADE
);