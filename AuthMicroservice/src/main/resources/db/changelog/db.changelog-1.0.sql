--liquibase formatted sql

--changeset jawh:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    role  VARCHAR(16) NOT NULL
);

--changeset jawh:2
INSERT INTO users(username, password, role)
VALUES ('jawh', '88005553535', 'USER')