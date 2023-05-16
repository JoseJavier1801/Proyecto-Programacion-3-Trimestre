package org.example.DOMAIN;

import java.util.ArrayList;
import java.util.List;

public class User extends person {
    /**
     * Clase user que hereda de la clase  person
     */

    public User(int id, String username, String password, String DNI, String email) {
        super(id, username, password, DNI, email);

    }

    public User() {
        this(0," "," "," "," ");
    }

    @Override
    public String toString() {
        return "User{} " + super.toString();
    }
}