-- Insert Admin User
INSERT INTO auth.users (username, password, enabled, created_date)
VALUES (
    'admin',
    '$2a$10$hXB2fs5cJFskrRmQTxN/Re37nHDY50TDvFz3A2Z2i0A0.bKVq9Luq',
    TRUE,
    '2025-01-03 11:08:32'
);

-- Insert Admin Authority
INSERT INTO auth.authorities (authority, username, created_date)
VALUES (
    'ROLE_ADMIN',
    'admin',
    '2025-01-03 11:08:32'
);

-- Insert Regular User
INSERT INTO auth.users (username, password, enabled, created_date)
VALUES (
    'pkmn_user_app',
    '$2a$10$hXB2fs5cJFskrRmQTxN/Re37nHDY50TDvFz3A2Z2i0A0.bKVq9Luq',
    TRUE,
    '2025-01-03 11:08:32'
);

-- Insert User Authority
INSERT INTO auth.authorities (authority, username, created_date)
VALUES (
    'ROLE_USER',
    'pkmn_user_app',
    '2025-01-03 11:08:32'
);