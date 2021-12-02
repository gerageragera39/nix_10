drop table countries if exists;
drop table population if exists;
drop table country_person if exists;

create table countries
(
    id         bigint auto_increment
        primary key,
    created       datetime(6) null,
    updated       datetime(6) null,
    visible       bit null,
    country_name varchar(255) not null,
    ISO           int          not null
);

create table population
(
    id               bigint auto_increment
        primary key,
    created          datetime(6)  null,
    updated          datetime(6)  null,
    visible          bit          null,
    first_name       varchar(255) not null,
    last_name        varchar(255) not null,
    age              int          not null,
    sex              varchar(1) not null,
    passport_id      varchar(255) not null
);

create table country_person
(
    country_id bigint not null,
    person_id   bigint not null,
    primary key (country_id, person_id),
    foreign key (country_id) references countries (id),
    foreign key (person_id) references population (id)
);