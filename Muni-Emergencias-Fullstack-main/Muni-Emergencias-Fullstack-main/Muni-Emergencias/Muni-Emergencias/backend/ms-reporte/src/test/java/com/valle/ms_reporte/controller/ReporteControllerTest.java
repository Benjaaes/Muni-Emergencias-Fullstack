package com.valle.ms_reporte.controller;

import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {

    @Mock
    private ReporteService service;

    @InjectMocks
    private ReporteController controller;

    @Test
    void listarTest() {
        when(service.obtenerTodos()).thenReturn(Collections.emptyList());
        List<Reporte> result = controller.listar();
        assertTrue(result.isEmpty());
    }

    @Test
    void crearTest() {
        Reporte reporte = new Reporte();
        when(service.guardar(any(Reporte.class))).thenReturn(reporte);
        
        Reporte result = controller.crear(reporte);
        assertNotNull(result);
    }

    @Test
    void eliminarTest() {
        when(service.eliminar(1L)).thenReturn(true);
        ResponseEntity<Void> response = controller.eliminar(1L);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    void eliminarNotFoundTest() {
        when(service.eliminar(1L)).thenReturn(false);
        ResponseEntity<Void> response = controller.eliminar(1L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void cambiarEstadoTest() {
        Reporte reporte = new Reporte();
        when(service.actualizarEstado(1L, "Controlado")).thenReturn(Optional.of(reporte));
        
        ResponseEntity<Reporte> response = controller.cambiarEstado(1L, "Controlado");
        assertEquals(200, response.getStatusCode().value());
    }
}
