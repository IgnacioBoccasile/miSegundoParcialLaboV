package com.example.boccasilesplabov;

import java.util.Objects;

public class Usuario {
    private Integer id;
    private String username;
    private String rol;
    private Boolean admin;

    public Usuario() { }

    public Usuario(Integer id, String username, String rol, Boolean admin) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.admin = admin;
    }

    public Integer getId() { return id; }

    public String getUsername() { return this.username; }

    public String getRol() { return this.rol; }

    public Boolean getAdmin() { return admin; }

    public void setId(Integer id) { this.id = id; }

    public void setUsername(String username) { this.username = username; }

    public void setRol(String rol) { this.rol = rol; }

    public void setAdmin(Boolean admin) { this.admin = admin; }

    @Override
    public String toString() {
        String respuesta = "{ 'id': " + this.id + ", 'username': '".concat(this.username).concat("', 'rol': '").concat(this.rol).concat("', 'admin': ");

        if (this.admin) respuesta += "true }";
        else respuesta += "false }";

        return respuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(username, usuario.username) &&
                Objects.equals(rol, usuario.rol) &&
                Objects.equals(admin, usuario.admin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, rol, admin);
    }
}