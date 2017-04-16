package com.seebcoq.proyectofinal.vista;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.seebcoq.proyectofinal.modelo.jpaControllers.PuestoJpaController;
import com.seebcoq.proyectofinal.modelo.Puesto;
import com.seebcoq.proyectofinal.controlador.UtilidadesSesion;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


@ManagedBean
@SessionScoped
public class MapHandler {

    private MapModel advancedModel;
    private PuestoJpaController puestoCtrl;
    private Marker marker;
    private String nombre;
    private double lat;
    private double lng;
    

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

    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), nombre);
        //aqui se agrega a la base de datos
        advancedModel.addOverlay(marker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }

}
    

