package com.example.carlo.recetapp.ClasesAntiguas;

/**
 * Created by carlo on 22/08/2016.
 */
public class Ingrediente {

    private int id;
    private String nombre;
    private String descripcion_nutricional;


    public Ingrediente(int id, String nombre, String descripcion_nutricional) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion_nutricional = descripcion_nutricional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion_nutricional() {
        return descripcion_nutricional;
    }

    public void setDescripcion_nutricional(String descripcion_nutricional) {
        this.descripcion_nutricional = descripcion_nutricional;
    }
}
