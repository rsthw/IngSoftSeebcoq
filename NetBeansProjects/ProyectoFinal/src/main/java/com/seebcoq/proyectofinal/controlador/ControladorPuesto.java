/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.Puesto;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author slf
 */
public class ControladorPuesto {
    private EntityManagerFactory emf;

    public ControladorPuesto(){
        emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
    }

    public Puesto buscarPuesto(String nombre){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Puesto> q1 = em.createNamedQuery( "Puesto.findByNombre", Puesto.class).setParameter("nombre", nombre);
        List<Puesto> ps = q1.getResultList();
        return ps.get(0);
    }

    public List<Comentario> buscarComentarios(Puesto puesto){
        List<Comentario> comentarios = new ArrayList<Comentario>();
        comentarios.addAll(puesto.getComentarioCollection());
        return comentarios;
    }

    public List<Calificacion> buscarCalificaciones(Puesto puesto){
        List<Calificacion> calificaciones = new ArrayList<Calificacion>();
        calificaciones.addAll(puesto.getCalificacionCollection());
        return calificaciones;
    }

    public Double buscarCalificacion(Puesto puesto){
      Double cal = puesto.getCalificacion();
      return cal;
    }
/*
    public void guardarCalificacion(Double c){
      Double actual = puesto.getCalificacion();
      actual = (actual + c)/6;
    }*/


}
