package org.example.DOMAIN;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Admin {

    private List<Products> cart;

    public User(int id, String username, String password, String DNI, String email) {
        super(id, username, password, DNI, email);
        cart = new ArrayList<>();
    }

    public User() {
        this(0," "," "," "," ");
    }

    public List<Products> getCart() {
        return cart;
    }

    public void setCart(List<Products> cart) {
        this.cart = cart;
    }

    public void addToCart(Products product) {
        cart.add(product);
    }

    public void removeFromCart(Products product) {
        cart.remove(product);
    }

    @Override
    public String toString() {
        return "User{" +
                "cart=" + cart +
                "} " + super.toString();
    }
}