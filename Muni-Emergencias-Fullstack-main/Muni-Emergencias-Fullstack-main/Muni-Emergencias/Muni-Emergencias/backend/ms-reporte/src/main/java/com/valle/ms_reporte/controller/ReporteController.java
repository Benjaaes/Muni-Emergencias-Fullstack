package com.valle.ms_reporte.controller;

import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
// @CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    public List<Reporte> listar() {
        return service.obtenerTodos();
    }

    @PostMapping
    public Reporte crear(@RequestBody Reporte reporte) {
        return service.guardar(reporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Reporte> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
