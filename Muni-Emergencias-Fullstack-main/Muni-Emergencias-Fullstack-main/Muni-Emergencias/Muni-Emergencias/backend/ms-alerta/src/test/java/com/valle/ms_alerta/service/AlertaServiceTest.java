package com.valle.ms_alerta.service;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.repository.AlertaRepository;
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
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del AlertaService (ms-alerta).
 *
 * Contexto del sistema: El municipio "Valle del Sol" emite alertas de emergencia
 * (incendios, sismos, emergencias médicas, etc.) a destinatarios específicos.
 *
 * Reglas de negocio cubiertas:
 *  - emitirAlerta: crea una alerta con estado enviada=true, la persiste y la devuelve.
 *  - emitirAlerta: mapea correctamente tipo, mensaje y destinatario desde el DTO.
 *  - listarAlertas: recupera el historial completo de alertas emitidas.
 *  - listarAlertas: retorna lista vacía cuando no hay alertas registradas.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AlertaService - Pruebas de lógica de negocio")
public class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AlertaService service;

    private AlertaDTO dtoIncendio;
    private AlertaDTO dtoSismo;
    private Alerta alertaIncendio;
    private Alerta alertaSismo;

    @BeforeEach
    void setUp() {
        // Alerta por incendio forestal
        dtoIncendio = new AlertaDTO("INCENDIO", "Incendio forestal en sector norte", "Bomberos Central");
        alertaIncendio = new Alerta("INCENDIO", "Incendio forestal en sector norte", "Bomberos Central", true);
        alertaIncendio.setId(1L);

        // Alerta por sismo
        dtoSismo = new AlertaDTO("SISMO", "Sismo de magnitud 5.2 detectado", "Toda la población");
        alertaSismo = new Alerta("SISMO", "Sismo de magnitud 5.2 detectado", "Toda la población", true);
        alertaSismo.setId(2L);
    }

    // ===================== emitirAlerta =====================

    @Test
    @DisplayName("emitirAlerta: La alerta se persiste y se marca como enviada")
    void emitirAlerta_AlertaMarcadaComoEnviada_YPersistida() {
        when(repository.save(any(Alerta.class))).thenReturn(alertaIncendio);

        Alerta resultado = service.emitirAlerta(dtoIncendio);

        assertNotNull(resultado);
        assertTrue(resultado.isEnviada(), "La alerta debe quedar marcada como enviada=true al emitirse");
        verify(repository, times(1)).save(any(Alerta.class));
    }

    @Test
    @DisplayName("emitirAlerta: El tipo, mensaje y destinatario se mapean correctamente desde el DTO")
    void emitirAlerta_DatosDelDtoMapeadosCorrectamente() {
        when(repository.save(any(Alerta.class))).thenAnswer(invocation -> {
            Alerta a = invocation.getArgument(0);
            a.setId(10L);
            return a;
        });

        Alerta resultado = service.emitirAlerta(dtoIncendio);

        assertEquals("INCENDIO", resultado.getTipo());
        assertEquals("Incendio forestal en sector norte", resultado.getMensaje());
        assertEquals("Bomberos Central", resultado.getDestinatario());
        assertTrue(resultado.isEnviada());
    }

    @Test
    @DisplayName("emitirAlerta: Soporta diferentes tipos de emergencia (SISMO, INCENDIO, etc.)")
    void emitirAlerta_SoportaDiferentesTiposDeEmergencia() {
        when(repository.save(any(Alerta.class))).thenReturn(alertaSismo);

        Alerta resultado = service.emitirAlerta(dtoSismo);

        assertNotNull(resultado);
        assertEquals("SISMO", resultado.getTipo());
        assertEquals("Toda la población", resultado.getDestinatario());
    }

    // ===================== listarAlertas =====================

    @Test
    @DisplayName("listarAlertas: Devuelve el historial completo de alertas emitidas por el municipio")
    void listarAlertas_DevuelveHistorialCompleto() {
        when(repository.findAll()).thenReturn(Arrays.asList(alertaIncendio, alertaSismo));
        
        com.valle.ms_alerta.dto.ReporteDTO[] reportes = new com.valle.ms_alerta.dto.ReporteDTO[1];
        com.valle.ms_alerta.dto.ReporteDTO rep = new com.valle.ms_alerta.dto.ReporteDTO();
        rep.setId(1L);
        rep.setTipoEmergencia("PRUEBA");
        rep.setDescripcion("Test desc");
        rep.setEstado("CRITICO");
        reportes[0] = rep;
        
        when(restTemplate.getForObject(anyString(), eq(com.valle.ms_alerta.dto.ReporteDTO[].class))).thenReturn(reportes);

        List<Alerta> resultado = service.listarAlertas();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("INCENDIO", resultado.get(0).getTipo());
        assertEquals("SISMO", resultado.get(1).getTipo());
        assertEquals("PRUEBA", resultado.get(2).getTipo());
        verify(repository, times(1)).findAll();
        verify(restTemplate, times(1)).getForObject(anyString(), eq(com.valle.ms_alerta.dto.ReporteDTO[].class));
    }

    @Test
    @DisplayName("listarAlertas: Retorna lista vacía cuando no hay alertas registradas")
    void listarAlertas_RetornaListaVacia_CuandoNoHayAlertas() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(restTemplate.getForObject(anyString(), eq(com.valle.ms_alerta.dto.ReporteDTO[].class))).thenThrow(new RuntimeException("API error"));

        List<Alerta> resultado = service.listarAlertas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
