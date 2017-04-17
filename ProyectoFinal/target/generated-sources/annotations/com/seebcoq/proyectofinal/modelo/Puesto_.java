package com.seebcoq.proyectofinal.modelo;

import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Menu;
import com.seebcoq.proyectofinal.modelo.Platillo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T17:32:01")
@StaticMetamodel(Puesto.class)
public class Puesto_ { 

    public static volatile ListAttribute<Puesto, Platillo> platilloList;
    public static volatile SingularAttribute<Puesto, Double> latitud;
    public static volatile SingularAttribute<Puesto, Double> longitud;
    public static volatile SingularAttribute<Puesto, Double> calificacion;
    public static volatile ListAttribute<Puesto, Menu> menuList;
    public static volatile ListAttribute<Puesto, Comentario> comentarioList;
    public static volatile SingularAttribute<Puesto, Long> idPuesto;
    public static volatile SingularAttribute<Puesto, String> imagen;
    public static volatile ListAttribute<Puesto, Calificacion> calificacionList;
    public static volatile SingularAttribute<Puesto, String> nombre;

}