--liquibase formatted sql

--changeset jawh:1
CREATE TABLE IF NOT EXISTS profile
(
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(32) NOT NULL ,
    lastname VARCHAR(32),
    email VARCHAR(128) NOT NULL UNIQUE ,
    born_date DATE NOT NULL,
    status VARCHAR(128)
);

--changeset jawh:2
ALTER TABLE profile
ALTER COLUMN id TYPE bigint;

--changeset jawh:3
INSERT INTO profile(id, firstname, lastname, email, born_date)
VALUES (1, 'Oleg', 'Kotov', 'olegkotov020@gmail.com', '2006-01-09')
