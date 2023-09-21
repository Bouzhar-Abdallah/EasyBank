create table person(
	id SERIAL primary key,
	nom varchar(250) NOT NULL,
	prenom varchar(250) NOT NULL,
	dateNaissance Date NOT NULL,
	numeroTel varchar(50) NOT NULL,
	adresse varchar(250) NOT NULL,
	adressemail varchar(250) NOT NULL
);

create table employer(
	matricule varchar(20) primary key,
	dateRecrutement Date NOT NULL,
	personId int,
	FOREIGN key (personId) REFERENCES person(id) on DELETE CASCADE on UPDATE CASCADE
);
create table client(
	code varchar(20) primary key,
	personId int,
	FOREIGN key (personId) REFERENCES person(id) on DELETE CASCADE on UPDATE CASCADE
);

create table mission(
	code varchar(20) primary key,
	nom varchar(20) NOT NULL,
	description varchar(250)
);

create table employer_mission(
	employerId varchar(20),
    FOREIGN key (employerId) REFERENCES employer(matricule) on DELETE CASCADE on UPDATE CASCADE,
    missionId varchar(20),
    FOREIGN key (missionId) REFERENCES mission(code) on DELETE CASCADE on UPDATE CASCADE,
    dateDebut Date NOT NULL,
    dateFin Date NOT NULL
);

CREATE TYPE etat_enum AS ENUM ('actif', 'suspendu', 'saisi');

CREATE TABLE compte (
    numero bigint PRIMARY KEY,
    solde double precision DEFAULT(0) NOT NULL,
    dateCreation DATE NOT NULL,
    etat etat_enum,
    employerMatricule varchar(20),
    FOREIGN KEY (employerMatricule) REFERENCES employer(matricule) on DELETE CASCADE on UPDATE CASCADE,
	clientCode varchar(20),
    FOREIGN KEY (clientCode) REFERENCES client(code) on DELETE CASCADE on UPDATE CASCADE
);

CREATE TABLE courant (
    numeroCompte bigint NOT NULL,
    decouvert double precision NOT NULL,
	compteNumero bigint,
	FOREIGN key (compteNumero) REFERENCES compte(numero) on DELETE CASCADE on UPDATE CASCADE
);

CREATE TABLE epargne (
    numeroCompte bigint NOT NULL,
    tauxInteret numeric NOT NULL,
	compteNumero bigint,
	FOREIGN key (compteNumero) REFERENCES compte(numero) on DELETE CASCADE on UPDATE CASCADE
);

CREATE TYPE type_operation_enum AS ENUM ('versement', 'retrait', 'virement');

create table operation(
	numero bigint primary key,
	dateOperation timestamp ,
    type type_operation_enum,
	montant double precision NOT NULL,
	employerCode varchar(20),
    FOREIGN KEY (employerCode) REFERENCES employer(matricule) on DELETE CASCADE on UPDATE CASCADE,
	compteNumero bigint,
    FOREIGN KEY (compteNumero) REFERENCES compte(numero) on DELETE CASCADE on UPDATE CASCADE
);

