create table OUTGOING_EMAIL
(
    ID                     INTEGER auto_increment,
    EMAIL_TO               VARCHAR(255)  not null,
    EMAIL_FROM             VARCHAR(255)  not null,
    EMAIL_SUBJECT          VARCHAR(255)  not null,
    EMAIL_BODY             VARCHAR(40960) not null,
    STATUS VARCHAR(32) not null,
    SENDING_ERROR VARCHAR(81920),
    SEND_ATTEMPT_AT TIMESTAMP,
    SEND_ATTEMPT_NO INTEGER,
    SENT_AT TIMESTAMP,
    CREATED_AT             TIMESTAMP,
    UPDATED_AT             TIMESTAMP,

    constraint OUTGOING_EMAIL_pk
        primary key (ID)
);

