-- liquibase formatted sql

-- changeset john:1
CREATE TABLE shelter (
    id SERIAL PRIMARY KEY,
    work_schedule VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number_security VARCHAR(255) NOT NULL,
    safety_precautions VARCHAR(255) NOT NULL
    );

CREATE TABLE volunteers (
    id BIGINT PRIMARY KEY,
    first_name TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    shelter_id BIGINT NOT NULL
);