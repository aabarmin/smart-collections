create table COLLECTION_SHARING
(
    ID                     INTEGER auto_increment,
    OWNER_ID               INTEGER not null,
    COLLECTION_ID          INTEGER not null,
    COLLECTION_PERMISSIONS VARCHAR(255),
    RECIPIENT_ID           INTEGER,
    RECIPIENT_EMAIL        VARCHAR(255),
    REQUEST_KEY            VARCHAR(255),
    REQUEST_STATUS         VARCHAR(255),
    REQUEST_EXPIRES_AT     timestamp,
    DELETED                BOOLEAN default FALSE,
    CREATED_AT             TIMESTAMP,
    UPDATED_AT             TIMESTAMP,

    constraint COLLECTION_SHARING_pk
        primary key (ID),
    constraint COLLECTION_SHARING_COLLECTIONS_ID_fk
        foreign key (COLLECTION_ID) references COLLECTIONS,
    constraint COLLECTION_SHARING_USERS_ID_fk
        foreign key (OWNER_ID) references USERS
);

