package com.valle.ms_alerta.controller;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.service.AlertaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertaControllerTest {

    @Mock
    private AlertaService service;

    @InjectMocks
    private AlertaController controller;

    @Test
    void enviarTest() {
        AlertaDTO dto = new AlertaDTO();
        Alerta alerta = new Alerta();
        when(service.emitirAlerta(any(AlertaDTO.class))).thenReturn(alerta);
        
        Alerta result = controller.enviar(dto);
        assertNotNull(result);
    }

    @Test
    void historialTest() {
        when(service.listarAlertas()).thenReturn(Collections.emptyList());
        List<Alerta> result = controller.historial();
        assertTrue(result.isEmpty());
    }
}
