package com.valle.ms_usuario.dto;

import lombok.Data;

public class UsuarioDTO {
    private String nombre;
    private String rut;
    private String rol;
    private String email;
    private String password;

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, String rut, String rol, String email, String password) {
        this.nombre = nombre;
        this.rut = rut;
        this.rol = rol;
        this.email = email;
        this.password = password;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}