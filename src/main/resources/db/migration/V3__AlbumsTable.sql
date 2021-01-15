CREATE TABLE albums(
    album_id SERIAL PRIMARY KEY,
    album_name VARCHAR(100) NOT NULL,
    album_year INTEGER NOT NULL,
    band_id INTEGER REFERENCES bands(band_id)
);