CREATE TABLE IF NOT EXISTS user_data (
    id BIGINT PRIMARY KEY,
    login VARCHAR(255),
    mobile_phone VARCHAR(255),
    balance INTEGER,
    name VARCHAR(255)
);