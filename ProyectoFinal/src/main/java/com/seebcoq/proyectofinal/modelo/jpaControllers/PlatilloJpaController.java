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
import com.seebcoq.proyectofinal.modelo.Alimento;
import com.seebcoq.proyectofinal.modelo.Platillo;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author slf
 */
public class PlatilloJpaController implements Serializable {

    public PlatilloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Platillo platillo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alimento idAlimento = platillo.getIdAlimento();
            if (idAlimento != null) {
                idAlimento = em.getReference(idAlimento.getClass(), idAlimento.getNIdAlimento());
                platillo.setIdAlimento(idAlimento);
            }
            em.persist(platillo);
            if (idAlimento != null) {
                idAlimento.getPlatilloCollection().add(platillo);
                idAlimento = em.merge(idAlimento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Platillo platillo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Platillo persistentPlatillo = em.find(Platillo.class, platillo.getIdPlatillo());
            Alimento idAlimentoOld = persistentPlatillo.getIdAlimento();
            Alimento idAlimentoNew = platillo.getIdAlimento();
            if (idAlimentoNew != null) {
                idAlimentoNew = em.getReference(idAlimentoNew.getClass(), idAlimentoNew.getNIdAlimento());
                platillo.setIdAlimento(idAlimentoNew);
            }
            platillo = em.merge(platillo);
            if (idAlimentoOld != null && !idAlimentoOld.equals(idAlimentoNew)) {
                idAlimentoOld.getPlatilloCollection().remove(platillo);
                idAlimentoOld = em.merge(idAlimentoOld);
            }
            if (idAlimentoNew != null && !idAlimentoNew.equals(idAlimentoOld)) {
                idAlimentoNew.getPlatilloCollection().add(platillo);
                idAlimentoNew = em.merge(idAlimentoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = platillo.getIdPlatillo();
                if (findPlatillo(id) == null) {
                    throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.");
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
            Platillo platillo;
            try {
                platillo = em.getReference(Platillo.class, id);
                platillo.getIdPlatillo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.", enfe);
            }
            Alimento idAlimento = platillo.getIdAlimento();
            if (idAlimento != null) {
                idAlimento.getPlatilloCollection().remove(platillo);
                idAlimento = em.merge(idAlimento);
            }
            em.remove(platillo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Platillo> findPlatilloEntities() {
        return findPlatilloEntities(true, -1, -1);
    }

    public List<Platillo> findPlatilloEntities(int maxResults, int firstResult) {
        return findPlatilloEntities(false, maxResults, firstResult);
    }

    private List<Platillo> findPlatilloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Platillo.class));
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

    public Platillo findPlatillo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Platillo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlatilloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Platillo> rt = cq.from(Platillo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
