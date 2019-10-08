/**
*	Base de datos GUPAP
*	Autores:
*	@guirdo
*	@dianini
*/

/**
*	Usuario para la base de datos
*/
create user 'adsoft' identified by '12345678';
GRANT ALL PRIVILEGES ON *.* TO adsoft;

create database bdgupap;

use bdgupap

create table usuario(
	idUsuario int auto_increment primary key,
	nombreUsuario varchar(20),
	contrasena varchar(32)
);

create table personal(
	idPersonal int(6) auto_increment primary key,
	nombreP varchar(25),
	apellidoPatP varchar(20),
	apellidoMatP varchar(20),
	fechaNacimiento date,
	domicilioP varchar(200),
	cargo varchar(50)
);

create table asistenciaPersonal(
	idAsistenciaP int auto_increment primary key,
	fechHora timestamp,
	tipo enum ('ENTRADA','SALIDA'),
	idPersonalA int(6),
	foreign key (idPersonalA) references personal(idPersonal)
);

create table informe(
	idInforme int auto_increment primary key,
	rutaTexto varchar(256),
	fecha date,
	enviado boolean not null default 0,
	leido boolean not null default 0,
	idPersonalI int(6),
	foreign key (idPersonalI) references personal(idPersonal)
);

create table usuarioPersonal(
	idUsuarioUP int,
	idPersonalUP int,
	primary key(idUsuarioUP,idPersonalUP)
);


/**
*	Usuario			Contraseña
*	****************************
*	director			1111
*	administrador		2222
*	coordinador			3333
*	recepcionista		4444
*	
*/
insert into usuario (nombreUsuario,contrasena) values
('director','b59c67bf196a4758191e42f76670ceba'),
('administrador','934b535800b1cba8f96a5d72f72f1611'),
('coordinador','2be9bd7a3434f7038ca27d1918de58bd'),
('recepcionista','dbc4d84bfcfe2284ba11beffb853a8c4');