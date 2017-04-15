package com.seebcoq.proyectofinal.vista;


import com.seebcoq.proyectofinal.controlador.dao.UsuarioDAO;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "iniciarSesionIH")
@SessionScoped
public class IniciarSesionIH implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uname;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public String iniciarSesion() {
		    int valid = UsuarioDAO.iniciarSesion(uname, password);
        String valor = "";
        HttpSession session = null;
        switch (valid){
          case 1:
          session = UtilidadesSesion.getSession();
          session.setAttribute("username", uname);
          valor = "user";
          break;
          case 2:
          session = UtilidadesSesion.getSession();
          session.setAttribute("username", uname);
          valor = "admin";
          break;
          default:
          FacesContext.getCurrentInstance().addMessage(null,
					            new FacesMessage(FacesMessage.SEVERITY_WARN,
							        "Correo o contrasenia incorrectos.",
							        "Por favor, intente nuevamente."));
			    valor = "error";
        }
        return valor;
}

    public String cerrarSesion() {
		    HttpSession session = UtilidadesSesion.getSession();
		    session.invalidate();
		    return "logout";
	}
}
