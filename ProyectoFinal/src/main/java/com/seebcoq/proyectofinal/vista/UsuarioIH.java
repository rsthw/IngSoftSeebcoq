package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import com.seebcoq.proyectofinal.modelo.Persona;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author valeria
 */
@ManagedBean(name = "usuarioIH")
@SessionScoped
public class UsuarioIH {
    private String email;
    private ControladorPersona personaCtrl;
    private String nuevo_nombre;
    private String nombreDeUsuario;

    private String nombre;

    private String apellidoP;

    private String apellidoM;

    private String password;
    
    private Persona persona;

public UsuarioIH(){
  HttpSession hs = UtilidadesSesion.getSession();
  persona = (Persona) hs.getAttribute("persona_usuario");
}


    @PostConstruct
    public void init() {
        personaCtrl = new ControladorPersona();
        persona = personaCtrl.buscarPersona(persona.getCorreo(), persona.getContraseña());
    }

        public String getNuevo_nombre() {
            return nuevo_nombre;
        }
        public void setNuevo_nombre(String nuevo_nombre) {
            this.nuevo_nombre = nuevo_nombre;
        }



        public String getNombreDeUsuario() {
          String s = persona.getNombreDeUsuario();
            return s;
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
            String s = persona.getNombre();
            return s;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellidoM() {
            return persona.getApMaterno();
        }
        public void setApellidoM(String apellidoM) {
            this.apellidoM = apellidoM;
        }

        public String getApellidoP() {
            return persona.getApPaterno();
        }
        public void setApellidoP(String apellidoP) {
            this.apellidoP = apellidoP;
        }

        public String getPassword() {
            return persona.getContraseña();
        }

        public void setPassword(String password) {
            this.password = password;
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

   public void modificaUsuario(){
     System.out.println("holi");
       personaCtrl.cambiarUsername(nombreDeUsuario, nombre, apellidoP, apellidoM, password, persona);
       //personaCtrl.ControladorPersonaCerrar();
       System.out.println("bye");
   }
}