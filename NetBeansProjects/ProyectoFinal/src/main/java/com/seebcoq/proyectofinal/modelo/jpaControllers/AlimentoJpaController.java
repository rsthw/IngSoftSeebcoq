/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.modelo.jpaControllers;

import com.seebcoq.proyectofinal.modelo.Alimento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seebcoq.proyectofinal.modelo.Menu;
import java.util.ArrayList;
import java.util.Collection;
import com.seebcoq.proyectofinal.modelo.Platillo;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class AlimentoJpaController implements Serializable {

    public AlimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alimento alimento) {
        if (alimento.getMenuCollection() == null) {
            alimento.setMenuCollection(new ArrayList<Menu>());
        }
        if (alimento.getPlatilloCollection() == null) {
            alimento.setPlatilloCollection(new ArrayList<Platillo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Menu> attachedMenuCollection = new ArrayList<Menu>();
            for (Menu menuCollectionMenuToAttach : alimento.getMenuCollection()) {
                menuCollectionMenuToAttach = em.getReference(menuCollectionMenuToAttach.getClass(), menuCollectionMenuToAttach.getIdMenu());
                attachedMenuCollection.add(menuCollectionMenuToAttach);
            }
            alimento.setMenuCollection(attachedMenuCollection);
            Collection<Platillo> attachedPlatilloCollection = new ArrayList<Platillo>();
            for (Platillo platilloCollectionPlatilloToAttach : alimento.getPlatilloCollection()) {
                platilloCollectionPlatilloToAttach = em.getReference(platilloCollectionPlatilloToAttach.getClass(), platilloCollectionPlatilloToAttach.getIdPlatillo());
                attachedPlatilloCollection.add(platilloCollectionPlatilloToAttach);
            }
            alimento.setPlatilloCollection(attachedPlatilloCollection);
            em.persist(alimento);
            for (Menu menuCollectionMenu : alimento.getMenuCollection()) {
                menuCollectionMenu.getAlimentoCollection().add(alimento);
                menuCollectionMenu = em.merge(menuCollectionMenu);
            }
            for (Platillo platilloCollectionPlatillo : alimento.getPlatilloCollection()) {
                Alimento oldIdAlimentoOfPlatilloCollectionPlatillo = platilloCollectionPlatillo.getIdAlimento();
                platilloCollectionPlatillo.setIdAlimento(alimento);
                platilloCollectionPlatillo = em.merge(platilloCollectionPlatillo);
                if (oldIdAlimentoOfPlatilloCollectionPlatillo != null) {
                    oldIdAlimentoOfPlatilloCollectionPlatillo.getPlatilloCollection().remove(platilloCollectionPlatillo);
                    oldIdAlimentoOfPlatilloCollectionPlatillo = em.merge(oldIdAlimentoOfPlatilloCollectionPlatillo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alimento alimento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alimento persistentAlimento = em.find(Alimento.class, alimento.getNIdAlimento());
            Collection<Menu> menuCollectionOld = persistentAlimento.getMenuCollection();
            Collection<Menu> menuCollectionNew = alimento.getMenuCollection();
            Collection<Platillo> platilloCollectionOld = persistentAlimento.getPlatilloCollection();
            Collection<Platillo> platilloCollectionNew = alimento.getPlatilloCollection();
            Collection<Menu> attachedMenuCollectionNew = new ArrayList<Menu>();
            for (Menu menuCollectionNewMenuToAttach : menuCollectionNew) {
                menuCollectionNewMenuToAttach = em.getReference(menuCollectionNewMenuToAttach.getClass(), menuCollectionNewMenuToAttach.getIdMenu());
                attachedMenuCollectionNew.add(menuCollectionNewMenuToAttach);
            }
            menuCollectionNew = attachedMenuCollectionNew;
            alimento.setMenuCollection(menuCollectionNew);
            Collection<Platillo> attachedPlatilloCollectionNew = new ArrayList<Platillo>();
            for (Platillo platilloCollectionNewPlatilloToAttach : platilloCollectionNew) {
                platilloCollectionNewPlatilloToAttach = em.getReference(platilloCollectionNewPlatilloToAttach.getClass(), platilloCollectionNewPlatilloToAttach.getIdPlatillo());
                attachedPlatilloCollectionNew.add(platilloCollectionNewPlatilloToAttach);
            }
            platilloCollectionNew = attachedPlatilloCollectionNew;
            alimento.setPlatilloCollection(platilloCollectionNew);
            alimento = em.merge(alimento);
            for (Menu menuCollectionOldMenu : menuCollectionOld) {
                if (!menuCollectionNew.contains(menuCollectionOldMenu)) {
                    menuCollectionOldMenu.getAlimentoCollection().remove(alimento);
                    menuCollectionOldMenu = em.merge(menuCollectionOldMenu);
                }
            }
            for (Menu menuCollectionNewMenu : menuCollectionNew) {
                if (!menuCollectionOld.contains(menuCollectionNewMenu)) {
                    menuCollectionNewMenu.getAlimentoCollection().add(alimento);
                    menuCollectionNewMenu = em.merge(menuCollectionNewMenu);
                }
            }
            for (Platillo platilloCollectionOldPlatillo : platilloCollectionOld) {
                if (!platilloCollectionNew.contains(platilloCollectionOldPlatillo)) {
                    platilloCollectionOldPlatillo.setIdAlimento(null);
                    platilloCollectionOldPlatillo = em.merge(platilloCollectionOldPlatillo);
                }
            }
            for (Platillo platilloCollectionNewPlatillo : platilloCollectionNew) {
                if (!platilloCollectionOld.contains(platilloCollectionNewPlatillo)) {
                    Alimento oldIdAlimentoOfPlatilloCollectionNewPlatillo = platilloCollectionNewPlatillo.getIdAlimento();
                    platilloCollectionNewPlatillo.setIdAlimento(alimento);
                    platilloCollectionNewPlatillo = em.merge(platilloCollectionNewPlatillo);
                    if (oldIdAlimentoOfPlatilloCollectionNewPlatillo != null && !oldIdAlimentoOfPlatilloCollectionNewPlatillo.equals(alimento)) {
                        oldIdAlimentoOfPlatilloCollectionNewPlatillo.getPlatilloCollection().remove(platilloCollectionNewPlatillo);
                        oldIdAlimentoOfPlatilloCollectionNewPlatillo = em.merge(oldIdAlimentoOfPlatilloCollectionNewPlatillo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = alimento.getNIdAlimento();
                if (findAlimento(id) == null) {
                    throw new NonexistentEntityException("The alimento with id " + id + " no longer exists.");
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
            Alimento alimento;
            try {
                alimento = em.getReference(Alimento.class, id);
                alimento.getNIdAlimento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alimento with id " + id + " no longer exists.", enfe);
            }
            Collection<Menu> menuCollection = alimento.getMenuCollection();
            for (Menu menuCollectionMenu : menuCollection) {
                menuCollectionMenu.getAlimentoCollection().remove(alimento);
                menuCollectionMenu = em.merge(menuCollectionMenu);
            }
            Collection<Platillo> platilloCollection = alimento.getPlatilloCollection();
            for (Platillo platilloCollectionPlatillo : platilloCollection) {
                platilloCollectionPlatillo.setIdAlimento(null);
                platilloCollectionPlatillo = em.merge(platilloCollectionPlatillo);
            }
            em.remove(alimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alimento> findAlimentoEntities() {
        return findAlimentoEntities(true, -1, -1);
    }

    public List<Alimento> findAlimentoEntities(int maxResults, int firstResult) {
        return findAlimentoEntities(false, maxResults, firstResult);
    }

    private List<Alimento> findAlimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alimento.class));
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

    public Alimento findAlimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alimento> rt = cq.from(Alimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
