/**
*	Base de datos GUPAP
*	Autores:
*	@guirdo
*	@diana
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