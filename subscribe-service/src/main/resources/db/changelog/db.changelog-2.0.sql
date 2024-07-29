--liquibase formatted sql

--changeset jawh:1
DROP TABLE profile_subscribers;

--changeset jawh:2
CREATE TABLE users
(
    id BIGINT PRIMARY KEY
);

--changeset jawh:3
CREATE TABLE subscriptions
(
    follower_id BIGINT REFERENCES users,
    followee_id BIGINT REFERENCES users,
    PRIMARY KEY (follower_id, followee_id)
);

--changeset jawh:4
CREATE INDEX idx_follower_id ON subscriptions(follower_id);
CREATE INDEX idx_followee_id ON subscriptions(followee_id);
