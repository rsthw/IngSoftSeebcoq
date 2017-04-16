/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

/**
 *
 * @author slf
 */
@ManagedBean
@ViewScoped
public class PuestoIH {
    private ControladorPuesto puestoCtrl;
    private Puesto puesto;
    private Long id;
    
    
    
    public PuestoIH(){
        HttpSession hs = UtilidadesSesion.getSession();
        id = (Long) hs.getAttribute("puestoId");
    }
    
    @PostConstruct
    public void init() {
        puestoCtrl = new ControladorPuesto();
        puesto = puestoCtrl.buscarPuesto(id);
    }
    
    public List<Comentario> getComentarios(){
        return puestoCtrl.buscarComentarios(puesto);
    }
    
    public String getNombre(){
        return puesto.getNombre();
    }
    public List<Puesto> getPuestos(){
        return puestoCtrl.buscarPuestos();
    }
}