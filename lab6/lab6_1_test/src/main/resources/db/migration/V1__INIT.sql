CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL
);

INSERT INTO car (brand, model) VALUES ('Toyota', 'Corolla');
INSERT INTO car (brand, model) VALUES ('Honda', 'Civic');
INSERT INTO car (brand, model) VALUES ('Ford', 'Focus');