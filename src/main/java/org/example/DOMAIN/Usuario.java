package org.example.DOMAIN;

import java.util.Objects;

public class Usuario {

    private  int id_user;

    private String username;

    private String password;

    private String DNI;

    private String email;

    public Usuario(int id_user, String username, String password, String DNI, String email) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.DNI = DNI;
        this.email = email;
    }

    public Usuario() {
        this(0," "," "," "," ");
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
        Usuario usuario = (Usuario) o;
        return id_user == usuario.id_user && Objects.equals(username, usuario.username) && Objects.equals(password, usuario.password) && Objects.equals(DNI, usuario.DNI) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, username, password, DNI, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
