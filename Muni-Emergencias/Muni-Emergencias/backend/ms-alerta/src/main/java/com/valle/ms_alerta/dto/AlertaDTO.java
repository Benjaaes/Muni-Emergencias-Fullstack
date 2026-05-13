package com.valle.ms_alerta.dto;

import lombok.Data;

@Data
public class AlertaDTO {
    private String tipo;
    private String mensaje;
    private String destinatario;
}