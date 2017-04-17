package com.seebcoq.proyectofinal.modelo;

import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.Comentario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T17:32:01")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, String> nombreDeUsuario;
    public static volatile SingularAttribute<Persona, String> apMaterno;
    public static volatile ListAttribute<Persona, Comentario> comentarioList;
    public static volatile SingularAttribute<Persona, String> correo;
    public static volatile ListAttribute<Persona, Calificacion> calificacionList;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, Long> idPersona;
    public static volatile SingularAttribute<Persona, String> apPaterno;
    public static volatile SingularAttribute<Persona, Boolean> esAdministrador;
    public static volatile SingularAttribute<Persona, String> contraseña;

}