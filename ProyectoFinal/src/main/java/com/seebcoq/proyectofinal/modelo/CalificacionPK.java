/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author slf
 */
@Embeddable
public class CalificacionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPuesto")
    private long idPuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPersona")
    private long idPersona;

    public CalificacionPK() {
    }

    public CalificacionPK(long idPuesto, long idPersona) {
        this.idPuesto = idPuesto;
        this.idPersona = idPersona;
    }

    public long getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(long idPuesto) {
        this.idPuesto = idPuesto;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPuesto;
        hash += (int) idPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalificacionPK)) {
            return false;
        }
        CalificacionPK other = (CalificacionPK) object;
        if (this.idPuesto != other.idPuesto) {
            return false;
        }
        if (this.idPersona != other.idPersona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seebcoq.proyectofinal.modelo.CalificacionPK[ idPuesto=" + idPuesto + ", idPersona=" + idPersona + " ]";
    }
    
}
