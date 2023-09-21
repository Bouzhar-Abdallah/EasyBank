create table person(
	id SERIAL primary key,
	nom varchar(250),
	prenom varchar(250),
	dateNaissance Date,
	numeroTel varchar(50),
	adresse varchar(250),
	adressemail varchar(250)
);

create table employer(
	matricule varchar(20) primary key,
	dateRecrutement Date
) INHERITS (person) ;
create table client(
	code varchar(20) primary key
) INHERITS (person) ;

create table mission(
	code varchar(20) primary key,
	nom varchar(20),
	description varchar(250)
);

create table employer_mission(
	employerId varchar(20),
    FOREIGN key (employerId) REFERENCES employer(matricule),
    missionId varchar(20),
    FOREIGN key (missionId) REFERENCES mission(code),
    dateDebut Date,
    dateFin Date
);

CREATE TYPE etat_enum AS ENUM ('actif', 'suspendu', 'saisi');

CREATE TABLE compte (
    numero bigint PRIMARY KEY,
    solde double precision,
    dateCreation DATE,
    etat etat_enum,
    employerCode varchar(20),
    FOREIGN KEY (employerCode) REFERENCES employer(matricule)
);

CREATE TABLE courant (
    numeroCompte bigint,
    decouvert double precision
) INHERITS (compte) ;

CREATE TABLE epargne (
    numeroCompte bigint,
    tauxInteret numeric
) INHERITS (compte) ;

CREATE TYPE type_operation_enum AS ENUM ('versement', 'retrait', 'virement');

create table operation(
	numero bigint primary key,
	dateOperation timestamp,
    type type_operation_enum,
	montant double precision,
	employerCode varchar(20),
    FOREIGN KEY (employerCode) REFERENCES employer(matricule),
	compteNumero bigint,
    FOREIGN KEY (compteNumero) REFERENCES compte(numero)
);

ALTER TABLE compte
ADD clientCode varchar(20); 

ALTER TABLE compte
ADD CONSTRAINT fk_compte_client
FOREIGN KEY (clientCode) REFERENCES client(code);
