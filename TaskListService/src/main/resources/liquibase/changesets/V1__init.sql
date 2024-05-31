
create table if not exists users
(
    id       int generated by default as identity primary key,
    name     varchar(50)  not null,
    email varchar(100) not null unique,
    password varchar(100) not null,
    role varchar not null
    );

create table if not exists tasks
(
    id              int generated by default as identity primary key,
    user_id int references users(id) on delete cascade ,
    title           varchar(50)  not null,
    description     varchar(100) null,
    status          varchar      not null,
    expiration_date timestamp    null
    );

create table if not exists quote(
    id int generated by default as identity primary key ,
    text varchar not null ,
    author varchar not null
)