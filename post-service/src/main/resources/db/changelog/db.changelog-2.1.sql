--liquibase formatted sql

--changeset jawh:1
ALTER TABLE post
DROP COLUMN dislike_id;

--changeset jawh:2
ALTER TABLE comment
DROP COLUMN dislike_id;

--changeset jawh:3
DROP TABLE post_dislike_table;

--changeset jawh:4
DROP TABLE comment_dislike_table;