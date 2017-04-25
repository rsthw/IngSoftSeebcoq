package com.seebcoq.proyectofinal.vista;


import com.seebcoq.proyectofinal.controlador.ControladorPuesto;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import com.seebcoq.proyectofinal.modelo.Puesto;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;


@ManagedBean
@SessionScoped
public class MapHandler {

    private MapModel advancedModel;
    private PuestoJpaController puestoCtrl;
    private Marker marker;
    private String nombre;
    private double lat;
    private double lng;
    private UploadedFile imagen;
    

    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
        advancedModel = new DefaultMapModel();
        puestoCtrl = new PuestoJpaController(emf);

        List<Puesto> puestos = puestoCtrl.findPuestoEntities();
        for (Puesto puesto : puestos) {
            Double latitud = puesto.getLatitud();
            Double longitud = puesto.getLongitud();
            String nombre = puesto.getNombre();
            
            advancedModel.addOverlay(new Marker(new LatLng(latitud, longitud), nombre, puesto.getIdPuesto()));
        }
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        System.out.println(marker.getTitle());
        
        HttpSession hs = UtilidadesSesion.getSession();
        hs.setAttribute("puestoId", (Long)marker.getData());
    }

    public Marker getMarker() {
        return marker;
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

    public void agregarPuesto() throws IOException{
        Marker marker = new Marker(new LatLng(lat, lng), nombre);
        //aqui se agrega a la base de datos
        advancedModel.addOverlay(marker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        Puesto p = new Puesto();
        p.setNombre(nombre);
        p.setLatitud(lat);
        p.setLongitud(lng);
        p.setCalificacion(0.0);
        //Ruta donde se guardará la imagen y ruta ue se guardará en la BD
        Path folder = Paths.get("/home/valeria/NetBeansProjects/ProyectoFinal/src/main/webapp/resources/images");
        String filename = FilenameUtils.getBaseName(imagen.getFileName());
        String extension = FilenameUtils.getExtension(imagen.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        InputStream input = imagen.getInputstream();
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        p.setImagen(file.toString());
        puestoCtrl.agregaPuesto(p);
    }

    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), nombre);
        //aqui se agrega a la base de datos
        advancedModel.addOverlay(marker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }

}
    

