package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import com.seebcoq.proyectofinal.controlador.SendMail;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class ContraseniaOlvidada {

        private String correoContrOlv;

      public String getCorreoContrOlv(){
        return correoContrOlv;
      }

      public void setCorreoContrOlv(String correoOlv){
        this.correoContrOlv = correoOlv;
      }

      public String contraseniaOlvidada(){
          SendMail mail = new SendMail();

          System.out.println(correoContrOlv);

          ControladorPersona cp = new ControladorPersona();
          String contr = cp.buscarPersonaCorreogPass(correoContrOlv);
          if(contr == null){
              mail.envioCorreo("Contrasenia Olvidada - FastFood", "FastFood :\n  No tenemos este correo registrado en nuestra base de datos, Ste recomendamos crear una cuenta en nustro sitio! .\n\n\n Saludos, el equipo de SeebCoq.",correoContrOlv);
              addWarning("No existe cuenta con dicho correo.");
              return "login";
          }
          mail.envioCorreo("Contrasenia Olvidada - FastFood", "FastFood :\n  Tu contrasenia es " + contr + ".\n\n\n Saludos, el equipo de SeebCoq.",correoContrOlv);
          addMessage("Correo enviado a " + correoContrOlv + " con contrasenia olvidada.");
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
