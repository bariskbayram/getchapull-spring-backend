CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(80) NOT NULL,
    bio_info VARCHAR(140),
    user_created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_role VARCHAR(50) NOT NULL
);