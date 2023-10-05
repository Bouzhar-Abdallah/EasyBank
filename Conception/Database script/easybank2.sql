CREATE TYPE etat_enum AS ENUM ('actif', 'suspendu', 'saisi');
CREATE TYPE type_operation_enum AS ENUM ('versement', 'retrait', 'virement');

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(250) NOT NULL,
    prenom VARCHAR(250) NOT NULL,
    dateNaissance DATE NOT NULL,
    numeroTel VARCHAR(50) NOT NULL,
    adresse VARCHAR(250) NOT NULL,
    adressemail VARCHAR(250) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employer (
    matricule SERIAL PRIMARY KEY,
    dateRecrutement DATE NOT NULL
)INHERITS(person);

CREATE TABLE client (
    code SERIAL PRIMARY KEY
)INHERITS(person);

CREATE TABLE mission (
    code SERIAL PRIMARY KEY,
    nom VARCHAR(20) NOT NULL,
    description VARCHAR(250),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE affectation (
    employerId INT,
    FOREIGN KEY (employerId) REFERENCES employer (matricule) ON DELETE CASCADE ON UPDATE CASCADE,
    missionId INT,
    FOREIGN KEY (missionId) REFERENCES mission (code) ON DELETE CASCADE ON UPDATE CASCADE,
    dateDebut DATE NOT NULL,
    dateFin DATE Null,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE agence(
    code SERIAL PRIMARY KEY,
    nom VARCHAR(250) NOT NULL,
    numeroTel VARCHAR(50) NOT NULL,
    adresse VARCHAR(250) NOT NULL
);
CREATE TABLE compte (
    numero BIGINT PRIMARY KEY,
    solde DOUBLE PRECISION DEFAULT (0) NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    etat etat_enum,
    clientCode INT,
    employerMatricule INT,
    agenceCode INT,
    FOREIGN KEY (clientCode) REFERENCES client (code) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (employerMatricule) REFERENCES employer (matricule) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (agenceCode) REFERENCES agence (code) ON DELETE CASCADE ON UPDATE CASCADE,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE courant (
    compteNumero BIGINT PRIMARY KEY,
    decouvert DOUBLE PRECISION NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)INHERITS(compte);

CREATE TABLE epargne (
    compteNumero BIGINT PRIMARY KEY,
    tauxInteret NUMERIC NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)INHERITS(compte);

CREATE TABLE operation (
    numero BIGINT PRIMARY KEY,
    dateOperation TIMESTAMP,
    type type_operation_enum,
    montant DOUBLE PRECISION NOT NULL,
    employerCode INT,
    FOREIGN KEY (employerCode) REFERENCES employer (matricule) ON DELETE CASCADE ON UPDATE CASCADE,
    compteNumero BIGINT,
    FOREIGN KEY (compteNumero) REFERENCES compte (numero) ON DELETE CASCADE ON UPDATE CASCADE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE account_number_seq
    START 1000000000000000
    INCREMENT 1
    MINVALUE 1000000000000000
    MAXVALUE 9999999999999999
    CYCLE;

-- Create an AFTER INSERT trigger for "versement" operations
-- Create a PL/pgSQL function for "versement" operations
CREATE OR REPLACE FUNCTION update_compte_solde_versement()
RETURNS TRIGGER AS $$
BEGIN
    -- Update compte.solde by adding operation.montant
    UPDATE compte
    SET solde = solde + NEW.montant
    WHERE numero = NEW.compteNumero;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Create a PL/pgSQL function for "retrait" operations
CREATE OR REPLACE FUNCTION update_compte_solde_retrait()
RETURNS TRIGGER AS $$
BEGIN
    -- Update compte.solde by subtracting operation.montant
    UPDATE compte
    SET solde = solde - NEW.montant
    WHERE numero = NEW.compteNumero;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Create an AFTER INSERT trigger for "versement" operations
CREATE TRIGGER update_compte_solde_versement_trigger
AFTER INSERT ON operation
FOR EACH ROW
WHEN (NEW.type = 'versement')
EXECUTE FUNCTION update_compte_solde_versement();

-- Create an AFTER INSERT trigger for "retrait" operations
CREATE TRIGGER update_compte_solde_retrait_trigger
AFTER INSERT ON operation
FOR EACH ROW
WHEN (NEW.type = 'retrait')
EXECUTE FUNCTION update_compte_solde_retrait();
 

 CREATE SEQUENCE operation_number_seq
    START 1000000000000000
    INCREMENT 1
    MINVALUE 1000000000000000
    MAXVALUE 9999999999999999
    CYCLE;

CREATE TABLE affectationAgence (
    agenceCode INT,
    FOREIGN KEY (agenceCode) REFERENCES agence (code) ON DELETE CASCADE ON UPDATE CASCADE,
    clientCode INT,
    FOREIGN KEY (clientCode) REFERENCES client (code) ON DELETE CASCADE ON UPDATE CASCADE,
    dateDebut DATE NOT NULL,
    dateFin DATE Null,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

