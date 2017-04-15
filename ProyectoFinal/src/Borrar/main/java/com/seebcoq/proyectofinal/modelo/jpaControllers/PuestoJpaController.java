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
import com.seebcoq.proyectofinal.modelo.Comentario;
import java.util.ArrayList;
import java.util.Collection;
import com.seebcoq.proyectofinal.modelo.Menu;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.IllegalOrphanException;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class PuestoJpaController implements Serializable {

    public PuestoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puesto puesto) {
        if (puesto.getComentarioCollection() == null) {
            puesto.setComentarioCollection(new ArrayList<Comentario>());
        }
        if (puesto.getMenuCollection() == null) {
            puesto.setMenuCollection(new ArrayList<Menu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : puesto.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdComentario());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            puesto.setComentarioCollection(attachedComentarioCollection);
            Collection<Menu> attachedMenuCollection = new ArrayList<Menu>();
            for (Menu menuCollectionMenuToAttach : puesto.getMenuCollection()) {
                menuCollectionMenuToAttach = em.getReference(menuCollectionMenuToAttach.getClass(), menuCollectionMenuToAttach.getIdMenu());
                attachedMenuCollection.add(menuCollectionMenuToAttach);
            }
            puesto.setMenuCollection(attachedMenuCollection);
            em.persist(puesto);
            for (Comentario comentarioCollectionComentario : puesto.getComentarioCollection()) {
                Puesto oldIdPuestoOfComentarioCollectionComentario = comentarioCollectionComentario.getIdPuesto();
                comentarioCollectionComentario.setIdPuesto(puesto);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldIdPuestoOfComentarioCollectionComentario != null) {
                    oldIdPuestoOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldIdPuestoOfComentarioCollectionComentario = em.merge(oldIdPuestoOfComentarioCollectionComentario);
                }
            }
            for (Menu menuCollectionMenu : puesto.getMenuCollection()) {
                Puesto oldIdPuestoOfMenuCollectionMenu = menuCollectionMenu.getIdPuesto();
                menuCollectionMenu.setIdPuesto(puesto);
                menuCollectionMenu = em.merge(menuCollectionMenu);
                if (oldIdPuestoOfMenuCollectionMenu != null) {
                    oldIdPuestoOfMenuCollectionMenu.getMenuCollection().remove(menuCollectionMenu);
                    oldIdPuestoOfMenuCollectionMenu = em.merge(oldIdPuestoOfMenuCollectionMenu);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puesto puesto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto persistentPuesto = em.find(Puesto.class, puesto.getIdPuesto());
            Collection<Comentario> comentarioCollectionOld = persistentPuesto.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = puesto.getComentarioCollection();
            Collection<Menu> menuCollectionOld = persistentPuesto.getMenuCollection();
            Collection<Menu> menuCollectionNew = puesto.getMenuCollection();
            List<String> illegalOrphanMessages = null;
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioCollectionOldComentario + " since its idPuesto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdComentario());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            puesto.setComentarioCollection(comentarioCollectionNew);
            Collection<Menu> attachedMenuCollectionNew = new ArrayList<Menu>();
            for (Menu menuCollectionNewMenuToAttach : menuCollectionNew) {
                menuCollectionNewMenuToAttach = em.getReference(menuCollectionNewMenuToAttach.getClass(), menuCollectionNewMenuToAttach.getIdMenu());
                attachedMenuCollectionNew.add(menuCollectionNewMenuToAttach);
            }
            menuCollectionNew = attachedMenuCollectionNew;
            puesto.setMenuCollection(menuCollectionNew);
            puesto = em.merge(puesto);
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Puesto oldIdPuestoOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getIdPuesto();
                    comentarioCollectionNewComentario.setIdPuesto(puesto);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldIdPuestoOfComentarioCollectionNewComentario != null && !oldIdPuestoOfComentarioCollectionNewComentario.equals(puesto)) {
                        oldIdPuestoOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldIdPuestoOfComentarioCollectionNewComentario = em.merge(oldIdPuestoOfComentarioCollectionNewComentario);
                    }
                }
            }
            for (Menu menuCollectionOldMenu : menuCollectionOld) {
                if (!menuCollectionNew.contains(menuCollectionOldMenu)) {
                    menuCollectionOldMenu.setIdPuesto(null);
                    menuCollectionOldMenu = em.merge(menuCollectionOldMenu);
                }
            }
            for (Menu menuCollectionNewMenu : menuCollectionNew) {
                if (!menuCollectionOld.contains(menuCollectionNewMenu)) {
                    Puesto oldIdPuestoOfMenuCollectionNewMenu = menuCollectionNewMenu.getIdPuesto();
                    menuCollectionNewMenu.setIdPuesto(puesto);
                    menuCollectionNewMenu = em.merge(menuCollectionNewMenu);
                    if (oldIdPuestoOfMenuCollectionNewMenu != null && !oldIdPuestoOfMenuCollectionNewMenu.equals(puesto)) {
                        oldIdPuestoOfMenuCollectionNewMenu.getMenuCollection().remove(menuCollectionNewMenu);
                        oldIdPuestoOfMenuCollectionNewMenu = em.merge(oldIdPuestoOfMenuCollectionNewMenu);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = puesto.getIdPuesto();
                if (findPuesto(id) == null) {
                    throw new NonexistentEntityException("The puesto with id " + id + " no longer exists.");
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
            Puesto puesto;
            try {
                puesto = em.getReference(Puesto.class, id);
                puesto.getIdPuesto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puesto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comentario> comentarioCollectionOrphanCheck = puesto.getComentarioCollection();
            for (Comentario comentarioCollectionOrphanCheckComentario : comentarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the Comentario " + comentarioCollectionOrphanCheckComentario + " in its comentarioCollection field has a non-nullable idPuesto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Menu> menuCollection = puesto.getMenuCollection();
            for (Menu menuCollectionMenu : menuCollection) {
                menuCollectionMenu.setIdPuesto(null);
                menuCollectionMenu = em.merge(menuCollectionMenu);
            }
            em.remove(puesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puesto> findPuestoEntities() {
        return findPuestoEntities(true, -1, -1);
    }

    public List<Puesto> findPuestoEntities(int maxResults, int firstResult) {
        return findPuestoEntities(false, maxResults, firstResult);
    }

    private List<Puesto> findPuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puesto.class));
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

    public Puesto findPuesto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puesto> rt = cq.from(Puesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
