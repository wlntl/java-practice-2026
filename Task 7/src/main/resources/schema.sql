CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL CHECK (length(trim(name)) > 0),
    email VARCHAR(255) NOT NULL UNIQUE CHECK (length(trim(email)) > 0),
    password VARCHAR(255) NOT NULL CHECK (length(trim(password)) > 0),
    profileDescription TEXT NOT NULL DEFAULT ''
);
