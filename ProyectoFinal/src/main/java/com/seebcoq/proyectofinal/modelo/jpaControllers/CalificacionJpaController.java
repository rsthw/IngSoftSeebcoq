/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.modelo.jpaControllers;

import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.CalificacionPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) throws PreexistingEntityException, Exception {
        if (calificacion.getCalificacionPK() == null) {
            calificacion.setCalificacionPK(new CalificacionPK());
        }
        calificacion.getCalificacionPK().setIdPersona(calificacion.getPersona().getIdPersona());
        calificacion.getCalificacionPK().setIdPuesto(calificacion.getPuesto().getIdPuesto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto puesto = calificacion.getPuesto();
            if (puesto != null) {
                puesto = em.getReference(puesto.getClass(), puesto.getIdPuesto());
                calificacion.setPuesto(puesto);
            }
            Persona persona = calificacion.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                calificacion.setPersona(persona);
            }
            em.persist(calificacion);
            if (puesto != null) {
                puesto.getCalificacionList().add(calificacion);
                puesto = em.merge(puesto);
            }
            if (persona != null) {
                persona.getCalificacionList().add(calificacion);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalificacion(calificacion.getCalificacionPK()) != null) {
                throw new PreexistingEntityException("Calificacion " + calificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificacion calificacion) throws NonexistentEntityException, Exception {
        calificacion.getCalificacionPK().setIdPersona(calificacion.getPersona().getIdPersona());
        calificacion.getCalificacionPK().setIdPuesto(calificacion.getPuesto().getIdPuesto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getCalificacionPK());
            Puesto puestoOld = persistentCalificacion.getPuesto();
            Puesto puestoNew = calificacion.getPuesto();
            Persona personaOld = persistentCalificacion.getPersona();
            Persona personaNew = calificacion.getPersona();
            if (puestoNew != null) {
                puestoNew = em.getReference(puestoNew.getClass(), puestoNew.getIdPuesto());
                calificacion.setPuesto(puestoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                calificacion.setPersona(personaNew);
            }
            calificacion = em.merge(calificacion);
            if (puestoOld != null && !puestoOld.equals(puestoNew)) {
                puestoOld.getCalificacionList().remove(calificacion);
                puestoOld = em.merge(puestoOld);
            }
            if (puestoNew != null && !puestoNew.equals(puestoOld)) {
                puestoNew.getCalificacionList().add(calificacion);
                puestoNew = em.merge(puestoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getCalificacionList().remove(calificacion);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getCalificacionList().add(calificacion);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CalificacionPK id = calificacion.getCalificacionPK();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CalificacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getCalificacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            Puesto puesto = calificacion.getPuesto();
            if (puesto != null) {
                puesto.getCalificacionList().remove(calificacion);
                puesto = em.merge(puesto);
            }
            Persona persona = calificacion.getPersona();
            if (persona != null) {
                persona.getCalificacionList().remove(calificacion);
                persona = em.merge(persona);
            }
            em.remove(calificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Calificacion findCalificacion(CalificacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
