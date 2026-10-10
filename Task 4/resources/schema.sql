CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE CHECK (length(trim(email)) > 0),
    password VARCHAR(255) NOT NULL CHECK (length(trim(password)) > 0),
    profile_description TEXT NOT NULL DEFAULT ''
);
