create sequence user_id_seq start with 10 increment by 10;
create sequence post_id_seq start with 10 increment by 10;
create sequence comment_id_seq start with 10 increment by 10;

create table users
(
    id         bigint DEFAULT nextval('user_id_seq') not null,
    name       varchar                               not null,
    email      varchar                               not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    CONSTRAINT user_email_unique UNIQUE (email)
);

create table posts
(
    id         bigint DEFAULT nextval('post_id_seq') not null,
    title      varchar                               not null,
    content    varchar                               not null,
    created_by bigint REFERENCES users (id)          not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

create table comments
(
    id         bigint DEFAULT nextval('comment_id_seq') not null,
    name       varchar                                  not null,
    content    varchar                                  not null,
    post_id    bigint REFERENCES posts (id)             not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);