CREATE SCHEMA IF NOT EXISTS itrum_demo;

CREATE TABLE IF NOT EXISTS itrum_demo.wallet
(
    id       UUID PRIMARY KEY,
    balance  NUMERIC(17, 2) NOT NULL,
    user_id  UUID           NOT NULL,
    currency CHAR(3)        NOT NULL
)