drop table restaurant if exists;
drop table review if exists;
drop table user if exists;

create table restaurant (
    id bigint generated by default as identity,
   	name varchar(50) not null,
   	address varchar(100) not null,
   	phone_number varchar(50) not null,
   	logo varchar(100) not null,
   	uri varchar(100) not null,
    primary key (id)
);

create table review (
    id bigint generated by default as identity,
    date_created timestamp not null,
    raiting bigint not null,
    text varchar(140) not null,
    restaurant_id bigint not null,
    primary key (id)
);

create table user (
    id bigint generated by default as identity,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(100) not null,
    primary key (id)
);