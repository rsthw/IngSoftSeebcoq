/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
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
}
