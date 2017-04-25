package com.seebcoq.proyectofinal.controlador;

import com.seebcoq.proyectofinal.modelo.Persona;

import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PersonaJpaController;

public class ControladorPersona{
  private EntityManagerFactory emf;

  public ControladorPersona(){
      emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
  }

  public void ControladorPersonaCerrar(){
    emf.close();
  }

  public void guardarPersona(String nombre, String apPaterno,  String apMaterno, String correo, String contraseña, String nombreDeUsuario){
      PersonaJpaController personaCtrl = new PersonaJpaController(emf);
      Persona persona = new Persona(nombre,apPaterno,apMaterno,correo,contraseña,nombreDeUsuario);
      
      personaCtrl.create(persona);
  }
  
  public void borrarPersona(String correo){
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    TypedQuery<Persona> q1 = em.createNamedQuery( "Persona.findByCorreo", Persona.class).setParameter("correo", correo);
    List<Persona> ps = q1.getResultList();
    Persona p=ps.get(0);
    em.remove(p);
    em.getTransaction().commit();
    em.close();

  }

  public boolean existeCorreo(String correo){
    EntityManager em = emf.createEntityManager();
    TypedQuery<Persona> q1 = em.createNamedQuery( "Persona.findByCorreo", Persona.class).setParameter("correo", correo);
    List<Persona> ps = q1.getResultList();
    return !ps.isEmpty();
  }

  public Persona buscarPersona(String correo, String password){
      EntityManager em = emf.createEntityManager();
      TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p WHERE p.correo = \'"+correo+"\' AND p.contraseña = \'"+password+"\'" , Persona.class);
      List<Persona> results = query.getResultList();
      
      if (results.isEmpty()) 
          return null;
      return results.get(0);
  }
  
  public Persona iniciarSesion(String correo, String password) {
        Persona persona = buscarPersona(correo, password);
        return persona;
  }
}
