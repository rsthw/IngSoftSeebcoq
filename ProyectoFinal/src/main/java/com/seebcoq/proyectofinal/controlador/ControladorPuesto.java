/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import com.seebcoq.proyectofinal.modelo.jpaControllers.ComentarioJpaController;
/**
 *
 * @author slf
 */
public class ControladorPuesto {
    private EntityManagerFactory emf;
    
    public ControladorPuesto(){
        emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
    }
    
    public Puesto buscarPuesto(Long id){
        PuestoJpaController puestoCtrl = new PuestoJpaController(emf);
        Puesto puesto = puestoCtrl.findPuesto(id);
        return puesto;
    }
    
    public List<Comentario> buscarComentarios(Puesto puesto){
        return puesto.getComentarioList();
    }
    
    public List<Puesto> buscarPuestos (){
        PuestoJpaController pjc = new PuestoJpaController(emf);
        return pjc.findPuestoEntities();
    }
    
    public Comentario guardarComentario(Puesto puesto, Persona persona, String comentario){
        ComentarioJpaController comentarioCtrl = new ComentarioJpaController(emf);
        Comentario nuevo_comentario = new Comentario(puesto, persona, comentario);
        comentarioCtrl.create(nuevo_comentario);
        return nuevo_comentario;
    }
    
    public void eliminarComentario(Comentario com){
        ComentarioJpaController comentarioCtrl = new ComentarioJpaController(emf);
        try{
            comentarioCtrl.destroy(com.getIdComentario());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void agregaPuesto(Puesto puesto){
        PuestoJpaController pjc = new PuestoJpaController(emf);
        pjc.create(puesto);
    }
}
