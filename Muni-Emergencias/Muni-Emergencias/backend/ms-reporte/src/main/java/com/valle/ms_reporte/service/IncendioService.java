package com.valle.ms_reporte.service;

import com.valle.ms_reporte.dto.IncendioDTO;
import com.valle.ms_reporte.model.Incendio;
import com.valle.ms_reporte.repository.IncendioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncendioService {

    @Autowired
    private IncendioRepository repository;

    public Incendio guardarReporte(IncendioDTO dto) {
        Incendio nuevoIncendio = Incendio.builder()
                .descripcion(dto.getDescripcion())
                .sector(dto.getSector())
                .nivelGravedad(dto.getNivelGravedad())
                .estado("ACTIVO")
                .build();
        
        return repository.save(nuevoIncendio);
    }

    public List<Incendio> listarTodos() {
        return repository.findAll();
    }
}