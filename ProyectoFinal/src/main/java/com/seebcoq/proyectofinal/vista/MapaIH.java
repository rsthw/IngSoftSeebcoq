/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Menu;
//import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RateEvent;


/**
 *
 * @author slf
 */
@ManagedBean
@RequestScoped
public class MapaIH {
    private ControladorPuesto puestoCtrl;
    private List<Puesto> puestos;
    private String nombre;
    


    public MapaIH(){
        
    }

    @PostConstruct
    public void init() {
        puestoCtrl = new ControladorPuesto();
       
    }

  
/*
    public List<Calificacion> getCalificaciones(){
        return puestoCtrl.buscarCalificaciones(puesto);
    }
*/
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        nombre =  n;
    }

    
    
}
