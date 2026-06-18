package com.valle.ms_reporte.service;

import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.repository.ReporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @Mock
    private ReporteRepository repository;

    @InjectMocks
    private ReporteService service;

    @Test
    void obtenerTodosTest() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Reporte> list = service.obtenerTodos();
        assertNotNull(list);
    }

    @Test
    void guardarTest() {
        Reporte reporte = new Reporte("Incendio", "Test", null);
        when(repository.save(any(Reporte.class))).thenReturn(reporte);
        
        Reporte result = service.guardar(reporte);
        assertEquals("Pendiente", reporte.getEstado());
        verify(repository, times(1)).save(any(Reporte.class));
    }

    @Test
    void eliminarTest() {
        when(repository.existsById(1L)).thenReturn(true);
        boolean result = service.eliminar(1L);
        assertTrue(result);
        verify(repository, times(1)).deleteById(1L);
    }
    
    @Test
    void eliminarNoExisteTest() {
        when(repository.existsById(1L)).thenReturn(false);
        boolean result = service.eliminar(1L);
        assertFalse(result);
    }

    @Test
    void actualizarEstadoTest() {
        Reporte reporte = new Reporte();
        reporte.setEstado("Pendiente");
        
        when(repository.findById(1L)).thenReturn(Optional.of(reporte));
        when(repository.save(any(Reporte.class))).thenReturn(reporte);
        
        Optional<Reporte> result = service.actualizarEstado(1L, "Controlado");
        assertTrue(result.isPresent());
        assertEquals("Controlado", result.get().getEstado());
    }
}
