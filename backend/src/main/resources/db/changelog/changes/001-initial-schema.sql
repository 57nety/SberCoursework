-- liquibase formatted sql

-- changeset alexenko-av:1-0

DROP TABLE IF EXISTS documents;

CREATE TABLE documents
(
    id           SERIAL PRIMARY KEY,
    type         VARCHAR(255) NOT NULL,
    organization VARCHAR(255) NOT NULL,
    date         TIMESTAMP    NOT NULL,
    description  VARCHAR(255) NOT NULL,
    patient      VARCHAR(255) NOT NULL,
    status       VARCHAR(255) NOT NULL
);

INSERT INTO documents (type, organization, date, description, patient, status)
VALUES ('Type1', 'Organization1', '2023-11-23 12:30:00', 'Description1', 'Patient1', 'NEW'),
       ('Type3', 'Organization3', '2023-11-25 10:15:00', 'Description3', 'Patient3', 'ACCEPTED'),
       ('Type4', 'Organization4', '2023-11-26 08:00:00', 'Description4', 'Patient4', 'DECLINED');