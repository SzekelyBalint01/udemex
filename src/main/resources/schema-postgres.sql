CREATE TABLE autok (
                       id SERIAL PRIMARY KEY,
                       neve VARCHAR(255) NOT NULL,
                       foto BYTEA,
                       aktiv BOOLEAN NOT NULL DEFAULT TRUE
);
