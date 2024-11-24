CREATE TABLE cars (
                       id SERIAL PRIMARY KEY,
                       neve VARCHAR(255) NOT NULL,
                       foto BYTEA,
                       aktiv BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE reservations(
        id SERIAL PRIMARY KEY,
        carId INTEGER,
        startDate DATE,
        endDate Date,
);

CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(17),
    rentDays INTEGER,
    price INTEGER,
    carId INTEGER
);