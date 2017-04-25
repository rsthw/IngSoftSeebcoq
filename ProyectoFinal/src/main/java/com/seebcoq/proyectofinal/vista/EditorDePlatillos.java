/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Platillo;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PlatilloJpaController;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

/**
 *
 * @author slf
 */
@ManagedBean
@ViewScoped
public class EditorDePlatillos {
    private PlatilloJpaController platilloCtrl;
    private Long id;
    private Puesto puesto;
    private List<Platillo> platillos;
    
    private String nuevoNombre;
    
    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
        platilloCtrl = new PlatilloJpaController(emf);
        puesto = new Puesto();
    }
    
    public void agregaPlatillo(){
        Platillo nuevoPlatillo = new Platillo();
        nuevoPlatillo.setNombre(nuevoNombre);
        nuevoPlatillo.setIdPuesto(puesto);
        platilloCtrl.create(nuevoPlatillo);
        
        platillos.add(nuevoPlatillo);
        nuevoNombre = "";
    }
    
    public void eliminarPlatillo(Platillo platillo){
        try{
            platilloCtrl.destroy(platillo.getIdPlatillo());
        } catch(Exception e){
            e.printStackTrace();
        }
        platillos.remove(platillo);
    }
    
    /* SETTERS & GETTERs */
    public Puesto getPuesto() {
        return puesto;
    }
    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id) {
        this.id = id;
        setPuesto(new ControladorPuesto().buscarPuesto(id));
        setPlatillos(puesto.getPlatilloList());
    }
    public List<Platillo> getPlatillos(){
        return platillos;
    }
    public void setPlatillos(List<Platillo> platillos){
        this.platillos = platillos;
    }
    public String getNuevoNombre(){
        return nuevoNombre;
    }
    public void setNuevoNombre(String nombre){
        this.nuevoNombre = nombre;
    }
    
    
}
