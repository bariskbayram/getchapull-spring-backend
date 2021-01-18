CREATE TABLE users_following (
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE ,
    following_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE ,
    PRIMARY KEY (user_id, following_id)
);