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
 *  - GET  /api/alertas/historial: devuelve el historial de alertas emitidas.
 *  - El controller responde correctamente cuando la lista de alertas está vacía.
 *  - El controller puede manejar múltiples tipos de emergencia.
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
    @DisplayName("enviar: Retorna la alerta emitida con enviada=true cuando el servicio la procesa")
    void enviar_RetornaAlerta_CuandoEmitidaExitosamente() {
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alertaIncendio);

        Alerta resultado = controller.enviar(dtoIncendio);

        assertNotNull(resultado);
        assertEquals("INCENDIO", resultado.getTipo());
        assertEquals("Bomberos Central", resultado.getDestinatario());
        assertTrue(resultado.isEnviada(), "La alerta debe estar marcada como enviada");
        verify(service, times(1)).emitirAlerta(any(AlertaDTO.class));
    }

    @Test
    @DisplayName("enviar: El controller delega correctamente la emisión de una alerta de sismo")
    void enviar_ManejaAlertaDeSismo_Correctamente() {
        AlertaDTO dtoSismo = new AlertaDTO("SISMO", "Sismo 5.2", "Toda la población");
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alertaSismo);

        Alerta resultado = controller.enviar(dtoSismo);

        assertNotNull(resultado);
        assertEquals("SISMO", resultado.getTipo());
        assertEquals("Toda la población", resultado.getDestinatario());
    }

    @Test
    @DisplayName("enviar: Invoca al service exactamente una vez por alerta emitida")
    void enviar_InvocaAlServiceUnaSolaVez() {
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alertaIncendio);

        controller.enviar(dtoIncendio);

        verify(service, times(1)).emitirAlerta(any(AlertaDTO.class));
        verifyNoMoreInteractions(service);
    }

    // ===================== GET /api/alertas/historial =====================

    @Test
    @DisplayName("historial: Devuelve el historial completo de alertas emitidas por el municipio")
    void historial_DevuelveHistorialCompleto() {
        when(service.listarAlertas()).thenReturn(Arrays.asList(alertaIncendio, alertaSismo));

        List<Alerta> resultado = controller.historial();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("INCENDIO", resultado.get(0).getTipo());
        assertEquals("SISMO", resultado.get(1).getTipo());
        verify(service, times(1)).listarAlertas();
    }

    @Test
    @DisplayName("historial: Devuelve lista vacía cuando no hay alertas registradas")
    void historial_DevuelveListaVacia_CuandoNoHayAlertas() {
        when(service.listarAlertas()).thenReturn(Collections.emptyList());

        List<Alerta> resultado = controller.historial();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
