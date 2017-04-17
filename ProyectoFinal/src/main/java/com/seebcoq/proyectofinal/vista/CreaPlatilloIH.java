/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.modelo.Platillo;
import com.seebcoq.proyectofinal.modelo.Puesto;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ulises
 */
@ManagedBean
public class CreaPlatilloIH {
    
    private String nombre;
    private Puesto puesto;
    
    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Puesto> getPuestos(){
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        return puestoCtrl.buscarPuestos();
    }
    
    public List<Platillo> getPlatillos(){
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        return puestoCtrl.buscarPlatillos(puesto);
    }
    
    public void agregaPlatillo(Puesto psto){
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        Platillo plat = new Platillo();
        plat.setNombre(nombre);
        //No sé por qué recibe el puesto pero supongo que lo hace bien
        plat.setIdPuesto(psto);
        puestoCtrl.agregaPlatillo(plat);
    }
}
