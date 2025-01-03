-- Users and Authorities Tables

-- Create Users Table
CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_pkey PRIMARY KEY (username)
);

-- Create Authorities Table
CREATE TABLE authorities (
     id BIGSERIAL PRIMARY KEY,
     authority VARCHAR(50) NOT NULL,
     username VARCHAR(50) NOT NULL,
     created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
     CONSTRAINT uk_username_authority UNIQUE (username, authority)
);

-- Create Index on Authorities
CREATE INDEX ix_auth_username ON authorities(username, authority);

-- Insert Admin User
INSERT INTO users (username, password, enabled, created_date)
VALUES (
    'admin',
    '$2a$10$hXB2fs5cJFskrRmQTxN/Re37nHDY50TDvFz3A2Z2i0A0.bKVq9Luq',
    TRUE,
    '2025-01-03 11:08:32'
);

-- Insert Admin Authority
INSERT INTO authorities (authority, username, created_date)
VALUES (
    'ROLE_ADMIN',
    'admin',
    '2025-01-03 11:08:32'
);

-- Insert Regular User
INSERT INTO users (username, password, enabled, created_date)
VALUES (
    'pkmn_user_app',
    '$2a$10$hXB2fs5cJFskrRmQTxN/Re37nHDY50TDvFz3A2Z2i0A0.bKVq9Luq',
    TRUE,
    '2025-01-03 11:08:32'
);

-- Insert User Authority
INSERT INTO authorities (authority, username, created_date)
VALUES (
    'ROLE_USER',
    'pkmn_user_app',
    '2025-01-03 11:08:32'
);