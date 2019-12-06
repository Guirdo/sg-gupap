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
	cargo varchar(50),
	correo varchar(150),
	genero enum('Masculino','Femenino')
);

create table horario(
	idHorario int(11) auto_increment primary key,
	idPersonalH int(6),
	horaInicial time,
	horaFinal time,
	lunes boolean,
	martes boolean,
	miercoles boolean,
	jueves boolean,
	viernes boolean,
	sabado boolean,
	domingo boolean,
	foreign key (idPersonalH) references personal(idPersonal)
);

create table grupo(
	idGrupo int auto_increment primary key,
	curso enum('InglesA1','InglesA2','InglesB1','InglesB2','InglesC1'),
	fechaInicio date,
	numEstudiantes int(2) default 0,
	idHorarioG int(11),
	foreign key (idHorarioG) references horario(idHorario)
);

create table alumno(
	idAlumno int(11) auto_increment primary key,
	nombreA varchar(25),
	apellidoPatA varchar(20),
	apellidoMatA varchar(20),
	fechaNacimiento date,
	genero enum('FEMENINO','MASCULINO'),
	telefono varchar(10),
	domicilioA varchar(200),
	semanasPagadas int(2) default 0,
	idGrupoA int,
	foreign key(idGrupoA) references grupo(idGrupo)
);

create table pago(
	idPago int(11) primary key,
	fecha date,
	concepto enum('SEMANAL','INSCRIPCION','REINSCRIPCION'),
	monto float(4,2)
	idAlumnoP int,
	foreign key(idAlumnoP) references alumno(idAlumno)
);

create table asistenciaPersonal(
	idAsistenciaP int auto_increment primary key,
	fecha date,
	hora time,
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
*  Procedimientos almacenados
*/
-- Procedimiento almacenado para insertar asistencia en la BD
delimiter $$	
CREATE PROCEDURE insertarAsistenciaP(IN tip VARCHAR(7), IN idP int)
BEGIN
    insert into asistenciaPersonal (fecha,hora,tipo,idPersonalA) values
	(now(),now(),tip,idP);
    END$$
delimiter ;

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

insert into personal (nombreP,apellidoPatP,apellidoMatP,fechaNacimiento,domicilioP,cargo,correo,genero) values
('Anadely','Ávila','Moreno','1985-06-17','Calle Femion, Col. Elementales, Chilpancingo','DIRECTOR','anadely.a.m@gmail.com','Femenino'),
('Guadalupe','Tolentino','Ríos','1990-08-23','Calle Proton, Col. Elementales, Chilpancingo','ADMINISTRADOR','correo1@gmail.com','Femenino'),
('Aldro','Hernández','Sánchez','1990-04-20','Calle 4, Col. Pericos, Chilpancingo','COORDINADOR','correo2@gmail.com','Masculino'),
('Pedro','Sanchez','Gutierrez','1995-02-20','Calle 13, Col. Pericos, Chilpancingo','RECEPCIONISTA','correo2@gmail.com','Masculino');

insert into usuarioPersonal values
(1,1),(2,2),(3,3),(4,4);