package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import com.seebcoq.proyectofinal.controlador.SendMail;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class Cambios{

        private String correoContrOlv = "amour.pale@gmail.com";
        private String name;

      public String getCorreoContrOlv(){
        return correoContrOlv;
      }

      public void setCorreoContrOlv(String correoOlv){
        this.correoContrOlv = correoOlv;
      }
      
       public String name(){
        return name;
      }

      public void setName(String name){
        this.name = name;
      }

    public String mandarCorreo(String nombre){
          SendMail mail = new SendMail();

          mail.envioCorreo("Cambios - FastFood", "Se agregó el siguiente puesto en la webapp FastFood :\n  " + nombre + ".\n Para ver mas detalles, ingrese a la aplicación. \n\n Saludos, el equipo de SeebCoq.",correoContrOlv);
          addMessage("Correo enviado a  con contrasenia olvidada.");
          return "login";
      }

       public void addMessage(String summary) {
          FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
          FacesContext.getCurrentInstance().addMessage(null, message);
      }

       public void addWarning(String summary) {
          FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary,  null);
          FacesContext.getCurrentInstance().addMessage(null, message);
      }

}
