CREATE TABLE users_albums (
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE ,
    album_id INTEGER REFERENCES albums(album_id) ON DELETE CASCADE ,
    PRIMARY KEY (user_id, album_id)
);