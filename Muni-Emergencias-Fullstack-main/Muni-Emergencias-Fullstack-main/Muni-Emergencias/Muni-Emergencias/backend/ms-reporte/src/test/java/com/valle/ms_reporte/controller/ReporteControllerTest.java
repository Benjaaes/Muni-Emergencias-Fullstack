package com.valle.ms_reporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valle.ms_reporte.entity.Reporte;
import com.valle.ms_reporte.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte("Incendio Forestal", "Fuego cerca del bosque", "Pendiente");
        reporte.setId(1L);
    }

    @Test
    @DisplayName("GET /api/reportes - Debe retornar lista de reportes")
    void testListar() throws Exception {
        // Arrange
        when(service.obtenerTodos()).thenReturn(Arrays.asList(reporte));

        // Act & Assert
        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].tipoEmergencia").value("Incendio Forestal"));

        verify(service, times(1)).obtenerTodos();
    }

    @Test
    @DisplayName("POST /api/reportes - Debe crear y retornar el reporte")
    void testCrear() throws Exception {
        // Arrange
        Reporte nuevoReporte = new Reporte("Incendio Urbano", "Fuego en casa", null);
        Reporte guardado = new Reporte("Incendio Urbano", "Fuego en casa", "Pendiente");
        guardado.setId(2L);

        when(service.guardar(any(Reporte.class))).thenReturn(guardado);

        // Act & Assert
        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoReporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.estado").value("Pendiente"));

        verify(service, times(1)).guardar(any(Reporte.class));
    }

    @Test
    @DisplayName("DELETE /api/reportes/{id} - Debe retornar 200 OK si se elimina")
    void testEliminar_Exitoso() throws Exception {
        // Arrange
        when(service.eliminar(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/reportes/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("DELETE /api/reportes/{id} - Debe retornar 404 si no existe")
    void testEliminar_NoEncontrado() throws Exception {
        // Arrange
        when(service.eliminar(99L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/reportes/99"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).eliminar(99L);
    }

    @Test
    @DisplayName("PUT /api/reportes/{id}/estado - Debe actualizar y retornar 200 OK")
    void testCambiarEstado_Exitoso() throws Exception {
        // Arrange
        Reporte actualizado = new Reporte("Incendio Forestal", "Fuego cerca del bosque", "En Proceso");
        actualizado.setId(1L);

        when(service.actualizarEstado(1L, "En Proceso")).thenReturn(Optional.of(actualizado));

        // Act & Assert
        mockMvc.perform(put("/api/reportes/1/estado")
                        .param("estado", "En Proceso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("En Proceso"));

        verify(service, times(1)).actualizarEstado(1L, "En Proceso");
    }

    @Test
    @DisplayName("PUT /api/reportes/{id}/estado - Debe retornar 404 si no existe")
    void testCambiarEstado_NoEncontrado() throws Exception {
        // Arrange
        when(service.actualizarEstado(99L, "Atendido")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/reportes/99/estado")
                        .param("estado", "Atendido"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).actualizarEstado(99L, "Atendido");
    }
}
