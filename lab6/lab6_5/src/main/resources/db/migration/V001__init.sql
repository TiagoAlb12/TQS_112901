CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255)
);

INSERT INTO car (brand, model) VALUES ('Ford', 'Fiesta'), ('Renault', 'Clio');
