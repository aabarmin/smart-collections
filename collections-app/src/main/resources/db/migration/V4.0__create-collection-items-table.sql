create table COLLECTION_ITEMS
(
    ID            INTEGER auto_increment,
    COLLECTION_ID INTEGER,
    ARTIST        VARCHAR(255) not null,
    ALBUM         VARCHAR(255),
    DELETED       BOOLEAN   default FALSE,
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP(),
    UPDATED_AT    TIMESTAMP,
    constraint COLLECTION_ITEMS_pk
        primary key (ID),
    constraint COLLECTION_ITEMS_COLLECTIONS_ID_fk
        foreign key (COLLECTION_ID) references COLLECTIONS
);

