DROP TABLE IF EXISTS Amistad;
DROP TABLE IF EXISTS Participacion;
DROP TABLE IF EXISTS Nest;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Estudios;
DROP TABLE IF EXISTS Solicitud;

/*----------------- CREACION DE TABLAS -------------------*/

CREATE TABLE Estudios(
	universidad varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	PRIMARY KEY(universidad)
);

CREATE TABLE Usuario(
	idU INTEGER AUTO_INCREMENT,
	nombre varchar(255) CHARACTER SET utf8mb4 NOT NULL,
        apellidos varchar(255) CHARACTER SET utf8mb4,
        fechaNacimiento date NOT NULL,
        correo varchar(255) CHARACTER SET utf8mb4 UNIQUE NOT NULL,
	universidad varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	grado varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	instagram varchar(255),
        arroba varchar(255) CHARACTER SET utf8mb4 UNIQUE NOT NULL,
        sexo varchar(255) CHARACTER SET utf8mb4 NOT NULL,
        password varchar(255) CHARACTER SET utf8mb4 NOT NULL,
        pfp MEDIUMBLOB,
        reputacion INT DEFAULT 0,
	PRIMARY KEY(idU),
	FOREIGN KEY(universidad) REFERENCES Estudios(universidad)
);

CREATE TABLE Nest (
	idN INTEGER AUTO_INCREMENT,
	idOrganizador int,
	nombre varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	ubicacion varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	descripcion varchar(255) CHARACTER SET utf8mb4 NOT NULL,
	fechaRealizacion DateTime NOT NULL,
	fechaFinalizacion DateTime NOT NULL,
	limitePersonas int NOT NULL,
	publico boolean NOT NULL,
        imagenFondo MEDIUMBLOB,
	PRIMARY KEY (idN),
	FOREIGN KEY (idOrganizador) REFERENCES Usuario(idU)
);

CREATE TABLE Amistad(
	idU1 INTEGER,
	idU2 INTEGER,
	PRIMARY KEY (idU1, idU2),
	FOREIGN KEY (idU1) REFERENCES Usuario(idU),
	FOREIGN KEY (idU2) REFERENCES Usuario(idU)
);


CREATE TABLE Participacion(
	idU INTEGER,
	idN INTEGER,
	votado BOOLEAN DEFAULT FALSE,
	PRIMARY KEY(idU, idN),
	FOREIGN KEY(idU) REFERENCES Usuario(idU),
        FOREIGN KEY(idN) REFERENCES Nest(idN)
);

CREATE TABLE Solicitud(
	idU1 INTEGER,
	idU2 INTEGER,
	PRIMARY KEY (idU1,idU2),
	FOREIGN KEY (idU1) REFERENCES Usuario(idU),
	FOREIGN KEY (idU2) REFERENCES Usuario(idU)
);
