--liquibase formatted sql

--changeset jawh:1
drop table like_table CASCADE;
drop table dislike_table CASCADE;
drop table post CASCADE;
drop table comment CASCADE;

--chageset jawh:2
create table post_like_table (
    id         bigserial primary key,
    profile_id bigint not null
);

--changeset jawh:3
CREATE TABLE post_dislike_table
(
    id         bigserial primary key,
    profile_id bigint not null
);

--chageset jawh:4
create table comment_like_table (
    id         bigserial primary key,
    profile_id bigint not null
);

--changeset jawh:5
CREATE TABLE comment_dislike_table
(
    id         bigserial primary key,
    profile_id bigint not null
);

--changeset jawh:6
CREATE TABLE post
(
    id               bigserial primary key,
    text             varchar(256),
    picture_url      varchar(128),
    profile_id       bigint not null,
    time_publication date   not null,
    like_id          bigint references post_like_table,
    dislike_id       bigint references post_dislike_table
);

--changeset jawh:7
CREATE TABLE comment
(
    id               bigserial primary key,
    profile_id       bigint       not null,
    post_id          bigint references post,
    text             varchar(256) not null,
    picture_url      varchar(128),
    time_publication date         not null,
    like_id          bigint references comment_like_table,
    dislike_id       bigint references comment_dislike_table
);

--changeset jawh:8
ALTER TABLE post
ADD COLUMN comment_id bigint references comment;

--changeset jawh:9
ALTER TABLE post_like_table
ADD COLUMN post_id bigint references post;
ALTER TABLE post_dislike_table
ADD COLUMN post_id bigint references post;
ALTER TABLE comment_like_table
ADD COLUMN comment_id bigint references comment;
ALTER TABLE comment_dislike_table
ADD COLUMN comment_id bigint references comment;


