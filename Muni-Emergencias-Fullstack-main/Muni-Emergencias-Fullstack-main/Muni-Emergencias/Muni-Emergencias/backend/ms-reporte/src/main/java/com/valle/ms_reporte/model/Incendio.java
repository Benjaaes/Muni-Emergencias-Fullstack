package com.valle.ms_reporte.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reportes_incendios")
public class Incendio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;
    private String sector;
    private String nivelGravedad; // Ejemplo: ALTA, MEDIA, BAJA
    private String estado; // Ejemplo: ACTIVO, CONTROLADO

    public Incendio() {
    }

    public Incendio(String descripcion, String sector, String nivelGravedad, String estado) {
        this.descripcion = descripcion;
        this.sector = sector;
        this.nivelGravedad = nivelGravedad;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(String nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}