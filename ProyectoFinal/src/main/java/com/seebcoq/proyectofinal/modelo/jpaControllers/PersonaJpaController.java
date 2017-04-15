/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.modelo.jpaControllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seebcoq.proyectofinal.modelo.Calificacion;
import java.util.ArrayList;
import java.util.List;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Persona;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.IllegalOrphanException;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getCalificacionList() == null) {
            persona.setCalificacionList(new ArrayList<Calificacion>());
        }
        if (persona.getComentarioList() == null) {
            persona.setComentarioList(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : persona.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalificacionPK());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            persona.setCalificacionList(attachedCalificacionList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : persona.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getIdComentario());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            persona.setComentarioList(attachedComentarioList);
            em.persist(persona);
            for (Calificacion calificacionListCalificacion : persona.getCalificacionList()) {
                Persona oldPersonaOfCalificacionListCalificacion = calificacionListCalificacion.getPersona();
                calificacionListCalificacion.setPersona(persona);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldPersonaOfCalificacionListCalificacion != null) {
                    oldPersonaOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldPersonaOfCalificacionListCalificacion = em.merge(oldPersonaOfCalificacionListCalificacion);
                }
            }
            for (Comentario comentarioListComentario : persona.getComentarioList()) {
                Persona oldIdPersonaOfComentarioListComentario = comentarioListComentario.getIdPersona();
                comentarioListComentario.setIdPersona(persona);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldIdPersonaOfComentarioListComentario != null) {
                    oldIdPersonaOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldIdPersonaOfComentarioListComentario = em.merge(oldIdPersonaOfComentarioListComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            List<Calificacion> calificacionListOld = persistentPersona.getCalificacionList();
            List<Calificacion> calificacionListNew = persona.getCalificacionList();
            List<Comentario> comentarioListOld = persistentPersona.getComentarioList();
            List<Comentario> comentarioListNew = persona.getComentarioList();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its persona field is not nullable.");
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its idPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalificacionPK());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            persona.setCalificacionList(calificacionListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getIdComentario());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            persona.setComentarioList(comentarioListNew);
            persona = em.merge(persona);
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Persona oldPersonaOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getPersona();
                    calificacionListNewCalificacion.setPersona(persona);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldPersonaOfCalificacionListNewCalificacion != null && !oldPersonaOfCalificacionListNewCalificacion.equals(persona)) {
                        oldPersonaOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldPersonaOfCalificacionListNewCalificacion = em.merge(oldPersonaOfCalificacionListNewCalificacion);
                    }
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Persona oldIdPersonaOfComentarioListNewComentario = comentarioListNewComentario.getIdPersona();
                    comentarioListNewComentario.setIdPersona(persona);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldIdPersonaOfComentarioListNewComentario != null && !oldIdPersonaOfComentarioListNewComentario.equals(persona)) {
                        oldIdPersonaOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldIdPersonaOfComentarioListNewComentario = em.merge(oldIdPersonaOfComentarioListNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionListOrphanCheck = persona.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable persona field.");
            }
            List<Comentario> comentarioListOrphanCheck = persona.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
