/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import java.io.Serializable;
/**
 *
 * @author slf
 */
@ManagedBean
@ViewScoped
public class PuestoIH implements Serializable {
    private ControladorPuesto puestoCtrl;
    private Puesto puesto;
    private Long id;
    private String comentario;
    private List<Comentario> comentarios;
    
    public PuestoIH(){
        HttpSession hs = UtilidadesSesion.getSession();
        id = (Long) hs.getAttribute("puestoId");
    }
    
    public String getComentario(){
        return comentario;
    }
    public void setComentario(String comentario){
        this.comentario = comentario;
    }
    
    @PostConstruct
    public void init() {
        puestoCtrl = new ControladorPuesto();
        puesto = puestoCtrl.buscarPuesto(id);
        comentarios = puestoCtrl.buscarComentarios(puesto);
    }
    
    public List<Comentario> getComentarios(){
        return comentarios;
    }
    
    public String getNombre(){
        return puesto.getNombre();
    }
    public List<Puesto> getPuestos(){
        return puestoCtrl.buscarPuestos();
    }
    
    public void guardarComentario(){
        HttpSession hs = UtilidadesSesion.getSession();
        Persona persona =(Persona) hs.getAttribute("persona_usuario");
        if(persona != null){
            Comentario nuevo = puestoCtrl.guardarComentario(puesto, persona, comentario);
            comentarios.add(0, nuevo);
        }
        comentario = "";
    }
    
    public void eliminarComentario(Comentario com){
        puestoCtrl.eliminarComentario(com);
        comentarios.remove(com);
    }
}