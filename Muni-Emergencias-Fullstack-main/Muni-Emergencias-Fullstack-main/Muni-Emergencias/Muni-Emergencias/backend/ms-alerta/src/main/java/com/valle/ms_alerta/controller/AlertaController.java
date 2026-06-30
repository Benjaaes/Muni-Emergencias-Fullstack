package com.valle.ms_alerta.controller;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService service;

    @PostMapping("/enviar")
    public ResponseEntity<Alerta> enviar(@RequestBody AlertaDTO dto) {
        if (dto == null || dto.getTipo() == null) {
            return ResponseEntity.badRequest().build();
        }
        Alerta alerta = service.emitirAlerta(dto);
        return ResponseEntity.ok(alerta);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Alerta>> historial() {
        List<Alerta> alertas = service.listarAlertas();
        if (alertas == null || alertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alertas);
    }
}