CREATE SCHEMA IF NOT EXISTS itrum_demo;

CREATE TABLE IF NOT EXISTS itrum_demo.wallet
(
    id       UUID PRIMARY KEY,
    amount   NUMERIC(17, 2),
    user_id  UUID,
    currency CHAR(3) NOT NULL
)