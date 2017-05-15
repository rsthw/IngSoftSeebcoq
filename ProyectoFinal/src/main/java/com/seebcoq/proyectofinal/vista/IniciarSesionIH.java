package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import com.seebcoq.proyectofinal.controlador.SendMail;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import com.seebcoq.proyectofinal.modelo.Persona;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "iniciarSesionIH")
@SessionScoped
public class IniciarSesionIH implements Serializable {

    private static final long serialVersionUID = 1L;
    private Persona persona;
    
    private String correo;
    private String password;
    
    private Integer valid;
    private Boolean esAdmin;
    
    public Boolean getEsAdmin(){
        return esAdmin;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
    public Integer getValid() {
        return valid;
    }

    public String getUsuario(){
        return persona.getNombreDeUsuario();
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String iniciarSesion() {
        ControladorPersona personaCtrl = new ControladorPersona();
        persona = personaCtrl.iniciarSesion(correo, password);
        
        int estado;
        if(persona == null){
            estado = 0;
        } else{
            boolean admin = persona.getEsAdministrador();
            UtilidadesSesion.getSession().setAttribute("persona_usuario", persona);
            if(admin) estado = 2;
            else estado = 1;
        }
        personaCtrl.ControladorPersonaCerrar();
        
        valid = estado;
        
        String valor = "";
        HttpSession session = null;
        
        switch (estado){
            case 1: // usuario
                session = UtilidadesSesion.getSession();
                session.setAttribute("correo", correo);
                valor = "user";
                esAdmin = false;
                break;
            case 2: // admin
                session = UtilidadesSesion.getSession();
                session.setAttribute("correo", correo);
                valor = "admin";
                esAdmin = true;
                break;
            default: // error
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Correo o contrasenia incorrectos.",
                                "Por favor, intente nuevamente."));
                valor = "error";
                esAdmin = false;
        }
        if(valid == 0) valid = null;
        return "index.xhtml?faces-redirect=true";
}

    public String cerrarSesion() {
        HttpSession session = UtilidadesSesion.getSession();
        session.removeAttribute("correo");
        session.removeAttribute("persona_usuario");
        persona = null;
        valid = null;
        esAdmin = false;
        return "logout";
    }
    
    public void contraseniaOlvidada(ActionEvent actionEvent){
        SendMail mail = new SendMail();
        ControladorPersona cp = new ControladorPersona();
        String contr = cp.buscarPersonaCorreogPass(correo);
        if(contr == null){
            addWarning("No existe cuenta con dicho correo.");
            return;
        }
        mail.envioCorreo("Contrasenia Olvidada - FastFood", "FastFood :\n  Tu contrasenia es"+ contr +".\n\n\n Saludos, el equipo de SeebCoq.",correo);
        addMessage("Correo enviado a "+correo+" con contrasenia olvidada.");
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
