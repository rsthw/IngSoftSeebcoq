package com.seebcoq.proyectofinal.vista;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@ManagedBean
@ViewScoped
public class MapHandler {

    private MapModel advancedModel;

    private Marker marker;

    private String nombre;

    private double lat;

    private double lng;

    @PostConstruct
    public void init() {
        advancedModel = new DefaultMapModel();
//Lat:19.324499386445776, Lng:-99.17937085032463
//        Lat:19.324328359583355, Lng:-99.17934268712997

        //Shared coordinates
        //con ayuda de una lista puedes sacar los lugares conocidos de la base de datos y
        //crear marcadores con dicha info, ojo no usar el addMarker() que ese sirve para agregar lugares a la db.
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        System.out.println(marker.getTitle());
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
