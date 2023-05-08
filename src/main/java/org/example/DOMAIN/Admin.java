package org.example.DOMAIN;

import java.util.Objects;

public class Admin {
    private  int id_admin;

    private String username;

    private String password;

    private String DNI;

    private String email;

    public Admin(int id_admin, String username, String password, String DNI, String email) {
        this.id_admin = id_admin;
        this.username = username;
        this.password = password;
        this.DNI = DNI;
        this.email = email;
    }

    public Admin() {
        this(0," "," "," "," ");
    }

    public Admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin that = (Admin) o;
        return id_admin == that.id_admin && Objects.equals(DNI, that.DNI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_admin, DNI);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id_admin=" + id_admin +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
