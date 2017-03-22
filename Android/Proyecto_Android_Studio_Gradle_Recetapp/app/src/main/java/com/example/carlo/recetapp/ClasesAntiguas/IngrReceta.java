package com.example.carlo.recetapp.ClasesAntiguas;

/**
 * Created by carlo on 22/08/2016.
 */
public class IngrReceta {

    private int id;
    private int cantidad;
    private int medida;
    private Ingrediente ingrediente;

    public IngrReceta(int id, int cantidad, int medida, Ingrediente ingrediente) {
        this.id = id;
        this.cantidad = cantidad;
        this.medida = medida;
        this.ingrediente = ingrediente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
