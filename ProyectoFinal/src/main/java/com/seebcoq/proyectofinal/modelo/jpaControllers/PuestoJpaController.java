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
import com.seebcoq.proyectofinal.modelo.Platillo;
import java.util.ArrayList;
import java.util.List;
import com.seebcoq.proyectofinal.modelo.Calificacion;
import com.seebcoq.proyectofinal.modelo.Comentario;
import com.seebcoq.proyectofinal.modelo.Menu;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.IllegalOrphanException;
import com.seebcoq.proyectofinal.modelo.jpaControllers.exceptions.NonexistentEntityException;
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
        if (puesto.getPlatilloList() == null) {
            puesto.setPlatilloList(new ArrayList<Platillo>());
        }
        if (puesto.getCalificacionList() == null) {
            puesto.setCalificacionList(new ArrayList<Calificacion>());
        }
        if (puesto.getComentarioList() == null) {
            puesto.setComentarioList(new ArrayList<Comentario>());
        }
        if (puesto.getMenuList() == null) {
            puesto.setMenuList(new ArrayList<Menu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Platillo> attachedPlatilloList = new ArrayList<Platillo>();
            for (Platillo platilloListPlatilloToAttach : puesto.getPlatilloList()) {
                platilloListPlatilloToAttach = em.getReference(platilloListPlatilloToAttach.getClass(), platilloListPlatilloToAttach.getIdPlatillo());
                attachedPlatilloList.add(platilloListPlatilloToAttach);
            }
            puesto.setPlatilloList(attachedPlatilloList);
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : puesto.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalificacionPK());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            puesto.setCalificacionList(attachedCalificacionList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : puesto.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getIdComentario());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            puesto.setComentarioList(attachedComentarioList);
            List<Menu> attachedMenuList = new ArrayList<Menu>();
            for (Menu menuListMenuToAttach : puesto.getMenuList()) {
                menuListMenuToAttach = em.getReference(menuListMenuToAttach.getClass(), menuListMenuToAttach.getIdMenu());
                attachedMenuList.add(menuListMenuToAttach);
            }
            puesto.setMenuList(attachedMenuList);
            em.persist(puesto);
            for (Platillo platilloListPlatillo : puesto.getPlatilloList()) {
                Puesto oldIdPuestoOfPlatilloListPlatillo = platilloListPlatillo.getIdPuesto();
                platilloListPlatillo.setIdPuesto(puesto);
                platilloListPlatillo = em.merge(platilloListPlatillo);
                if (oldIdPuestoOfPlatilloListPlatillo != null) {
                    oldIdPuestoOfPlatilloListPlatillo.getPlatilloList().remove(platilloListPlatillo);
                    oldIdPuestoOfPlatilloListPlatillo = em.merge(oldIdPuestoOfPlatilloListPlatillo);
                }
            }
            for (Calificacion calificacionListCalificacion : puesto.getCalificacionList()) {
                Puesto oldPuestoOfCalificacionListCalificacion = calificacionListCalificacion.getPuesto();
                calificacionListCalificacion.setPuesto(puesto);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldPuestoOfCalificacionListCalificacion != null) {
                    oldPuestoOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldPuestoOfCalificacionListCalificacion = em.merge(oldPuestoOfCalificacionListCalificacion);
                }
            }
            for (Comentario comentarioListComentario : puesto.getComentarioList()) {
                Puesto oldIdPuestoOfComentarioListComentario = comentarioListComentario.getIdPuesto();
                comentarioListComentario.setIdPuesto(puesto);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldIdPuestoOfComentarioListComentario != null) {
                    oldIdPuestoOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldIdPuestoOfComentarioListComentario = em.merge(oldIdPuestoOfComentarioListComentario);
                }
            }
            for (Menu menuListMenu : puesto.getMenuList()) {
                Puesto oldIdPuestoOfMenuListMenu = menuListMenu.getIdPuesto();
                menuListMenu.setIdPuesto(puesto);
                menuListMenu = em.merge(menuListMenu);
                if (oldIdPuestoOfMenuListMenu != null) {
                    oldIdPuestoOfMenuListMenu.getMenuList().remove(menuListMenu);
                    oldIdPuestoOfMenuListMenu = em.merge(oldIdPuestoOfMenuListMenu);
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
            List<Platillo> platilloListOld = persistentPuesto.getPlatilloList();
            List<Platillo> platilloListNew = puesto.getPlatilloList();
            List<Calificacion> calificacionListOld = persistentPuesto.getCalificacionList();
            List<Calificacion> calificacionListNew = puesto.getCalificacionList();
            List<Comentario> comentarioListOld = persistentPuesto.getComentarioList();
            List<Comentario> comentarioListNew = puesto.getComentarioList();
            List<Menu> menuListOld = persistentPuesto.getMenuList();
            List<Menu> menuListNew = puesto.getMenuList();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its puesto field is not nullable.");
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its idPuesto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Platillo> attachedPlatilloListNew = new ArrayList<Platillo>();
            for (Platillo platilloListNewPlatilloToAttach : platilloListNew) {
                platilloListNewPlatilloToAttach = em.getReference(platilloListNewPlatilloToAttach.getClass(), platilloListNewPlatilloToAttach.getIdPlatillo());
                attachedPlatilloListNew.add(platilloListNewPlatilloToAttach);
            }
            platilloListNew = attachedPlatilloListNew;
            puesto.setPlatilloList(platilloListNew);
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalificacionPK());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            puesto.setCalificacionList(calificacionListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getIdComentario());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            puesto.setComentarioList(comentarioListNew);
            List<Menu> attachedMenuListNew = new ArrayList<Menu>();
            for (Menu menuListNewMenuToAttach : menuListNew) {
                menuListNewMenuToAttach = em.getReference(menuListNewMenuToAttach.getClass(), menuListNewMenuToAttach.getIdMenu());
                attachedMenuListNew.add(menuListNewMenuToAttach);
            }
            menuListNew = attachedMenuListNew;
            puesto.setMenuList(menuListNew);
            puesto = em.merge(puesto);
            for (Platillo platilloListOldPlatillo : platilloListOld) {
                if (!platilloListNew.contains(platilloListOldPlatillo)) {
                    platilloListOldPlatillo.setIdPuesto(null);
                    platilloListOldPlatillo = em.merge(platilloListOldPlatillo);
                }
            }
            for (Platillo platilloListNewPlatillo : platilloListNew) {
                if (!platilloListOld.contains(platilloListNewPlatillo)) {
                    Puesto oldIdPuestoOfPlatilloListNewPlatillo = platilloListNewPlatillo.getIdPuesto();
                    platilloListNewPlatillo.setIdPuesto(puesto);
                    platilloListNewPlatillo = em.merge(platilloListNewPlatillo);
                    if (oldIdPuestoOfPlatilloListNewPlatillo != null && !oldIdPuestoOfPlatilloListNewPlatillo.equals(puesto)) {
                        oldIdPuestoOfPlatilloListNewPlatillo.getPlatilloList().remove(platilloListNewPlatillo);
                        oldIdPuestoOfPlatilloListNewPlatillo = em.merge(oldIdPuestoOfPlatilloListNewPlatillo);
                    }
                }
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Puesto oldPuestoOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getPuesto();
                    calificacionListNewCalificacion.setPuesto(puesto);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldPuestoOfCalificacionListNewCalificacion != null && !oldPuestoOfCalificacionListNewCalificacion.equals(puesto)) {
                        oldPuestoOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldPuestoOfCalificacionListNewCalificacion = em.merge(oldPuestoOfCalificacionListNewCalificacion);
                    }
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Puesto oldIdPuestoOfComentarioListNewComentario = comentarioListNewComentario.getIdPuesto();
                    comentarioListNewComentario.setIdPuesto(puesto);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldIdPuestoOfComentarioListNewComentario != null && !oldIdPuestoOfComentarioListNewComentario.equals(puesto)) {
                        oldIdPuestoOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldIdPuestoOfComentarioListNewComentario = em.merge(oldIdPuestoOfComentarioListNewComentario);
                    }
                }
            }
            for (Menu menuListOldMenu : menuListOld) {
                if (!menuListNew.contains(menuListOldMenu)) {
                    menuListOldMenu.setIdPuesto(null);
                    menuListOldMenu = em.merge(menuListOldMenu);
                }
            }
            for (Menu menuListNewMenu : menuListNew) {
                if (!menuListOld.contains(menuListNewMenu)) {
                    Puesto oldIdPuestoOfMenuListNewMenu = menuListNewMenu.getIdPuesto();
                    menuListNewMenu.setIdPuesto(puesto);
                    menuListNewMenu = em.merge(menuListNewMenu);
                    if (oldIdPuestoOfMenuListNewMenu != null && !oldIdPuestoOfMenuListNewMenu.equals(puesto)) {
                        oldIdPuestoOfMenuListNewMenu.getMenuList().remove(menuListNewMenu);
                        oldIdPuestoOfMenuListNewMenu = em.merge(oldIdPuestoOfMenuListNewMenu);
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
            List<Calificacion> calificacionListOrphanCheck = puesto.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable puesto field.");
            }
            List<Comentario> comentarioListOrphanCheck = puesto.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable idPuesto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Platillo> platilloList = puesto.getPlatilloList();
            for (Platillo platilloListPlatillo : platilloList) {
                platilloListPlatillo.setIdPuesto(null);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            List<Menu> menuList = puesto.getMenuList();
            for (Menu menuListMenu : menuList) {
                menuListMenu.setIdPuesto(null);
                menuListMenu = em.merge(menuListMenu);
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
