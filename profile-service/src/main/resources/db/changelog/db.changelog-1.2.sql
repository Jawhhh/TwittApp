--liquibase formatted sql

--changeset jawh:1
ALTER TABLE profile
ADD COLUMN avatar_url varchar;

--changeset jawh:2
ALTER TABLE kafka_messages
ALTER COLUMN id TYPE varchar(32)