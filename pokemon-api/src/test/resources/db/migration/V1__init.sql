CREATE SCHEMA IF NOT EXISTS pokemon;

CREATE TABLE IF NOT EXISTS pokemon.students(
    id UUID NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    student_group VARCHAR(255),
    CONSTRAINT pk_students PRIMARY KEY (id)
);