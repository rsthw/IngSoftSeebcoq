drop table if exists CALIFICACION;
drop table if exists COMENTARIO;

drop table if exists PLATILLO;
drop table if exists MENU;

drop table if exists PUESTO;
drop table if exists PERSONA;

create table if not exists PERSONA (
  idPersona serial primary key,
  nombre varchar(255) not null,
  apPaterno varchar(255) not null,
  apMaterno varchar(255),
  correo varchar(255) not null,
  contraseña varchar(255) not null,
  esAdministrador boolean,
  nombreDeUsuario varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists PUESTO (
  idPuesto serial primary key,
  nombre varchar(255) not null,
  imagen varchar(255),
  latitud double,
  longitud double,
  calificacion double
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists MENU(
  idMenu serial primary key,
  diaDeLaSemana varchar(15) not null,
  desayuno_o_comida varchar(15) not null,
  idPuesto bigint(20) unsigned,
  foreign key(idPuesto) references PUESTO(idPuesto)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists PLATILLO(
  idPlatillo serial primary key,
  nombre varchar(64),
  idPuesto bigint(20) unsigned,
  foreign key (idPuesto) references PUESTO(idPuesto)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists COMENTARIO (
	idComentario serial primary key,
	idPuesto bigint(20) unsigned not null,
	idPersona bigint(20) unsigned not null,
	comentario varchar(255) not null,

	foreign key(idPuesto) references PUESTO(idPuesto),
	foreign key(idPersona) references PERSONA(idPersona)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists CALIFICACION (
	idPuesto bigint(20) unsigned not null,
	idPersona bigint(20) unsigned not null,
	calificacion int not null,

	foreign key(idPuesto) references PUESTO(idPuesto),
	foreign key(idPersona) references PERSONA(idPersona),

  primary key(idPuesto, idPersona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

