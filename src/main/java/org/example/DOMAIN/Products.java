package org.example.DOMAIN;

import java.util.Objects;

/**
 * Clase Products que tiene los atributos que almacenan los datos que introduce el usuario
 */

public class Products {

    private int id;

    private String name;
    private String description;
    private int Stock;

    private double price;

    private Admin id_admin;

    public Products(int id, String name, String description, int stock, double price, Admin id_admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        Stock = stock;
        this.price = price;
        this.id_admin = id_admin;
    }

    public Products() {
        this(0," ","",0,0,null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        return id == products.id && Objects.equals(id_admin, products.id_admin);
    }
    @Override
    public String toString() {
        return  "name:" + name;

    }


}
