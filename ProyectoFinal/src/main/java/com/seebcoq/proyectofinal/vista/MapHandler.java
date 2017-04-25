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
;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;



@ManagedBean
@ViewScoped
public class MapHandler {

    private MapModel advancedModel;
    private Marker marker;
    private String nombre;
    private double lat;
    private double lng;

    private PuestoJpaController puestoCtrl;

    private UploadedFile imagen;
    private Long id;
    private Puesto puesto;

    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
        advancedModel = new DefaultMapModel();
        puestoCtrl = new PuestoJpaController(emf);
        puesto = new Puesto();
        List<Puesto> puestos = puestoCtrl.findPuestoEntities();
        for (Puesto puesto : puestos) {
            Double latitud = puesto.getLatitud();
            Double longitud = puesto.getLongitud();
            String nombre = puesto.getNombre();

            advancedModel.addOverlay(new Marker(new LatLng(latitud, longitud), nombre, puesto.getIdPuesto()));
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        HttpSession hs = UtilidadesSesion.getSession();
        hs.setAttribute("puestoId", (Long) marker.getData());
    }

    public void agregarPuesto() throws IOException {
        if (lat != 0 && lng != 0) {
            ControladorPuesto puestoCtrl = new ControladorPuesto();
            Puesto p = new Puesto();
            p.setNombre(nombre);
            p.setLatitud(lat);
            p.setLongitud(lng);
            p.setCalificacion(0.0);
            // Se guarda la imagen si existe
            if(imagen.getSize() > 0){
                Path folder = Paths.get("/home/slf/Documents/Maven/IngSoftSeebcoq/ProyectoFinal/src/main/webapp/resources/images");
                String filename = FilenameUtils.getBaseName(imagen.getFileName());
                String extension = FilenameUtils.getExtension(imagen.getFileName());
                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                InputStream input = imagen.getInputstream();
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                p.setImagen(file.toString());
            } else {
                p.setImagen("");
            }
            puestoCtrl.agregaPuesto(p);
            
            // Se agrega el Marker al mapa, con el Id nuevo
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("comidaCienciasPersistentUnit");
            PuestoJpaController pja = new PuestoJpaController(emf);

            List<Puesto> pst = pja.findPuestoEntities();

            p = pst.get(pst.size() - 1);

            Marker marker = new Marker(new LatLng(lat, lng), nombre, puesto.getIdPuesto());
            advancedModel.addOverlay(marker);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Seleccionar algún lugar del mapa."));
        }
    }

    public void modificaPuesto() throws IOException {
        boolean seHacenCambios = false;
        // Agregando al mapa si se selecciona un lugar nuevo
        if (lat != 0 && lng != 0) {
            Marker marker = new Marker(new LatLng(lat, lng), puesto.getNombre(), puesto.getIdPuesto());
            advancedModel.addOverlay(marker);

            puesto.setLatitud(lat);
            puesto.setLongitud(lng);

            seHacenCambios = true;
        }
        // Se guarda la imagen si existe
        if (imagen != null && imagen.getSize() > 0) {
            Path folder = Paths.get("/home/slf/Documents/Maven/IngSoftSeebcoq/ProyectoFinal/src/main/webapp/resources/images");
            String filename = FilenameUtils.getBaseName(imagen.getFileName());
            String extension = FilenameUtils.getExtension(imagen.getFileName());
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            InputStream input = imagen.getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            puesto.setImagen(file.toString());

            seHacenCambios = true;
        }
        if (seHacenCambios) {
            try {
                puestoCtrl.edit(puesto);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), nombre);
        //aqui se agrega a la base de datos
        advancedModel.addOverlay(marker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }

    public void resetMarker(){
        lat = 0.0; lng = 0.0;
    }
    /* SETTERS & GETTERS */
    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        setPuesto(new ControladorPuesto().buscarPuesto(id));
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public List<Puesto> getPuestos() {
        ControladorPuesto puestoCtrl = new ControladorPuesto();
        return puestoCtrl.buscarPuestos();
    }

}
