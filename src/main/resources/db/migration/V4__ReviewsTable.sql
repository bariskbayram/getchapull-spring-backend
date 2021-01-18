CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    review_title VARCHAR(100) NOT NULL,
    review_content VARCHAR(1000) NOT NULL,
    review_point INTEGER NOT NULL,
    posting_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    album_id INTEGER REFERENCES albums(album_id),
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE
);