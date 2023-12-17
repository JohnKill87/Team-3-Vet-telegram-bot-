--liquibase formatted sql

--changeset roma:1

CREATE TABLE shelter (
id SERIAL PRIMARY KEY,
work_schedule VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
phone_number_security VARCHAR(255) NOT NULL,
safety_precautions VARCHAR(255) NOT NULL
);

CREATE TABLE driving_directions (
id SERIAL PRIMARY KEY,
file_path VARCHAR(255) NOT NULL,
file_size BIGINT NOT NULL,
media_type VARCHAR(255) NOT NULL,
shelter_id INT,
data BYTEA,
FOREIGN KEY (shelter_id) REFERENCES shelter(id)
);
