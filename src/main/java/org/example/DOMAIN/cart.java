package org.example.DOMAIN;

import org.example.DAO.ProductsDAO;

import java.util.Date;

/***
 * Clase cart que tiene los atributos que almacenaran los datos
 */
public class cart {
    private User id_user;
    private Products id_product;
    private Date buyDate;
    private int cant;
    private double price;

    public cart(User id_user, Products id_product, Date buyDate, int cant, double price) {
        this.id_user = id_user;
        this.id_product = id_product;
        this.buyDate = buyDate;
        this.cant = cant;
        this.price = price;
    }

    public cart() {
        this(null, null, new Date(), 0, 0.0);
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Products getId_product() {
        return id_product;
    }

    public void setId_product(Products id_product) {
        this.id_product = id_product;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id_user=" + id_user +
                ", id_product=" + id_product +
                ", buyDate=" + buyDate +
                ", cant=" + cant +
                ", price=" + price +
                '}';
    }
}