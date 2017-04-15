/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Persona;
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
public class PuestoIH {
    private ControladorPuesto puestoCtrl;
    private Puesto puesto;
    private String nombre;
    private Double calificacion;
    private String img;
    private Integer rating1;
    //Double r = puestoCtrl.buscarCalificacion(puesto);
    //private Integer rating4 =r.intValue();
    private Integer rating4 =3;
    private Persona persona;


    public PuestoIH(){
        nombre = "tacosCiencias";
    }

    @PostConstruct
    public void init() {
        puestoCtrl = new ControladorPuesto();
        puesto = puestoCtrl.buscarPuesto(nombre);
    }

    public List<Comentario> getComentarios(){
        return puestoCtrl.buscarComentarios(puesto);
    }

    public List<Menu> getMenu(){
        return puestoCtrl.buscarMenu(puesto);
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

    public Double getCalificacion(){
       Double r = puestoCtrl.buscarCalificacion(puesto);
        return r;
    }
/*
    public String getImagen(){
       String i = puestoCtrl.buscarImagen(puesto);
        return i;
    }*/


    public void setCalificacion(Double c){
        calificacion =  c;
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



    public Integer getRating4() {
        System.out.println("hagoalgo");
        return rating4;
    }

    public void setRating4(Integer rat4) {

        this.rating4 = rat4;
    }

    public void calificar(){
        //guardarCalificacion();
    }
}
