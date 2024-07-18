--liquibase formatted sql

--changeset jawh:1
ALTER TABLE kafka_messages
ALTER COLUMN id TYPE varchar(64);