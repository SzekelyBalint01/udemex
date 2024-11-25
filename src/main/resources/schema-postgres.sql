DROP TABLE IF EXISTS cars CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;

CREATE TABLE cars (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       photo BYTEA,
                       active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255),
                      email VARCHAR(255),
                      address VARCHAR(255),
                      phone VARCHAR(17),
                      rent_days INTEGER,
                      price INTEGER
);

CREATE TABLE reservations(
        id SERIAL PRIMARY KEY,
        start_date DATE,
        end_date Date,
        car_id INTEGER,
        user_id INTEGER,
        FOREIGN KEY (car_id) REFERENCES cars (id),
        FOREIGN KEY (user_id) REFERENCES users (id)
);

