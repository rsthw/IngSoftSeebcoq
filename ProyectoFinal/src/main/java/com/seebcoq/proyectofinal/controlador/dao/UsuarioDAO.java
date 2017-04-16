package com.seebcoq.proyectofinal.controlador.dao;

import java.util.List;
import com.seebcoq.proyectofinal.controlador.ControladorPersona;
import com.seebcoq.proyectofinal.modelo.Persona;

public class UsuarioDAO {
     public static Integer iniciarSesion(String user, String password) {
        ControladorPersona cp = new ControladorPersona();
        List<Persona> lp = cp.buscarUsuario(user,password);
        if(lp == null)
          return null;
        Persona p = lp.remove(0);
        boolean admin = p.getEsAdministrador();
        if(admin)
          return 2;
        return 1;
    }
}
