package com.seebcoq.proyectofinal.controlador;

import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Calificacion;

import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PersonaJpaController;
import com.seebcoq.proyectofinal.modelo.jpaControllers.ComentarioJpaController;
import com.seebcoq.proyectofinal.modelo.jpaControllers.CalificacionJpaController;

public class ControladorPersona {

    private EntityManagerFactory emf;

    public ControladorPersona() {
        emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
    }

    public void ControladorPersonaCerrar() {
        emf.close();
    }

    public void guardarPersona(String nombre, String apPaterno, String apMaterno, String correo, String contraseña, String nombreDeUsuario) {
        PersonaJpaController personaCtrl = new PersonaJpaController(emf);
        Persona persona = new Persona(nombre, apPaterno, apMaterno, correo, contraseña, nombreDeUsuario);

        personaCtrl.create(persona);
    }

    public void borrarPersona(String correo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Persona> q1 = em.createNamedQuery("Persona.findByCorreo", Persona.class).setParameter("correo", correo);
        List<Persona> ps = q1.getResultList();
        Persona p = ps.get(0);

        List<Comentario> comentarios = p.getComentarioList();
        List<Calificacion> calificaciones = p.getCalificacionList();

        for (Comentario co : comentarios) {
            ComentarioJpaController cja = new ComentarioJpaController(emf);
            try {
                cja.destroy(co.getIdComentario());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Calificacion ca : calificaciones) {
            CalificacionJpaController cal = new CalificacionJpaController(emf);
            try {
                cal.destroy(ca.getCalificacionPK());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        em.remove(p);

        em.getTransaction().commit();
        em.close();

    }

    public boolean existeCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Persona> q1 = em.createNamedQuery("Persona.findByCorreo", Persona.class).setParameter("correo", correo);
        List<Persona> ps = q1.getResultList();
        return !ps.isEmpty();
    }

    public boolean buscarCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p WHERE p.correo = \'" + correo + "\'", Persona.class);
        List<Persona> results = query.getResultList();

        if (results.size() == 0) {
            return false;
        }
        return true;
    }

    public Persona buscarPersona(String correo, String password) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p WHERE p.correo = \'" + correo + "\' AND p.contraseña = \'" + password + "\'", Persona.class);
        List<Persona> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
    
    public String buscarPersonaCorreogPass(String correo) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p WHERE p.correo = \'" + correo + "\'", Persona.class);
        List<Persona> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0).getContraseña();
    }

    public Persona iniciarSesion(String correo, String password) {
        Persona persona = buscarPersona(correo, password);
        return persona;
    }


    public void cambiarUsername(String usuario, String nombre, String apellidoP, String apellidoM, String pass, Persona persona){
      PersonaJpaController personaCtrl = new PersonaJpaController(emf);
      persona.setNombreDeUsuario(usuario);
      persona.setNombre(nombre);
      persona.setApPaterno(apellidoP);
      persona.setApMaterno(apellidoM);
      persona.setContraseña(pass);
      try{
      personaCtrl.edit(persona);
      }
      catch (Exception e) {
                System.out.println("no se pudo");;


    }
}
}
