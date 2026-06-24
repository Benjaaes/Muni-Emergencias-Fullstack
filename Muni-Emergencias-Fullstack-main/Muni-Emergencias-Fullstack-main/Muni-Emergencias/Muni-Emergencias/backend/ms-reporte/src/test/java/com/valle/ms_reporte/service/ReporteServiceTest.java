package com.valle.ms_reporte.service;

import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository repository;

    @InjectMocks
    private ReporteService service;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte("Incendio Forestal", "Fuego cerca del bosque", "Pendiente");
        reporte.setId(1L);
    }

    @Test
    @DisplayName("Debe retornar la lista de todos los reportes")
    void testObtenerTodos() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(reporte));

        // Act
        List<Reporte> resultado = service.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Incendio Forestal", resultado.get(0).getTipoEmergencia());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe guardar un reporte y asignar estado 'Pendiente' si viene nulo")
    void testGuardar_NuevoPendiente() {
        // Arrange
        Reporte nuevoReporte = new Reporte();
        nuevoReporte.setTipoEmergencia("Incendio Urbano");
        nuevoReporte.setDescripcion("Fuego en casa");

        Reporte reporteGuardado = new Reporte("Incendio Urbano", "Fuego en casa", "Pendiente");
        reporteGuardado.setId(2L);

        when(repository.save(any(Reporte.class))).thenReturn(reporteGuardado);

        // Act
        Reporte resultado = service.guardar(nuevoReporte);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getEstado());
        assertEquals(2L, resultado.getId());
        verify(repository, times(1)).save(nuevoReporte);
    }

    @Test
    @DisplayName("Debe eliminar el reporte si existe y retornar true")
    void testEliminar_Existente() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        boolean resultado = service.eliminar(1L);

        // Assert
        assertTrue(resultado);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe retornar false si el reporte a eliminar no existe")
    void testEliminar_NoExistente() {
        // Arrange
        when(repository.existsById(99L)).thenReturn(false);

        // Act
        boolean resultado = service.eliminar(99L);

        // Assert
        assertFalse(resultado);
        verify(repository, times(1)).existsById(99L);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar el estado de un reporte existente")
    void testActualizarEstado_Existente() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(reporte));
        when(repository.save(any(Reporte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Reporte> resultado = service.actualizarEstado(1L, "En Proceso");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("En Proceso", resultado.get().getEstado());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(reporte);
    }

    @Test
    @DisplayName("No debe actualizar nada si el reporte no existe")
    void testActualizarEstado_NoExistente() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Reporte> resultado = service.actualizarEstado(99L, "Atendido");

        // Assert
        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any(Reporte.class));
    }
}
