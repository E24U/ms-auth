create table roles
(
    id      uuid not null primary key,
    name    varchar(124) UNIQUE
);

insert into roles(id, name) VALUES ('11111111-d171-4df0-8636-448d6cf2ddf8', 'администратор'),
                                   ('22222222-d171-4df0-8636-448d6cf2ddf8', 'пользователь');