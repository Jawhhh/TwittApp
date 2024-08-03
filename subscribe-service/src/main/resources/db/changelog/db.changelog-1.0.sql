--liquibase formatted sql

--changeset jawh:1
CREATE TABLE users
(
    id bigint primary key
);

--changeset jawh:2
CREATE TABLE subscriptions
(
    user_id BIGINT REFERENCES users (id),
    subscriber_id BIGINT REFERENCES users (id),
    PRIMARY KEY (user_id, subscriber_id)
);

--changeset jawh:3
CREATE TABLE kafka_messages
(
    id varchar(64) primary key
);