-- liquibase formatted sql

-- changeset john:1
CREATE TABLE shelter (
    id BIGINT PRIMARY KEY,
       name TEXT NOT NULL,
       address TEXT NOT NULL,
       work_time TEXT NOT NULL,
       security_contact_details TEXT NOT NULL
    );

CREATE TABLE volunteers (
    id BIGINT PRIMARY KEY,
    first_name TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    shelter BIGINT NOT NULL
);