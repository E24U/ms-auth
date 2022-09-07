create table users
(
    id       uuid         not null primary key,
    login varchar(124) UNIQUE,
    password varchar(512) not null
)