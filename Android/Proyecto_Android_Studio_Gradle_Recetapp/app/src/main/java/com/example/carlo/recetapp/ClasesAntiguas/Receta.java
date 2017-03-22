package com.example.carlo.recetapp.ClasesAntiguas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlo on 21/08/2016.
 */
public class Receta {

    private int id;
    private String nombre;
    private String comensales;
    private String comentarios;
    private String tipo_receta;
    private String resumen;
    private List<Ingrediente> ingredientes;
    private ArrayList<String> pasos;

    public Receta(int id, String nombre, String comensales, String comentarios, String tipo_receta, String resumen, List<Ingrediente> ingredientes, ArrayList<String> pasos) {
        this.id = id;
        this.nombre = nombre;
        this.comensales = comensales;
        this.comentarios = comentarios;
        this.tipo_receta = tipo_receta;
        this.resumen = resumen;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
    }

    public Receta(String comentarios, String comensales, String nombre) {
        this.comentarios = comentarios;
        this.comensales = comensales;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComensales() {
        return comensales;
    }

    public void setComensales(String comensales) {
        this.comensales = comensales;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
