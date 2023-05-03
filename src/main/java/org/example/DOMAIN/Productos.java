package org.example.DOMAIN;

import java.util.Objects;

public class Productos {

    private int id;
    private String descripcion;
    private int Stock;

    private double precio;

    private Administrador id_admin;

    public Productos(int id, String descripcion, int stock, double precio, Administrador id_admin) {
        this.id = id;
        this.descripcion = descripcion;
        Stock = stock;
        this.precio = precio;
        this.id_admin = id_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Administrador getId_admin() {
        return id_admin;
    }

    public void setId_admin(Administrador id_admin) {
        this.id_admin = id_admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Productos productos = (Productos) o;
        return id == productos.id;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", Stock=" + Stock +
                ", precio=" + precio +
                ", id_admin=" + id_admin +
                '}';
    }
}
