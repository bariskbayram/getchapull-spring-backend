CREATE TABLE users_albums (
    user_id INTEGER REFERENCES users(user_id),
    album_id INTEGER REFERENCES albums(album_id),
    PRIMARY KEY (user_id, album_id)
);