CREATE TABLE students (
    id UUID NOT NULL,
    first_name VARCHAR(255),
    sur_name VARCHAR(255),
    family_name VARCHAR(255),
    "group" VARCHAR(255),
    CONSTRAINT pk_students PRIMARY KEY (id)
);