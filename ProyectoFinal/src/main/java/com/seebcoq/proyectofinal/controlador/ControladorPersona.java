package com.seebcoq.proyectofinal.controlador;

import com.seebcoq.proyectofinal.modelo.Persona;

import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ControladorPersona{

  private EntityManagerFactory emf;

  public ControladorPersona(){
      emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
  }

  public void ControladorPersonaCerrar(){
    emf.close();
  }

  public void guardarPersona(String nombre, String apPaterno,  String apMaterno, String correo, String contraseña, String nombreDeUsuario){
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
      Persona p = new Persona(nombre,apPaterno,apMaterno,correo,contraseña,nombreDeUsuario);
      em.persist(p);
      em.getTransaction().commit();
      em.close();
  }

  public boolean buscarCorreo(String correo){
    EntityManager em = emf.createEntityManager();
    TypedQuery<Persona> q1 = em.createNamedQuery( "Persona.findByCorreo", Persona.class).setParameter("correo", correo);
    List<Persona> ps = q1.getResultList();
    return !ps.isEmpty();
  }

}
