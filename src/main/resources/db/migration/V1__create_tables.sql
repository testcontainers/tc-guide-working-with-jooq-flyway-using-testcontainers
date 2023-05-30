create sequence user_id_seq start with 10 increment by 10;
create sequence post_id_seq start with 10 increment by 10;
create sequence comment_id_seq start with 10 increment by 10;

create table users
(
    id         bigint default nextval('user_id_seq') not null,
    name       varchar                               not null,
    email      varchar                               not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    constraint user_email_unique unique (email)
);

create table posts
(
    id         bigint default nextval('post_id_seq') not null,
    title      varchar                               not null,
    content    varchar                               not null,
    created_by bigint references users (id)          not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

create table comments
(
    id         bigint default nextval('comment_id_seq') not null,
    name       varchar                                  not null,
    content    varchar                                  not null,
    post_id    bigint references posts (id)             not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);