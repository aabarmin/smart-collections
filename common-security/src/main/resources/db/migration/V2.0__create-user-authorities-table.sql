create table USER_AUTHORITIES
(
    ID         INTEGER auto_increment,
    USER_ID    INTEGER not null,
    AUTHORITY  varchar(255),
    CREATED_AT timestamp default current_timestamp(),
    UPDATED_AT timestamp,
    constraint USER_AUTHORITIES_pk
        primary key (ID),
    constraint USER_AUTHORITIES_USERS_ID_fk
        foreign key (USER_ID) references USERS
);

create index USER_AUTHORITIES_USER_ID_index
    on USER_AUTHORITIES (USER_ID);

