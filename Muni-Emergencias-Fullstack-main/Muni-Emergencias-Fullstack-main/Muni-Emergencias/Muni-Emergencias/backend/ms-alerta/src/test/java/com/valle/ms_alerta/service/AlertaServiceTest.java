package com.valle.ms_alerta.service;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.repository.AlertaRepository;
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
public class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @InjectMocks
    private AlertaService service;

    @Test
    void emitirAlertaTest() {
        AlertaDTO dto = new AlertaDTO();
        dto.setTipo("Alerta");
        dto.setMensaje("Test");
        
        Alerta alerta = new Alerta();
        alerta.setTipo("Alerta");
        alerta.setEnviada(true);
        
        when(repository.save(any(Alerta.class))).thenReturn(alerta);
        
        Alerta result = service.emitirAlerta(dto);
        assertNotNull(result);
        assertTrue(result.isEnviada());
        verify(repository, times(1)).save(any(Alerta.class));
    }

    @Test
    void listarAlertasTest() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Alerta> result = service.listarAlertas();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
