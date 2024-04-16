create table COLLECTIONS
(
    ID         INTEGER auto_increment,
    USER_ID    INTEGER      not null,
    NAME       VARCHAR(255) not null,
    DELETED    BOOLEAN   default FALSE,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP,
    constraint COLLECTIONS_pk
        primary key (ID),
    constraint COLLECTIONS_USER_ID___fk
        foreign key (USER_ID) references USERS
);

create index COLLECTIONS_USER_ID__index
    on COLLECTIONS (USER_ID);

