package com.seebcoq.proyectofinal.vista;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ValidacionCorreo {

    private String text;

    private String email;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
