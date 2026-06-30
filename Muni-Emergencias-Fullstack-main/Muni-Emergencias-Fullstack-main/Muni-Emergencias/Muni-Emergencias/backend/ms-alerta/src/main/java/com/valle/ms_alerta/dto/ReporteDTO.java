package com.valle.ms_alerta.dto;

public class ReporteDTO {
    private Long id;
    private String tipoEmergencia;
    private String descripcion;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoEmergencia() { return tipoEmergencia; }
    public void setTipoEmergencia(String tipoEmergencia) { this.tipoEmergencia = tipoEmergencia; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
