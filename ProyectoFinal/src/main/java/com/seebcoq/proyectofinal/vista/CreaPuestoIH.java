/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seebcoq.proyectofinal.vista;

import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.modelo.Puesto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ulises
 */
@ManagedBean
public class CreaPuestoIH {
    
    private String nombre;
    private double lat;
    private double lng;
    private UploadedFile imagen;    
    private MapHandler mh;

    public MapHandler getMh() {
        return mh;
    }

    public void setMh(MapHandler mh) {
        this.mh = mh;
    }
    
    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
    
    public List<Puesto> getPuestos(){
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        return puestoCtrl.buscarPuestos();
    }    
    
    /**
     * Añade un puesto con los parametros dados
     */
    public void agregarPuesto() throws IOException{
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        Puesto p = new Puesto();
        p.setNombre(nombre);
        p.setLatitud(lat);
        p.setLongitud(lng);
        p.setLatitud(mh.getLat());
        p.setLongitud(mh.getLng());
        //Ruta donde se guardará la imagen y ruta ue se guardará en la BD
        Path folder = Paths.get(Variantes.URLImagenes);
        String filename = FilenameUtils.getBaseName(imagen.getFileName());
        String extension = FilenameUtils.getExtension(imagen.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        InputStream input = imagen.getInputstream();
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        p.setImagen(file.toString());
        puestoCtrl.agregaPuesto(p);
    }
    
}
