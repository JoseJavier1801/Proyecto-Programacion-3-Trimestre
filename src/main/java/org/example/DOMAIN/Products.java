package org.example.DOMAIN;

public class Products {

    private int id;
    private String descripcion;
    private int Stock;

    private double precio;

    private Admin id_admin;

    public Products(int id, String descripcion, int stock, double precio, Admin id_admin) {
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

    public Admin getId_admin() {
        return id_admin;
    }

    public void setId_admin(Admin id_admin) {
        this.id_admin = id_admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return id == products.id;
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
