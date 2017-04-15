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
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.Alimento;
import com.seebcoq.proyectofinal.modelo.Menu;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class MenuJpaController implements Serializable {

    public MenuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menu menu) {
        if (menu.getAlimentoCollection() == null) {
            menu.setAlimentoCollection(new ArrayList<Alimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto idPuesto = menu.getIdPuesto();
            if (idPuesto != null) {
                idPuesto = em.getReference(idPuesto.getClass(), idPuesto.getIdPuesto());
                menu.setIdPuesto(idPuesto);
            }
            Collection<Alimento> attachedAlimentoCollection = new ArrayList<Alimento>();
            for (Alimento alimentoCollectionAlimentoToAttach : menu.getAlimentoCollection()) {
                alimentoCollectionAlimentoToAttach = em.getReference(alimentoCollectionAlimentoToAttach.getClass(), alimentoCollectionAlimentoToAttach.getNIdAlimento());
                attachedAlimentoCollection.add(alimentoCollectionAlimentoToAttach);
            }
            menu.setAlimentoCollection(attachedAlimentoCollection);
            em.persist(menu);
            if (idPuesto != null) {
                idPuesto.getMenuCollection().add(menu);
                idPuesto = em.merge(idPuesto);
            }
            for (Alimento alimentoCollectionAlimento : menu.getAlimentoCollection()) {
                alimentoCollectionAlimento.getMenuCollection().add(menu);
                alimentoCollectionAlimento = em.merge(alimentoCollectionAlimento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menu menu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu persistentMenu = em.find(Menu.class, menu.getIdMenu());
            Puesto idPuestoOld = persistentMenu.getIdPuesto();
            Puesto idPuestoNew = menu.getIdPuesto();
            Collection<Alimento> alimentoCollectionOld = persistentMenu.getAlimentoCollection();
            Collection<Alimento> alimentoCollectionNew = menu.getAlimentoCollection();
            if (idPuestoNew != null) {
                idPuestoNew = em.getReference(idPuestoNew.getClass(), idPuestoNew.getIdPuesto());
                menu.setIdPuesto(idPuestoNew);
            }
            Collection<Alimento> attachedAlimentoCollectionNew = new ArrayList<Alimento>();
            for (Alimento alimentoCollectionNewAlimentoToAttach : alimentoCollectionNew) {
                alimentoCollectionNewAlimentoToAttach = em.getReference(alimentoCollectionNewAlimentoToAttach.getClass(), alimentoCollectionNewAlimentoToAttach.getNIdAlimento());
                attachedAlimentoCollectionNew.add(alimentoCollectionNewAlimentoToAttach);
            }
            alimentoCollectionNew = attachedAlimentoCollectionNew;
            menu.setAlimentoCollection(alimentoCollectionNew);
            menu = em.merge(menu);
            if (idPuestoOld != null && !idPuestoOld.equals(idPuestoNew)) {
                idPuestoOld.getMenuCollection().remove(menu);
                idPuestoOld = em.merge(idPuestoOld);
            }
            if (idPuestoNew != null && !idPuestoNew.equals(idPuestoOld)) {
                idPuestoNew.getMenuCollection().add(menu);
                idPuestoNew = em.merge(idPuestoNew);
            }
            for (Alimento alimentoCollectionOldAlimento : alimentoCollectionOld) {
                if (!alimentoCollectionNew.contains(alimentoCollectionOldAlimento)) {
                    alimentoCollectionOldAlimento.getMenuCollection().remove(menu);
                    alimentoCollectionOldAlimento = em.merge(alimentoCollectionOldAlimento);
                }
            }
            for (Alimento alimentoCollectionNewAlimento : alimentoCollectionNew) {
                if (!alimentoCollectionOld.contains(alimentoCollectionNewAlimento)) {
                    alimentoCollectionNewAlimento.getMenuCollection().add(menu);
                    alimentoCollectionNewAlimento = em.merge(alimentoCollectionNewAlimento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = menu.getIdMenu();
                if (findMenu(id) == null) {
                    throw new NonexistentEntityException("The menu with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu menu;
            try {
                menu = em.getReference(Menu.class, id);
                menu.getIdMenu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menu with id " + id + " no longer exists.", enfe);
            }
            Puesto idPuesto = menu.getIdPuesto();
            if (idPuesto != null) {
                idPuesto.getMenuCollection().remove(menu);
                idPuesto = em.merge(idPuesto);
            }
            Collection<Alimento> alimentoCollection = menu.getAlimentoCollection();
            for (Alimento alimentoCollectionAlimento : alimentoCollection) {
                alimentoCollectionAlimento.getMenuCollection().remove(menu);
                alimentoCollectionAlimento = em.merge(alimentoCollectionAlimento);
            }
            em.remove(menu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menu> findMenuEntities() {
        return findMenuEntities(true, -1, -1);
    }

    public List<Menu> findMenuEntities(int maxResults, int firstResult) {
        return findMenuEntities(false, maxResults, firstResult);
    }

    private List<Menu> findMenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menu.class));
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

    public Menu findMenu(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menu.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menu> rt = cq.from(Menu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
