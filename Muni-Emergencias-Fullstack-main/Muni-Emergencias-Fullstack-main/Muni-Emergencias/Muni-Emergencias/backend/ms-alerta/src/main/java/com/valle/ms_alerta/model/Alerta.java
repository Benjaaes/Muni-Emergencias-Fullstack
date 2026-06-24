package com.valle.ms_alerta.model; // Nombre actualizado

import jakarta.persistence.*;
@Entity
@Table(name = "alertas")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo; 
    private String mensaje;
    private String destinatario; 
    private boolean enviada;

    public Alerta() {}

    public Alerta(String tipo, String mensaje, String destinatario, boolean enviada) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.enviada = enviada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public boolean isEnviada() { return enviada; }
    public void setEnviada(boolean enviada) { this.enviada = enviada; }
}