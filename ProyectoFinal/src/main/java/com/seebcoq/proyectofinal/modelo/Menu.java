/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author slf
 */
@Entity
@Table(name = "MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
    , @NamedQuery(name = "Menu.findByIdMenu", query = "SELECT m FROM Menu m WHERE m.idMenu = :idMenu")
    , @NamedQuery(name = "Menu.findByDiaDeLaSemana", query = "SELECT m FROM Menu m WHERE m.diaDeLaSemana = :diaDeLaSemana")
    , @NamedQuery(name = "Menu.findByDesayunoOComida", query = "SELECT m FROM Menu m WHERE m.desayunoOComida = :desayunoOComida")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMenu")
    private Long idMenu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "diaDeLaSemana")
    private String diaDeLaSemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "desayuno_o_comida")
    private String desayunoOComida;
    @ManyToMany(mappedBy = "menuCollection")
    private Collection<Alimento> alimentoCollection;
    @JoinColumn(name = "idPuesto", referencedColumnName = "idPuesto")
    @ManyToOne
    private Puesto idPuesto;

    public Menu() {
    }

    public Menu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Menu(Long idMenu, String diaDeLaSemana, String desayunoOComida) {
        this.idMenu = idMenu;
        this.diaDeLaSemana = diaDeLaSemana;
        this.desayunoOComida = desayunoOComida;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public String getDiaDeLaSemana() {
        return diaDeLaSemana;
    }

    public void setDiaDeLaSemana(String diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
    }

    public String getDesayunoOComida() {
        return desayunoOComida;
    }

    public void setDesayunoOComida(String desayunoOComida) {
        this.desayunoOComida = desayunoOComida;
    }

    @XmlTransient
    public Collection<Alimento> getAlimentoCollection() {
        return alimentoCollection;
    }

    public void setAlimentoCollection(Collection<Alimento> alimentoCollection) {
        this.alimentoCollection = alimentoCollection;
    }

    public Puesto getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Puesto idPuesto) {
        this.idPuesto = idPuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seebcoq.proyectofinal.modelo.Menu[ idMenu=" + idMenu + " ]";
    }
    
}
