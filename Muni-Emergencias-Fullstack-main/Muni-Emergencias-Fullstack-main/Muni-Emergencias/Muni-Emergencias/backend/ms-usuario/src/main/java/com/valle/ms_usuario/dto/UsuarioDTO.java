package com.valle.ms_usuario.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombre;
    private String rut;
    private String rol;
    private String email;
}