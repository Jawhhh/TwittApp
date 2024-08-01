--liquibase formatted sql

--changeset jawh:1
CREATE TABLE users
(
    id bigint primary key
);

CREATE TABLE profile
(
    user_id bigint primary key,
    subscriber bigint references users,
    publisher bigint references users
);

--changeset jawh:2
CREATE TABLE kafka_messages
(
    id varchar(64) primary key
);