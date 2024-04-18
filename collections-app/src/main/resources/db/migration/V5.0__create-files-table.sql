create table FILES
(
    ID         INTEGER auto_increment,
    USER_ID    INTEGER      not null,
    FILENAME       VARCHAR(255) not null,
    DELETED    BOOLEAN   default FALSE,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP,
    constraint FILES_pk
        primary key (ID),
    constraint FILES_USER_ID___fk
        foreign key (USER_ID) references USERS
);

create index FILES_USER_ID__index
    on COLLECTIONS (USER_ID);

