CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE IF NOT EXISTS auth.users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_pkey PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS auth.authorities (
    id BIGSERIAL PRIMARY KEY,
    authority VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES auth.users (username) ON DELETE CASCADE,
    CONSTRAINT uk_username_authority UNIQUE (username, authority)
);

-- Create Index on Authorities
CREATE INDEX IF NOT EXISTS ix_auth_username ON auth.authorities(username, authority);