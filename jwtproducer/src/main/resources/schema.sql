CREATE TABLE IF NOT EXISTS user_credentials (
    id BIGINT PRIMARY KEY,
    Login VARCHAR(255),
    Password VARCHAR(255),
    Name VARCHAR(255),
    Role VARCHAR(255)
);