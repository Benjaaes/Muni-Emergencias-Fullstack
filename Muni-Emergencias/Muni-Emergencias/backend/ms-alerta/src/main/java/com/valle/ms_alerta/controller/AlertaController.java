package com.valle.ms_alerta.controller;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService service;

    @PostMapping("/enviar")
    public Alerta enviar(@RequestBody AlertaDTO dto) {
        return service.emitirAlerta(dto);
    }

    @GetMapping("/historial")
    public List<Alerta> historial() {
        return service.listarAlertas();
    }
}