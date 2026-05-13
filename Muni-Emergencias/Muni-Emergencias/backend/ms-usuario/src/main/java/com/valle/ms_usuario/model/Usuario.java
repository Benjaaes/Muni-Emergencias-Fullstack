package com.valle.ms_usuario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // <--- PATRÓN DE DISEÑO
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String rut;
    private String rol; // Ejemplo: "ADMIN", "BOMBERO", "VECINO"
    private String email;
}