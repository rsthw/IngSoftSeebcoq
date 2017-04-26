/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;

import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.jpaControllers.*;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import com.seebcoq.proyectofinal.modelo.*;
import java.util.List;
import java.util.ArrayList;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import com.seebcoq.proyectofinal.modelo.jpaControllers.ComentarioJpaController;

/**
 *
 * @author slf
 */
public class ControladorPuesto {

    private EntityManagerFactory emf;

    public ControladorPuesto() {
        emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
    }

    public Puesto buscarPuesto(Long id) {
        PuestoJpaController puestoCtrl = new PuestoJpaController(emf);
        Puesto puesto = puestoCtrl.findPuesto(id);
        return puesto;
    }

    public List<Comentario> buscarComentarios(Puesto puesto) {
        return puesto.getComentarioList();
    }

    public List<Puesto> buscarPuestos() {
        PuestoJpaController pjc = new PuestoJpaController(emf);
        return pjc.findPuestoEntities();
    }

    public Comentario guardarComentario(Puesto puesto, Persona persona, String comentario) {
        ComentarioJpaController comentarioCtrl = new ComentarioJpaController(emf);
        Comentario nuevo_comentario = new Comentario(puesto, persona, comentario);
        comentarioCtrl.create(nuevo_comentario);
        return nuevo_comentario;
    }

    public void eliminarComentario(Comentario com) {
        ComentarioJpaController comentarioCtrl = new ComentarioJpaController(emf);
        try {
            comentarioCtrl.destroy(com.getIdComentario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregaPuesto(Puesto puesto) {
        PuestoJpaController pjc = new PuestoJpaController(emf);
        pjc.create(puesto);
    }

    public List<Menu> buscarMenu(Puesto puesto) {
        List<Menu> menus = new ArrayList<Menu>();
        menus.addAll(puesto.getMenuList());
        return menus;
    }

    public List<Platillo> buscarPlatillos(Puesto puesto) {
        List<Platillo> platillos = new ArrayList<Platillo>();
        platillos.addAll(puesto.getPlatilloList());
        return platillos;
    }

    public List<Calificacion> buscarCalificaciones(Puesto puesto) {
        List<Calificacion> calificaciones = new ArrayList<Calificacion>();
        calificaciones.addAll(puesto.getCalificacionList());
        return calificaciones;
    }

    public Double buscarCalificacion(Puesto puesto) {
        Double actual = puesto.getCalificacion();
        int totalCalfs = buscarCalificaciones(puesto).size();
        List<Calificacion> calfs = buscarCalificaciones(puesto);
        for (Calificacion c : calfs) {
            actual = (actual + c.getCalificacion());
            System.out.println(actual);
        }
        actual = (actual) / totalCalfs;
        puesto.setCalificacion(actual);
        PuestoJpaController ctrl_puesto = new PuestoJpaController(emf);
        try {
            ctrl_puesto.edit(puesto);
        } catch (Exception e) {
            System.out.println("no se pudo actualizar la calificacion");
        }
        return actual;
    }

    public String buscarImagen(Puesto puesto) {
        String imagen = puesto.getImagen();
        return imagen;
    }

    public void guardarCalificacion(int c, Puesto puesto, Persona persona) {
        PuestoJpaController ctrl_puesto = new PuestoJpaController(emf);

        Long idPuesto = puesto.getIdPuesto();
        Long idUsuario = persona.getIdPersona();

        System.out.println(idPuesto);
        System.out.println(idUsuario);
        System.out.println(c);
        System.out.println("trabajando");

        CalificacionPK pk = new CalificacionPK(idPuesto, idUsuario);
        Calificacion nueva = new Calificacion(pk, c);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(nueva);
        System.out.println("trabajando calificacion");
        em.getTransaction().commit();
        em.close();

        
        System.out.println("sigo trabajando");
        try {
            ctrl_puesto.edit(puesto);
            Double actual = puesto.getCalificacion();
            actual = (actual + c) / 2;
            puesto.setCalificacion(actual);
        } catch (Exception ex) {
            System.out.println("no se pudo actualizar el puesto");
            ex.printStackTrace();
        }
    }
}
