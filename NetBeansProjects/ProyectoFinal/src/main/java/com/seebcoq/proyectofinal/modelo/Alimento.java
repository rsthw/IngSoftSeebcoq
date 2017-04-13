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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author slf
 */
@Entity
@Table(name = "ALIMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alimento.findAll", query = "SELECT a FROM Alimento a")
    , @NamedQuery(name = "Alimento.findByNIdAlimento", query = "SELECT a FROM Alimento a WHERE a.nIdAlimento = :nIdAlimento")
    , @NamedQuery(name = "Alimento.findBySNombre", query = "SELECT a FROM Alimento a WHERE a.sNombre = :sNombre")})
public class Alimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nIdAlimento")
    private Long nIdAlimento;
    @Size(max = 64)
    @Column(name = "sNombre")
    private String sNombre;
    @JoinTable(name = "Menu_Alimento", joinColumns = {
        @JoinColumn(name = "nIdAlimento", referencedColumnName = "nIdAlimento")}, inverseJoinColumns = {
        @JoinColumn(name = "nIdMenu", referencedColumnName = "idMenu")})
    @ManyToMany
    private Collection<Menu> menuCollection;
    @OneToMany(mappedBy = "idAlimento")
    private Collection<Platillo> platilloCollection;

    public Alimento() {
    }

    public Alimento(Long nIdAlimento) {
        this.nIdAlimento = nIdAlimento;
    }

    public Long getNIdAlimento() {
        return nIdAlimento;
    }

    public void setNIdAlimento(Long nIdAlimento) {
        this.nIdAlimento = nIdAlimento;
    }

    public String getSNombre() {
        return sNombre;
    }

    public void setSNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    public Collection<Platillo> getPlatilloCollection() {
        return platilloCollection;
    }

    public void setPlatilloCollection(Collection<Platillo> platilloCollection) {
        this.platilloCollection = platilloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nIdAlimento != null ? nIdAlimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alimento)) {
            return false;
        }
        Alimento other = (Alimento) object;
        if ((this.nIdAlimento == null && other.nIdAlimento != null) || (this.nIdAlimento != null && !this.nIdAlimento.equals(other.nIdAlimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seebcoq.proyectofinal.modelo.Alimento[ nIdAlimento=" + nIdAlimento + " ]";
    }
    
}
