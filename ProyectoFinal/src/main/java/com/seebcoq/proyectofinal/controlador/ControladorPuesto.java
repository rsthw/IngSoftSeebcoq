/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.controlador;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import java.util.List;
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
        return puesto.getComentarioList();
<<<<<<< HEAD
    }
    
    public List<Puesto> buscarPuestos (){
        PuestoJpaController pjc = new PuestoJpaController(emf);
        return pjc.findPuestoEntities();
=======
>>>>>>> e5d481299f16115bf1c85003a502b0757ade855b
    }
}
