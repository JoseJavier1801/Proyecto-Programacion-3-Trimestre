package org.example.DOMAIN;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Admin {

    private List<cart> myCart;

    public User(int id, String username, String password, String DNI, String email) {
        super(id, username, password, DNI, email);
        myCart=new ArrayList<>();
    }

    public User() {
        this(0," "," "," "," ");
    }

    public List<cart> getMyCart() {
        return myCart;
    }

    public void setMyCart(List<cart> myCart) {
        this.myCart = myCart;
    }

    @Override
    public String toString() {
        return "User{" +
                "myCart=" + myCart +
                "} " + super.toString();
    }
}
