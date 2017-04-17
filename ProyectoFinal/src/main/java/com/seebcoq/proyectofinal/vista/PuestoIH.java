/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.modelo.Platillo;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;

/**
 *
 * @author slf
 */
@ManagedBean
@RequestScoped
public class PuestoIH {
    private ControladorPuesto puestoCtrl;
    private Puesto puesto;
    private String nombre;
    
    public PuestoIH(){
        nombre = "tacosCiencias";
    }
    
    @PostConstruct
    public void init() {
        puestoCtrl = new ControladorPuesto();
        puesto = puestoCtrl.buscarPuesto(nombre);
    }
    /*
    public List<Comentario> getComentarios(){
        return puestoCtrl.buscarComentarios(puesto);
    }*/
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        nombre =  n;
    }
    public List<Puesto> getPuestos(){
        return puestoCtrl.buscarPuestos();
    }

    /**
     * Añade un puesto con los parametros dados
     * @param nombre nombre del puesto
     * @param lat latitud donde se encuentra
     * @param lng longitud donde se enecuentra
     */
    public void AddPuesto(String nombre, double lat, double lng){
        Puesto p = new Puesto();
        p.setNombre(nombre);
        p.setLatitud(lat);
        p.setLongitud(lng);
        puestoCtrl.agregaPuesto(puesto);
    }
}