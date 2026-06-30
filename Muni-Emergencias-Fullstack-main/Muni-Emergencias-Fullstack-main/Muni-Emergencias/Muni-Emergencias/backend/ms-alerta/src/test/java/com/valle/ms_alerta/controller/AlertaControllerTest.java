package com.valle.ms_alerta.controller;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.service.AlertaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del AlertaController (ms-alerta).
 *
 * Contexto del sistema: El municipio emite alertas de emergencia a través
 * de este endpoint. El Controller delega la lógica al AlertaService.
 *
 * Reglas de negocio cubiertas:
 *  - POST /api/alertas/enviar: emite una nueva alerta y la devuelve con enviada=true.
 *  - POST /api/alertas/enviar: retorna 400 si dto es null o tipo es null.
 *  - GET  /api/alertas/historial: devuelve el historial de alertas emitidas.
 *  - GET  /api/alertas/historial: devuelve 204 si la lista está vacía.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AlertaController - Pruebas de capa web")
public class AlertaControllerTest {

    @Mock
    private AlertaService service;

    @InjectMocks
    private AlertaController controller;

    private AlertaDTO dtoIncendio;
    private Alerta alertaIncendio;
    private Alerta alertaSismo;

    @BeforeEach
    void setUp() {
        dtoIncendio = new AlertaDTO("INCENDIO", "Incendio forestal en sector norte", "Bomberos Central");

        alertaIncendio = new Alerta("INCENDIO", "Incendio forestal en sector norte", "Bomberos Central", true);
        alertaIncendio.setId(1L);

        alertaSismo = new Alerta("SISMO", "Sismo de magnitud 5.2", "Toda la población", true);
        alertaSismo.setId(2L);
    }

    // ===================== POST /api/alertas/enviar =====================

    @Test
    @DisplayName("enviar: Retorna 200 con la alerta emitida cuando el DTO es válido")
    void enviar_RetornaAlerta_CuandoEmitidaExitosamente() {
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alertaIncendio);

        ResponseEntity<Alerta> response = controller.enviar(dtoIncendio);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("INCENDIO", response.getBody().getTipo());
        assertEquals("Bomberos Central", response.getBody().getDestinatario());
        assertTrue(response.getBody().isEnviada(), "La alerta debe estar marcada como enviada");
        verify(service, times(1)).emitirAlerta(any(AlertaDTO.class));
    }

    @Test
    @DisplayName("enviar: Retorna 400 cuando el DTO es null")
    void enviar_Retorna400_CuandoDtoEsNull() {
        ResponseEntity<Alerta> response = controller.enviar(null);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        verify(service, never()).emitirAlerta(any());
    }

    @Test
    @DisplayName("enviar: Retorna 400 cuando el tipo del DTO es null")
    void enviar_Retorna400_CuandoTipoEsNull() {
        AlertaDTO dtoSinTipo = new AlertaDTO(null, "Sin tipo", "Nadie");
        
        ResponseEntity<Alerta> response = controller.enviar(dtoSinTipo);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        verify(service, never()).emitirAlerta(any());
    }

    @Test
    @DisplayName("enviar: El controller delega correctamente la emisión de una alerta de sismo")
    void enviar_ManejaAlertaDeSismo_Correctamente() {
        AlertaDTO dtoSismo = new AlertaDTO("SISMO", "Sismo 5.2", "Toda la población");
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alertaSismo);

        ResponseEntity<Alerta> response = controller.enviar(dtoSismo);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("SISMO", response.getBody().getTipo());
    }

    // ===================== GET /api/alertas/historial =====================

    @Test
    @DisplayName("historial: Devuelve 200 con el historial completo de alertas del municipio")
    void historial_DevuelveHistorialCompleto() {
        when(service.listarAlertas()).thenReturn(Arrays.asList(alertaIncendio, alertaSismo));

        ResponseEntity<List<Alerta>> response = controller.historial();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("INCENDIO", response.getBody().get(0).getTipo());
        assertEquals("SISMO", response.getBody().get(1).getTipo());
        verify(service, times(1)).listarAlertas();
    }

    @Test
    @DisplayName("historial: Devuelve 204 cuando no hay alertas registradas")
    void historial_Devuelve204_CuandoNoHayAlertas() {
        when(service.listarAlertas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Alerta>> response = controller.historial();

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
    }
}
