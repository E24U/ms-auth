create table roles
(
    id   uuid not null default gen_random_uuid()
        primary key,
    name varchar(124) UNIQUE
);

insert into roles(name)
VALUES ('администратор'),
       ('пользователь');