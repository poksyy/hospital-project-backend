use hospitaldb;

CREATE TABLE nurse (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE patient (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    vitalSigns VARCHAR(100) NOT NULL,
    hygiene VARCHAR(50) UNIQUE NOT NULL,
    medication VARCHAR(255) NOT NULL,
    diet VARCHAR(255) NOT NULL,
    weight FLOAT NOT NULL
);

CREATE TABLE nurse_patient (
    nurse_id INT,
    patient_id INT,
    PRIMARY KEY (nurse_id, patient_id),
    FOREIGN KEY (nurse_id) REFERENCES nurse(Id),
    FOREIGN KEY (patient_id) REFERENCES patient(Id)
);

CREATE TABLE discharged_patients (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    patient_id INT UNIQUE,
    FOREIGN KEY (patient_id) REFERENCES patient(Id)
);


INSERT INTO nurse (name, user, password)
VALUES 
('Pau', 'pl2024769', 'pau1234'),
('Dylan', 'pl2024379', 'dylan1234'),
('Cristian', 'pl2024768', 'cristian1234');

