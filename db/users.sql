create table users
(
    id       uuid         not null default gen_random_uuid()
        primary key,
    login    varchar(124) UNIQUE,
    password varchar(512) not null
);