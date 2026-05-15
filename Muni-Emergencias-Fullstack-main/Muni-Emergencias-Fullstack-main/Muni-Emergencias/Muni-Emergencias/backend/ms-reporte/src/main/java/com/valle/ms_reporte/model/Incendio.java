package com.valle.ms_reporte.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reportes_incendios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incendio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;
    private String sector;
    private String nivelGravedad; // Ejemplo: ALTA, MEDIA, BAJA
    private String estado; // Ejemplo: ACTIVO, CONTROLADO
}