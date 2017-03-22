package com.example.carlo.recetapp.ClasesAntiguas;

import java.util.List;

/**
 * Created by carlo on 22/08/2016.
 */
public class Usuario {

    private int id;
    private String mail;
    private List<Receta> recetas;

    public Usuario(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}
