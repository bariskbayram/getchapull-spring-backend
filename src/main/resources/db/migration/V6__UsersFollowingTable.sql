CREATE TABLE users_following (
    user_id INTEGER REFERENCES users(user_id),
    following_id INTEGER REFERENCES users(user_id),
    PRIMARY KEY (userid, following_id)
);