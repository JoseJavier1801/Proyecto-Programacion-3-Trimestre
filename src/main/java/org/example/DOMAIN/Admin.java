package org.example.DOMAIN;

import java.util.Objects;

/**
 * Clase admin que hereda la clase user
 */
public class Admin extends person {

    public Admin(int id, String username, String password, String DNI, String email) {
        super(id, username, password, DNI, email);
    }



    public Admin() {
        this(0," "," "," "," ");
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
