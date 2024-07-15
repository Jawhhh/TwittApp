--liquibase formatted sql

--changeset jawh:1
CREATE TABLE post
(
    id               bigserial primary key,
    text             varchar(256),
    picture_url      varchar(128),
    profile_id       bigint not null,
    time_publication date   not null,
    like_id          bigint references like,
    dislike_id       bigint references dislike,
    comment_id       bigint references comment
);

--changeset jawh:2
CREATE TABLE like
(
    id         bigserial primary key,
    profile_id bigint not null
);

--changeset jawh:3
CREATE TABLE dislike
(
    id         bigserial primary key,
    profile_id bigint not null
);


--changeset jawh:4
CREATE TABLE comment
(
    id               bigserial primary key,
    profile_id       bigint       not null,
    post_id          bigint references post,
    text             varchar(256) not null,
    picture_url      varchar(128),
    time_publication date         not null,
    like_id          bigint references like,
    dislike_id       bigint references dislike
);

