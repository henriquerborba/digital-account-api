CREATE TABLE
    customer
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255)                            NOT NULL,
    cpf      VARCHAR(255)                            NOT NULL,
    email    VARCHAR(255) UNIQUE                     NOT NULL,
    password VARCHAR(255) UNIQUE                     NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE
    account
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    account_type VARCHAR(255)                            NOT NULL,
    balance      DECIMAL DEFAULT 0.0                     NOT NULL,
    customer_id  BIGINT                                  NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE
    transaction
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type       VARCHAR(255)                            NOT NULL,
    amount     DECIMAL                                 NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()                 NOT NULL,
    account_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE account
    ADD
        CONSTRAINT FK_ACCOUNT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);
