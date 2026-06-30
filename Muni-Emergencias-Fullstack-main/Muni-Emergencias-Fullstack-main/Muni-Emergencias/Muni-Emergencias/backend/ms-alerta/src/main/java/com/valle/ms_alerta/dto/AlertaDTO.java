package com.valle.ms_alerta.dto;

public class AlertaDTO {
    private String tipo;
    private String mensaje;
    private String destinatario;

    public AlertaDTO() {}

    public AlertaDTO(String tipo, String mensaje, String destinatario) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { 
        if (mensaje == null) {
            this.mensaje = "";
        } else {
            this.mensaje = mensaje; 
        }
    }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
}