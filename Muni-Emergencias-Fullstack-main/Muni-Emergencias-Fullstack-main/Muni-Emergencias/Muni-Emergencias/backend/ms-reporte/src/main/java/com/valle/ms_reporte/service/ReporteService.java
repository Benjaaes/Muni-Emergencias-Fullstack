package com.valle.ms_reporte.service;

import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository repository;

    public List<Reporte> obtenerTodos() {
        return repository.findAll();
    }

    public Reporte guardar(Reporte reporte) {
        if (reporte.getEstado() == null)
            reporte.setEstado("Pendiente");
        return repository.save(reporte);
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Reporte> actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(reporte -> {
            reporte.setEstado(nuevoEstado);
            return repository.save(reporte);
        });
    }
}
