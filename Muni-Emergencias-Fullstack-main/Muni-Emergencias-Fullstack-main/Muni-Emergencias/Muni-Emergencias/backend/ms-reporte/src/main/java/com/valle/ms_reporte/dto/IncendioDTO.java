package com.valle.ms_reporte.dto;

import lombok.Data;

public class IncendioDTO {
    private String descripcion;
    private String sector;
    private String nivelGravedad;

    public IncendioDTO() {
    }

    public IncendioDTO(String descripcion, String sector, String nivelGravedad) {
        this.descripcion = descripcion;
        this.sector = sector;
        this.nivelGravedad = nivelGravedad;
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
}