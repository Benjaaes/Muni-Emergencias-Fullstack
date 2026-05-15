package com.valle.ms_reporte.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reportes")
@Data // Esta anotación de Lombok crea automáticamente los Getters y Setters
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_emergencia")
    private String tipoEmergencia;

    private String descripcion;

    private String estado;

    // Constructor vacío (Obligatorio para JPA)
    public Reporte() {
    }

    // Constructor útil para crear reportes rápido
    public Reporte(String tipoEmergencia, String descripcion, String estado) {
        this.tipoEmergencia = tipoEmergencia;
        this.descripcion = descripcion;
        this.estado = estado;
    }
}
