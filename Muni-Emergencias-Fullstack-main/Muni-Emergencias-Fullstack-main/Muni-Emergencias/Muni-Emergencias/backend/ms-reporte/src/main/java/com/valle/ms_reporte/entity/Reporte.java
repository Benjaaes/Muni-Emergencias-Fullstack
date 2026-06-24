package com.valle.ms_reporte.entity;

import jakarta.persistence.*;
import jakarta.persistence.*;

@Entity
@Table(name = "reportes")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEmergencia() {
        return tipoEmergencia;
    }

    public void setTipoEmergencia(String tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
