package com.seebcoq.proyectofinal.controlador.dao;
//import java.sql.*;

public class UsuarioDAO {
     public static int iniciarSesion(String user, String password) {


        /*Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select user, pass from userinfo where user= ? and pass= ? ");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                System.out.println(rs.getString("user"));
                return true;
            }
            else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }*/
        return 2;
    }
}