/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.*;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RateEvent;
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
    private Double calificacion;
    private String imagen;
    private Integer rating1;

         
    //Double r = puestoCtrl.buscarCalificacion(puesto);
    //private Integer rating4 =r.intValue();
    private Integer rating4;
    
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
        if(id != null){
            puesto = puestoCtrl.buscarPuesto(id);
            comentarios = puestoCtrl.buscarComentarios(puesto);
        }
    }

    public List<Comentario> getComentarios(){
        return comentarios;
    }


    public List<Puesto> getPuestos(){
        return puestoCtrl.buscarPuestos();
    }
    
    public String getNombre(){
        return puesto.getNombre();
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

    public List<Menu> getMenu(){
        return puestoCtrl.buscarMenu(puesto);
    }

    public List<Platillo> getPlatillos(){
        return puestoCtrl.buscarPlatillos(puesto);
    }

    public List<Calificacion> getCalificaciones(){
        return puestoCtrl.buscarCalificaciones(puesto);
    }

    

    public Double getCalificacion(){
       Double r = puestoCtrl.buscarCalificacion(puesto);
        return r;
    }

    public void setCalificacion(Double calificacion ){
        this.calificacion = calificacion;
    }

    public String getImagen(){
       String i = puestoCtrl.buscarImagen(puesto);
        return i;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }



   public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + ((Integer) rateEvent.getRating()).intValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Integer getRating1() {
        return rating1;
    }

public void setRating1(Integer rating1) {

        this.rating1 = rating1;
    }

    public Integer getRating4() {
        System.out.println("hagoalgo");
        Double r = puestoCtrl.buscarCalificacion(puesto);
    rating4 =r.intValue();
        return rating4;
    }

    public void setRating4(Integer rating4) {

        this.rating4 = rating4;
    }

    public String calificar(){
        HttpSession hs = UtilidadesSesion.getSession();
      Persona persona =(Persona) hs.getAttribute("persona_usuario");
        puestoCtrl.guardarCalificacion(rating1, puesto, persona);
        return "se guardo la calificacion";
    }

}
