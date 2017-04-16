/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.jpaControllers.CalificacionJpaController;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import com.seebcoq.proyectofinal.modelo.Menu;
import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.CalificacionPK;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.*;
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
        comentarios.addAll(puesto.getComentarioList());
        return comentarios;
    }

    public List<Menu> buscarMenu(Puesto puesto){
        List<Menu> menus = new ArrayList<Menu>();
        menus.addAll(puesto.getMenuList());
        return menus;
    }

    public List<Platillo> buscarPlatillos(Puesto puesto){
        List<Platillo> platillos = new ArrayList<Platillo>();
        platillos.addAll(puesto.getPlatilloList());
        return platillos;
    }

    
    public List<Calificacion> buscarCalificaciones(Puesto puesto){
        List<Calificacion> calificaciones = new ArrayList<Calificacion>();
        calificaciones.addAll(puesto.getCalificacionList());
        return calificaciones;
    }

    public Double buscarCalificacion(Puesto puesto){
      Double actual = puesto.getCalificacion();
      int totalCalfs = buscarCalificaciones(puesto).size();
      List<Calificacion> calfs = buscarCalificaciones(puesto);
      for(Calificacion c: calfs){
          actual = (actual + c.getCalificacion());
          System.out.println(actual);
      }
      actual = (actual)/totalCalfs;
      puesto.setCalificacion(actual);
      PuestoJpaController ctrl_puesto = new PuestoJpaController(emf);
      try{
      ctrl_puesto.edit(puesto);
      }catch(Exception e){
          System.out.println("no se pudo actualizar la calificacion");
      }
      return actual;
    }

    public String buscarImagen(Puesto puesto){
      String imagen = puesto.getImagen();
      return imagen;
    }


    public void guardarCalificacion(int c, Puesto puesto, Persona persona){
      CalificacionJpaController controlador = new CalificacionJpaController(emf);
      PuestoJpaController ctrl_puesto = new PuestoJpaController(emf);
      Long idPuesto = puesto.getIdPuesto();
      Long idUsuario = persona.getIdPersona();
      System.out.println(idPuesto);
      System.out.println(idUsuario);
      System.out.println(c);
      System.out.println("trabajando");
      CalificacionPK pk = new CalificacionPK(idPuesto, idUsuario);
      Calificacion nueva = new Calificacion(pk, c);
      
     /* try{
      controlador.create(nueva2);
      System.out.println("trabajando calificacion");
      }
      catch(Exception ex){
        System.out.println("no se pudo crear la calificacion 2");
      }*/
       EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
    
      em.persist(nueva);
      System.out.println("trabajando calificacion");
      em.getTransaction().commit();
      em.close();
      Double actual = puesto.getCalificacion();
      actual = (actual + c)/2;
      puesto.setCalificacion(actual);
      System.out.println("sigo trabajando");
       try{
      ctrl_puesto.edit(puesto);
       } catch(Exception ex){
             System.out.println("no se pudo actualizar el puesto");
       }
       }
      
    }



