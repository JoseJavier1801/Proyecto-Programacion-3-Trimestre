package org.example.DOMAIN;

import java.util.ArrayList;
import java.util.List;

public class User extends Admin {
    /**
     * Clase user que hereda de la clase admin
     */
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


    @Override
    public String toString() {
        return "User{" +
                "cart=" + cart +
                "} " + super.toString();
    }
}