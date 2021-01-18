CREATE TABLE bands(
    band_id SERIAL PRIMARY KEY,
    band_spotify_id VARCHAR(62) NOT NULL UNIQUE,
    band_name VARCHAR(100) NOT NULL
);