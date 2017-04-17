package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author antonio
 */
@ManagedBean(name = "borrarUsuarioIH")
@SessionScoped
public class BorrarUsuarioIH {
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void borrarPersona(){
      ControladorPersona cp = new ControladorPersona();
      
      if(cp.buscarCorreo(email)){
          cp.borrarPersona(email);
           FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN,
                  "se ha borrado "+email,""));
         
      }else{
        FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN,
                  "El correo que quieres borrar no existe.",
                  "Por favor ingrese otro correo"));
   
      }
      cp.ControladorPersonaCerrar();
      
   } 
}
