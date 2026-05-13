package com.valle.ms_alerta.model; // Nombre actualizado

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo; 
    private String mensaje;
    private String destinatario; 
    private boolean enviada;
}