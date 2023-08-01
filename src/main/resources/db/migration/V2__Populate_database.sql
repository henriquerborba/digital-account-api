INSERT INTO
    customer (name, cpf, email, password)
VALUES (
        'Penni Bogace',
        '501.416.880-92',
        'pbogace0@time.com',
        '$2a$10$cDMZ9A/fueS2veXEq9ewJ.l.qVx4u8BwW5rlOZi0vG/Fw/o/CWjVK'
    );

INSERT INTO
    customer (name, cpf, email, password)
VALUES (
        'Ettie Fontenot',
        '176.902.420-49',
        'efontenot1@taobao.com',
        '$2a$10$/pACz4LJr7rthl71/nQXDenZ46Ompye3rSjXNat91ijW89rae8xWW'
    );

INSERT INTO
    customer (name, cpf, email, password)
VALUES (
        'Micheline Sandys',
        '187.401.390-00',
        'msandys2@aol.com',
        '$2a$10$H2NyinLW9gYMxRbjAL8zU.6h8CuVytP11vV8fh0U1Frj3rHPnwNse'
    );

INSERT INTO
    customer (name, cpf, email, password)
VALUES (
        'Ally Sothcott',
        '896.148.200-98',
        'asothcott3@hhs.gov',
        '$2a$10$i/dXGHsjLO2Rg/ql/rxC3.8WDr4dfJKWZLVzIsM2jH/gms.gBV9Fy'
    );

INSERT INTO
    customer (name, cpf, email, password)
VALUES (
        'Alick Easum',
        '341.070.450-72',
        'aeasum4@weebly.com',
        '$2a$10$PzJ2SkTWMeyKfvwfEIrSfuZjf4MG4c4fgwTg/7xsDM9.QXRzwkW7K'
    );


    INSERT INTO
    account (
        account_type,
        balance,
        customer_id
    )
VALUES ('SAVINGS_ACCOUNT', 76.25, 1);

INSERT INTO
    account (
        account_type,
        balance,
        customer_id
    )
VALUES ('CURRENT_ACCOUNT', 2050, 1);

INSERT INTO
    account (
        account_type,
        balance,
        customer_id
    )
VALUES ('CURRENT_ACCOUNT', 124.33, 2);

INSERT INTO
    account (
        account_type,
        balance,
        customer_id
    )
VALUES ('SAVINGS_ACCOUNT', 250, 3);

INSERT INTO transaction (type, amount, created_at, account_id) VALUES ('DEPOSIT', 1000, '2023-07-31 15:23:03.219458', 1);
INSERT INTO transaction (type, amount, created_at, account_id) VALUES ('DEPOSIT', 2500, '2023-07-31 15:23:17.388604', 2);
INSERT INTO transaction (type, amount, created_at, account_id) VALUES ('WITHDRAW', 923.75, '2023-07-31 15:27:24.965467', 1);
INSERT INTO transaction (type, amount, created_at, account_id) VALUES ('WITHDRAW', 450, '2023-07-31 15:28:20.383985', 2);
INSERT INTO transaction (type, amount, created_at, account_id) VALUES ('WITHDRAW', 125.67, '2023-07-31 15:30:15.390345', 3);