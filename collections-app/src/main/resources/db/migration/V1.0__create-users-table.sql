create table USERS
(
    ID         INTEGER auto_increment,
    USERNAME   varchar(255) not null,
    PASSWORD   varchar(255),
    ACTIVATED  boolean default false,
    AUTH_TYPE  varchar(32)  not null,
    CREATED_AT timestamp default CURRENT_TIMESTAMP,
    UPDATED_AT timestamp,
    constraint users_pk
        primary key (ID)
);

create index username_unique
    on USERS (USERNAME);

