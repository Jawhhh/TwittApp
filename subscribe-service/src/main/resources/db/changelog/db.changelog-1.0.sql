--liquibase formatted sql

--changeset jawh:1
CREATE TABLE profile_subscribers (
    id bigint primary key ,
    subscribers_ids bigint ,
    subscriptions_ids bigint
);

--changeset jawh:2
CREATE TABLE kafka_messages (
    id varchar(64) primary key
);