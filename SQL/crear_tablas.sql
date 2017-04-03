use ComidaCienciasDB;

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

drop table if exists PUESTO;

create table if not exists PUESTO (
  idPuesto serial primary key,
  nombre varchar(255) not null,
  posX double,
  posY double,
  calificacion double
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists MENU;

create table if not exists MENU(
  idMenu serial primary key,
  diaDeLaSemana varchar(15) not null,
  desayuno_o_comida varchar(15) not null,
  idPuesto bigint(20) UNSIGNED,
  foreign key(idPuesto) references PUESTO(idPuesto)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists ALIMENTO;
CREATE TABLE if not exists ALIMENTO(
  nIdAlimento serial primary key,
  sNombre varchar(64)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists Menu_Alimento;
CREATE TABLE if not exists Menu_Alimento(
  nIdMenu bigint(20) unsigned,
  nIdAlimento bigint(20) unsigned,
  foreign key (nIdAlimento) references ALIMENTO(nIdAlimento),
  foreign key (nIdMenu) references MENU(idMenu)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists PLATILLO;

CREATE TABLE if not exists PLATILLO(
  idPlatillo serial primary key,
  idAlimento bigint(20) unsigned, 
  precio decimal(19,4),
  foreign key (idAlimento) references ALIMENTO(nIdAlimento)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists COMENTARIO;

create table if not exists COMENTARIO (
	idComentario serial primary key,
	idPuesto bigint(20) unsigned not null,
	idPersona bigint(20) unsigned not null,
	comentario varchar(255) not null,

	foreign key(idPuesto) references PUESTO(idPuesto),
	foreign key(idPersona) references PERSONA(idPersona)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;
