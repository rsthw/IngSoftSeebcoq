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
        return puesto.getCalificacionList();
    }

    public Double buscarCalificacion(Puesto puesto) {
        Double actual = 0.0;
        int totalCalfs = buscarCalificaciones(puesto).size();
        if (totalCalfs > 0) {
            List<Calificacion> calfs = buscarCalificaciones(puesto);
            for (Calificacion c : calfs) {
                actual = (actual + c.getCalificacion());
            }
            actual = (actual) / totalCalfs;
            puesto.setCalificacion(actual);
            PuestoJpaController ctrl_puesto = new PuestoJpaController(emf);
            try {
                ctrl_puesto.edit(puesto);
            } catch (Exception e) {
                System.out.println("no se pudo actualizar la calificacion");
            }
        }
        return actual;
    }

    public String buscarImagen(Puesto puesto) {
        String imagen = puesto.getImagen();
        return imagen;
    }

    public void guardarCalificacion(int c, Puesto puesto, Persona persona) {
        CalificacionJpaController ctrl_cal = new CalificacionJpaController(emf);
        boolean esNueva = false;
        Calificacion anterior = ctrl_cal.findCalificacion(new CalificacionPK(puesto.getIdPuesto(), persona.getIdPersona()));
        if (anterior == null) {
            esNueva = true;
        }

        if (esNueva) {
            Calificacion cal = new Calificacion();
            cal.setCalificacion(c);
            cal.setPersona(persona);
            cal.setPuesto(puesto);

            try {
                ctrl_cal.create(cal);
            } catch (Exception e) {
                System.out.println("No se guarda calificacion");
            }
        } else {
            anterior.setCalificacion(c);
            try {
                ctrl_cal.edit(anterior);
            } catch (Exception e) {
                System.out.println("No se guarda calificacion");
            }
        }

    }
}
