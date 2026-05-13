package com.valle.ms_reporte.controller;

import com.valle.ms_reporte.dto.IncendioDTO;
import com.valle.ms_reporte.model.Incendio;
import com.valle.ms_reporte.service.IncendioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class IncendioController {

    @Autowired
    private IncendioService service;

    @PostMapping("/crear")
    public Incendio crear(@RequestBody IncendioDTO dto) {
        return service.guardarReporte(dto);
    }

    @GetMapping("/listar")
    public List<Incendio> listar() {
        return service.listarTodos();
    }
}