package com.seebcoq.proyectofinal.vista;


import com.seebcoq.proyectofinal.controlador.dao.UsuarioDAO;
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
		    int valid = UsuarioDAO.login(user, pwd);
        string valor = "";
        switch (valid){
          case 1:
          HttpSession session = SessionUtils.getSession();
          session.setAttribute("username", user);
          valor = "user";
          break;
          case 2:
          HttpSession session = SessionUtils.getSession();
          session.setAttribute("username", user);
          valor = "admin";
          break;
          default:
          FacesContext.getCurrentInstance().addMessage(null,
					            new FacesMessage(FacesMessage.SEVERITY_WARN,
							        "Incorrect Username and Passowrd",
							        "Please enter correct username and Password"));
			    valor = "error";
        }
      return valor;
	}

    public String cerrarSesion() {
		    HttpSession session = SessionUtils.getSession();
		    session.invalidate();
		    return "logout";
	}
}
