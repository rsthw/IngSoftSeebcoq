package com.seebcoq.proyectofinal.modelo;

import com.seebcoq.proyectofinal.modelo.CalificacionPK;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.Puesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T17:32:01")
@StaticMetamodel(Calificacion.class)
public class Calificacion_ { 

    public static volatile SingularAttribute<Calificacion, Puesto> puesto;
    public static volatile SingularAttribute<Calificacion, Integer> calificacion;
    public static volatile SingularAttribute<Calificacion, Persona> persona;
    public static volatile SingularAttribute<Calificacion, CalificacionPK> calificacionPK;

}