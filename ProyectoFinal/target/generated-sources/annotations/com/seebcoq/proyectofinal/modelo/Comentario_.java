package com.seebcoq.proyectofinal.modelo;

import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.Puesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T17:32:01")
@StaticMetamodel(Comentario.class)
public class Comentario_ { 

    public static volatile SingularAttribute<Comentario, Puesto> idPuesto;
    public static volatile SingularAttribute<Comentario, String> comentario;
    public static volatile SingularAttribute<Comentario, Long> idComentario;
    public static volatile SingularAttribute<Comentario, Persona> idPersona;

}