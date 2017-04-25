package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.EmailValidador;
import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


public class CreaSesionIH {

    private String nombreDeUsuario;

    private String nombre;

    private String apellidoP;

    private String apellidoM;

    private String email;

    private String password;

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }
    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoM() {
        return apellidoM;
    }
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getApellidoP() {
        return apellidoP;
    }
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String creaCuenta(){
      ControladorPersona cp = new ControladorPersona();
      if(cp.existeCorreo(email)){
        FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN,
                  "El correo con el que intenta crear cuenta ya existe.",
                  "Por favor, intente iniciar sesion con su correo y contasenia."));
                  return "";
      }else{
        cp.guardarPersona(nombre,apellidoP,apellidoM,email,password,nombreDeUsuario);
      }
      cp.ControladorPersonaCerrar();
      return "exito";
    }
}
